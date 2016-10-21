package aia.faulttolerance.supervision

import akka.testkit.TestKit
import org.scalatest.{BeforeAndAfterAll, Suite}

trait ShutdownAfterAll extends BeforeAndAfterAll {
  this: TestKit with Suite =>
  override protected def afterAll(): Unit = {
    super.afterAll
    system.terminate
  }
}
