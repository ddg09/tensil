/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright © 2019-2022 Tensil AI Company */

package tensil.util.decoupled

import chisel3._
import chiseltest._
import tensil.FunUnitSpec
import tensil.decoupled.{Driver => DecoupledDriver}

class SplitterSpec extends FunUnitSpec {
  describe("DecoupledSplitter") {
    describe("when there are two outputs") {
      it("should output to each one in turn") {
        test(new Splitter(2, UInt(32.W))) { m =>
          m.io.in.setSourceClock(m.clock)
          m.io.out(0).setSinkClock(m.clock)
          m.io.out(1).setSinkClock(m.clock)

          var covered = false

          fork {
            m.io.out(0).expectDequeue(123.U)
            m.io.out(1).expectDequeue(456.U)
            covered = true
          }
          m.io.in.enqueue(123.U)
          m.io.in.enqueue(456.U)

          assert(covered)
        }
      }

      it("should handle repeated rounds of output") {
        test(new Splitter(2, UInt(32.W))) { m =>
          m.io.in.setSourceClock(m.clock)
          m.io.out(0).setSinkClock(m.clock)
          m.io.out(1).setSinkClock(m.clock)

          var covered = false

          fork {
            m.io.out(0).expectDequeue(123.U)
            m.io.out(1).expectDequeue(456.U)
            m.io.out(0).expectDequeue(789.U)
            m.io.out(1).expectDequeue(321.U)
            covered = true
          }
          m.io.in.enqueue(123.U)
          m.io.in.enqueue(456.U)
          m.io.in.enqueue(789.U)
          m.io.in.enqueue(321.U)

          assert(covered)
        }
      }
    }
  }
}
