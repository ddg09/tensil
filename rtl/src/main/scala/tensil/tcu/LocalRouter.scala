/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright © 2019-2022 Tensil AI Company */

package tensil.tcu

import chisel3._
import chisel3.util.{Decoupled, DecoupledIO, Queue}
import tensil.util.{DecoupledHelper, decoupled, reportThroughput}
import tensil.util.decoupled.{
  MultiEnqueue,
  MuxSel,
  MuxSelWithSize,
  makeSizeHandler
}
import tensil.util.decoupled.QueueWithReporting
import tensil.Architecture
import tensil.mem.SizeHandler

class LocalRouter[T <: Data](
    val gen: T,
    val arch: Architecture,
    controlQueueSize: Int = 2,
) extends Module {
  val io = IO(new Bundle {
    val control = Flipped(
      Decoupled(
        new LocalDataFlowControlWithSize(arch.localDepth)
      )
    )
    val mem = new Bundle {
      val output = Flipped(Decoupled(gen))
      val input  = Decoupled(gen)
    }
    val array = new Bundle {
      val input       = Decoupled(gen)
      val output      = Flipped(Decoupled(gen))
      val weightInput = Decoupled(gen)
    }
    val acc = new Bundle {
      val output = Flipped(Decoupled(gen))
      val input  = Decoupled(gen)
    }
    val timeout        = Input(Bool())
    val tracepoint     = Input(Bool())
    val programCounter = Input(UInt(32.W))
  })

  dontTouch(io.timeout)
  dontTouch(io.tracepoint)
  dontTouch(io.programCounter)

  val control = io.control

  // routing control muxes
  val memReadDataDemuxModule = Module(
    new decoupled.Demux(
      chiselTypeOf(io.mem.output.bits),
      3,
      name = "router.memReadData"
    )
  )
  memReadDataDemuxModule.io.in <> io.mem.output
  io.array.weightInput <> memReadDataDemuxModule.io.out(0)
  io.array.input <> memReadDataDemuxModule.io.out(1)

  val memWriteDataMuxModule = Module(
    new decoupled.Mux(
      chiselTypeOf(io.mem.input.bits),
      2,
      name = "router.memWriteData"
    )
  )
  memWriteDataMuxModule.io.in(0).tieOff()
  memWriteDataMuxModule.io.in(1) <> io.acc.output
  io.mem.input <> memWriteDataMuxModule.io.out

  val accWriteDataMuxModule = Module(
    new decoupled.Mux(
      chiselTypeOf(io.acc.input.bits),
      2,
      name = "router.accWriteData"
    )
  )
  accWriteDataMuxModule.io.in(0) <> io.array.output
  accWriteDataMuxModule.io.in(1) <> memReadDataDemuxModule.io.out(2)
  io.acc.input <> accWriteDataMuxModule.io.out

  // size handlers
  val (memReadDataDemux, memReadDataDemuxSel) =
    makeSizeHandler(
      3,
      "memReadDataDemux",
      memReadDataDemuxModule.io.sel,
      arch.localDepth,
      control.bits.size
    )
  val (memWriteDataMux, memWriteDataMuxSel) =
    makeSizeHandler(
      2,
      "memWriteDataMux",
      memWriteDataMuxModule.io.sel,
      arch.localDepth,
      control.bits.size
    )
  val (accWriteDataMux, accWriteDataMuxSel) =
    makeSizeHandler(
      2,
      "accWriteDataMux",
      accWriteDataMuxModule.io.sel,
      arch.localDepth,
      control.bits.size,
      bufferSize = arch.arraySize * 2,
    )
  memReadDataDemux.tieOff()
  memWriteDataMux.tieOff()
  accWriteDataMux.tieOff()

  val enqueuer1 = MultiEnqueue(1)
  val enqueuer2 = MultiEnqueue(2)
  enqueuer1.tieOff()
  enqueuer2.tieOff()

  when(control.bits.kind === LocalDataFlowControl.memoryToArrayWeight) {
    control.ready := enqueuer1.enqueue(
      control.valid,
      memReadDataDemux,
      memReadDataDemuxSel(0.U),
    )
  }.elsewhen(control.bits.kind === LocalDataFlowControl._memoryToArrayToAcc) {
    control.ready := enqueuer2.enqueue(
      control.valid,
      memReadDataDemux,
      memReadDataDemuxSel(1.U),
      accWriteDataMux,
      accWriteDataMuxSel(0.U),
    )
  }.elsewhen(control.bits.kind === LocalDataFlowControl._arrayToAcc) {
    control.ready := enqueuer1.enqueue(
      control.valid,
      accWriteDataMux,
      accWriteDataMuxSel(0.U),
    )
  }.elsewhen(control.bits.kind === LocalDataFlowControl.accumulatorToMemory) {
    control.ready := enqueuer1.enqueue(
      control.valid,
      memWriteDataMux,
      memWriteDataMuxSel(1.U),
    )
  }.elsewhen(
    control.bits.kind === LocalDataFlowControl.memoryToAccumulator
  ) {
    control.ready := enqueuer2.enqueue(
      control.valid,
      memReadDataDemux,
      memReadDataDemuxSel(2.U),
      accWriteDataMux,
      accWriteDataMuxSel(1.U),
    )
  }.otherwise {
    control.ready := true.B
  }
}

object LocalRouter {
  val dataflows = Array(
    LocalDataFlowControl.memoryToArrayWeight,
    LocalDataFlowControl._memoryToArrayToAcc,
    LocalDataFlowControl._arrayToAcc,
    LocalDataFlowControl.accumulatorToMemory,
    LocalDataFlowControl.memoryToAccumulator,
  )
}
