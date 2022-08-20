/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright © 2019-2022 Tensil AI Company */

package tensil.util.decoupled

import chisel3._
import chisel3.util.{Decoupled, DecoupledIO, Queue}
import tensil.util.zero

class Demux[T <: Data](
    val gen: T,
    val n: Int,
    controlQueueSize: Int = 2,
    name: String = ""
) extends Module {
  val io = IO(new Bundle {
    val in  = Flipped(Decoupled(gen))
    val sel = Flipped(Decoupled(UInt(util.log2Ceil(n).W)))
    val out = Vec(n, Decoupled(gen))
  })

  val in  = io.in
  val sel = io.sel

  for (i <- 0 until n) {
    val out = io.out(i)
    out.bits := zero(gen)
    out.valid := false.B
  }

  val out = io.out(sel.bits)
  out.bits := in.bits
  out.valid := sel.valid && in.valid
  sel.ready := in.valid && out.ready
  in.ready := sel.valid && out.ready
}

object Demux {
  def apply[T <: Data](
      in: DecoupledIO[T],
      out0: DecoupledIO[T],
      out1: DecoupledIO[T],
      controlQueueSize: Int = 2,
      name: String = "",
  ): DecoupledIO[UInt] = {
    val demux = Module(
      new Demux(
        chiselTypeOf(in.bits),
        2,
        controlQueueSize = controlQueueSize,
        name = name
      )
    )
    demux.io.in <> in
    out0 <> demux.io.out(0)
    out1 <> demux.io.out(1)
    demux.io.sel
  }

  def apply[T <: Data](
      in: DecoupledIO[T],
      out0: DecoupledIO[T],
      out1: DecoupledIO[T],
      out2: DecoupledIO[T],
      controlQueueSize: Int,
      name: String,
  ): DecoupledIO[UInt] = {
    val demux = Module(
      new Demux(
        chiselTypeOf(in.bits),
        3,
        controlQueueSize = controlQueueSize,
        name = name
      )
    )
    demux.io.in <> in
    out0 <> demux.io.out(0)
    out1 <> demux.io.out(1)
    out2 <> demux.io.out(2)
    demux.io.sel
  }
}
