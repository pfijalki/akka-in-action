package aia.faulttolerance.supervision

import aia.faulttolerance.supervision.Writer.Writer
import akka.actor.{Actor, ActorLogging}

case class ConsoleWriter(target: String) extends Actor with Writer with ActorLogging {
  override def receive: Receive = {
    case Writer.WriteMessage(obj) => log.info(s"Writing to $target: $obj");
  }
}
