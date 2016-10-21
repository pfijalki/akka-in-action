package aia.faulttolerance.supervision

import java.io.File

import aia.faulttolerance.supervision.Writer.Writer
import akka.actor.{Actor, Props}

import scala.reflect.ClassTag

object Processor {

  def props[T <: Processor : ClassTag](destinations: Seq[String], genFunc: Seq[String] => T) = Props(genFunc(destinations))

  trait Processor extends Actor {
    val writeDestinations: Seq[String]
    val writerGenerator: String => Writer
  }

  case class LogFile(file: File)

}
