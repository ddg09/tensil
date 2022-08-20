/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright © 2019-2022 Tensil AI Company */

package tensil.mem

import chisel3._
import chisel3.util.log2Ceil

class MemRequest(val depth: Long) extends Bundle with Address {
  val write   = Bool()
  val address = UInt(log2Ceil(depth).W)
}
