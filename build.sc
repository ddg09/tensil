/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright © 2019-2022 Tensil AI Company */

import mill._
import mill.define.Sources
import mill.modules.Util
import mill.scalalib.TestModule.ScalaTest
import mill.bsp._
import scalalib._

import $ivy.`com.lihaoyi::mill-contrib-scalapblib:`
import contrib.scalapblib._

object common extends ScalaModule { m =>
  def scalaVersion = "2.12.13"

  override def ivyDeps =
    Agg(
      ivy"com.lihaoyi::upickle:1.3.8",
      ivy"com.github.scopt::scopt:3.7.1",
    )

  object test extends Tests with TestModule.ScalaTest {
    def ivyDeps = m.ivyDeps() ++ Agg(ivy"org.scalatest::scalatest:3.2.12")
  }
}

object tools extends ScalaPBModule { m =>
  def moduleDeps = Seq(common)

  def scalaVersion   = common.scalaVersion
  def scalaPBVersion = "0.11.6"

  object test extends Tests with TestModule.ScalaTest {
    def forkArgs = Seq("-Xmx12g", "-Xmx12g")

    def ivyDeps = m.ivyDeps() ++ Agg(ivy"org.scalatest::scalatest:3.2.12")
  }
}

object compiler extends ScalaModule { m =>
  def moduleDeps = Seq(tools)

  def scalaVersion = tools.scalaVersion

  def mainClass = Some("tensil.tools.compiler.Main")
}

object emulator extends ScalaModule { m =>
  def moduleDeps = Seq(tools)

  def scalaVersion = tools.scalaVersion

  def mainClass = Some("tensil.tools.emulator.Main")
}

object web extends ScalaModule { m =>
  def moduleDeps = Seq(tools)

  def scalaVersion = tools.scalaVersion

  override def ivyDeps =
    tools.ivyDeps() ++ super.ivyDeps() ++ Agg(
      ivy"com.github.seratch::awscala-s3:0.8.5",
      ivy"com.github.seratch::awscala-sqs:0.8.5",
      ivy"com.github.seratch::awscala-dynamodb:0.8.5",
    )

  object test extends Tests with TestModule.ScalaTest {
    def forkArgs = Seq("-Xmx12g", "-Xmx12g")

    def ivyDeps = m.ivyDeps() ++ Agg(ivy"org.scalatest::scalatest:3.2.12")
  }

  def mainClass = Some("tensil.tools.web.Main")
}

object rtl extends SbtModule { m =>
  def moduleDeps = Seq(common)

  override def scalaVersion = common.scalaVersion
  override def scalacOptions =
    Seq(
      "-encoding",
      "utf8",
      "-Xsource:2.11",
      "-Xfatal-warnings",
      "-unchecked",
      "-deprecation",
      "-feature",
      "-language:reflectiveCalls",
    )

  def mainClass = Some("tensil.zynq.tcu.Top")

  override def ivyDeps =
    common.ivyDeps() ++ super.ivyDeps() ++ Agg(
      ivy"edu.berkeley.cs::chisel3:3.5.3",
      ivy"edu.berkeley.cs::chiseltest:0.5.3",
      ivy"edu.berkeley.cs::treadle:1.5.3",
    )
  override def scalacPluginIvyDeps =
    Agg(
      ivy"edu.berkeley.cs:::chisel3-plugin:3.5.3",
    )

  object test extends Tests with ScalaTest {
    def forkArgs = Seq("-Xmx12g", "-Xmx12g")

    override def ivyDeps =
      m.ivyDeps() ++ Agg(ivy"org.scalatest::scalatest:3.2.12")
  }
}

object sim extends ScalaModule { m =>
  def moduleDeps = Seq(rtl, tools)

  override def scalaVersion = common.scalaVersion

  override def ivyDeps =
    tools.ivyDeps() ++ rtl.ivyDeps() ++ super.ivyDeps()

  object test extends Tests with ScalaTest {
    def moduleDeps = super.moduleDeps ++ Seq(rtl.test, tools.test)

    def forkArgs = Seq("-Xmx48g", "-Xmx48g")

    override def ivyDeps =
      m.ivyDeps() ++ Agg(ivy"org.scalatest::scalatest:3.2.12")
  }
}
