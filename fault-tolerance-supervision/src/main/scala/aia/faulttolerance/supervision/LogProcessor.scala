package aia.faulttolerance.supervision

import java.io.File

import aia.faulttolerance.supervision.Processor.{LogFile, Processor}
import aia.faulttolerance.supervision.Writer.Writer
import akka.actor.ActorRef

case class LogProcessor(writeDestinations: Seq[String], writerGenerator: String => Writer) extends Processor {
  require(writeDestinations.nonEmpty)
  var remainingDestinations = writeDestinations
  var writer = pickWriter

  def receive = {
    case LogFile(file) =>
      read(file).map(Writer.WriteMessage(_)).foreach(writer.!)
  }

  private def read(file: File) = scala.io.Source.fromFile(file).getLines()

  private def pickWriter: ActorRef = context.actorOf(Writer.props(writeDestinations.head, writerGenerator))
}
