package aia.faulttolerance.supervision

import java.io.Serializable

import akka.actor.{Actor, Props}

import scala.reflect.ClassTag

object Writer {

  def props[T <: Writer : ClassTag](target: String, createFunc: String => T) = Props(createFunc(target))

  case class WriteMessage[T <: Serializable](any: T)

  trait Writer extends Actor {
    val target: String
  }

}
