/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright © 2019-2022 Tensil AI Company */

package tensil

import tensil.mem.MemKind.{MemKind, RegisterBank}
import tensil.axi.Config

/**
  * @param memKind which kind of memory to implement, used for swapping out black boxes in simulation
  * @param axi the AXI port width configurations to use for all AXI interfaces
  */
case class PlatformConfig(
    memKind: MemKind,
    axi: Config,
)

object PlatformConfig {
  implicit val default = PlatformConfig(
    memKind = RegisterBank,
    axi = Config.Xilinx,
  )
}
