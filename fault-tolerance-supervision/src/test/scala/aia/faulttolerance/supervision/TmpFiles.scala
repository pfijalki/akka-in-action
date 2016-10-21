package aia.faulttolerance.supervision

import java.nio.file.{Files, Path}

import org.scalatest.{BeforeAndAfter, Suite}

trait TmpFiles extends BeforeAndAfter {
  this: Suite =>

  var files: Seq[Path] = Seq()

  after {
    files foreach Files.delete
    files = Seq()
  }

  def withTmpFile(prefix: String = "tmpFile")(x: Path => Unit = file => {}) = {
    val file: Path = newTmpFile(prefix)
    x.apply(file)
    file
  }

  private def newTmpFile(prefix: String = "tmpfile") = {
    val file: Path = Files.createTempFile(prefix, ".test")
    files = files :+ file
    file
  }


}
