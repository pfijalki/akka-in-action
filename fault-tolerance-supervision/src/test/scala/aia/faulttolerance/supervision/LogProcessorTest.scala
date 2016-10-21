package aia.faulttolerance.supervision

import java.io.File
import java.nio.charset.StandardCharsets.UTF_8
import java.nio.file.{Files, Path}
import java.nio.file.StandardOpenOption.APPEND

import aia.faulttolerance.supervision.Writer.Writer
import akka.actor.{ActorRef, ActorSystem}
import akka.testkit.TestKit
import org.scalatest.WordSpecLike

class LogProcessorTest extends TestKit(ActorSystem("testSystem")) with WordSpecLike with ShutdownAfterAll with TmpFiles {

  "Log processor" should {
    "read test resource file and send it to writer" in {
      // given
      val logProcessor: ActorRef = system.actorOf(Processor.props(Seq("finalDestination"), LogProcessor(_, EchoWriter(_, testActor))))
      val fileContent = Seq("firstLine", "secondLine")
      val path: Path = withTmpFile("logProcessorTest") { path =>
        fileContent map (line => s"$line\n".getBytes(UTF_8)) foreach (Files.write(path, _, APPEND))
      }

      // given
      logProcessor ! Processor.LogFile(new File(path.toUri))

      // then
      expectMsg("finalDestination:firstLine")
      expectMsg("finalDestination:secondLine")
    }
  }

}

sealed case class EchoWriter(target: String, targetActor: ActorRef) extends Writer {
  override def receive: Receive = {
    case Writer.WriteMessage(msg) => targetActor ! s"$target:$msg"
  }
}
