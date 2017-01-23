import java.io.{FileWriter, BufferedWriter, File}
import java.nio.file.{Paths, Files}

import scala.util.{Success, Failure, Try}

object sandbox {
  val tom = <a>this is tom</a>

  println(s"Does it work? $tom")

  val sets: Seq[String] = Seq("Sets", "Usual")

  sets.zipWithIndex

  val tb = Thumbnail("this", "sucks")

  case class Thumbnail(size: String, url: String)

  tb.size

  def getTB(tb: Thumbnail): Seq[Thumbnail] = {
    Seq(Thumbnail("this", "sucks"), Thumbnail("yes", "does"))
  }

  val nails = Thumbnail("small", "http://ih1.redbubble.net/work.31820.1.flat,135x135,075,f.jpg")

  nails.url.split(",")(1).split('x')

  val results = Try(scala.io.Source.fromURL("http://take-home-test.herokuapp.com/api/v1/work.xml").getLines.toList)

  results match {
    case Success(v) => "WHAT"
    case Failure(s) => "I FAIL"
  }

  val file: Try[File] = {
    Try(new File("/bad/path/exists"))
  }

  val checkBasePathExists: Boolean = Files.exists(Paths.get("/bad/path/exists"))
}