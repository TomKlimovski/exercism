import scala.io.Source

object redbubble2 {
  def getFile = {
    val url = "http://take-home-test.herokuapp.com/api/v1/works.xml"
    scala.io.Source.fromURL(url).mkString

  }

  val node = scala.xml.XML.loadString(getFile)

  abstract class Cameras {
    val cameraMake: String
    val cameraModels: String
    val thumbNailURLs: String
  }

  val cameraMap: Map[String, String] = (
    for {n <- node.child
         if (!((n \\ "make").text.trim.isEmpty))
    } yield {
      (n \\ "model").text -> (n \\ "make").text
    }
    ).toMap

  def fromXML(node: scala.xml.Node): Cameras =
    new Cameras {
      val cameraMake: String = (node \\ "model").txt
      val cameraModels: String = (node \\ "make").txt
      val thumbNailURLs: String = (node \\ "urls")
    }

  println("YOU")

  val something = {
    for {n <- node.child
         if (!((n \\ "make").text.trim.isEmpty))
    } yield {
      fromXML(n)
    }
  }

  def main(args: Array[String]): Unit = {
    println("Hello, world!")

  }
}