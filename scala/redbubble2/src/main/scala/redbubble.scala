import scala.xml.NodeSeq
import java.io._
import scalatags.Text.all._

object redbubble {

  abstract class Cameras {
    val cameraMake: String
    val cameraModels: String
    val thumbNailURLs: NodeSeq
  }

  def fromXML(node: scala.xml.Node): Cameras =
    new Cameras {
      val cameraMake: String = (node \\ "make").text
      val cameraModels: String = (node \\ "model").text
      val thumbNailURLs: NodeSeq = (node \\ "urls")
    }

  private def getFile = {
    val url = "http://take-home-test.herokuapp.com/api/v1/works.xml"
    scala.io.Source.fromURL(url).mkString

  }

  private def cameraMap(node: scala.xml.Elem): Map[String, String] = (
    for {n <- node.child
         if (!((n \\ "make").text.trim.isEmpty))
    } yield {
      (n \\ "model").text -> (n \\ "make").text
    }
  ).toMap

  def printHTMLindex(node: scala.xml.Elem) = {

    val cameras: Seq[Cameras] = {
      for {n <- node.child
           if (!((n \\ "make").text.trim.isEmpty))
      } yield {
        fromXML(n)
      }
    }

    val cameraMaps = (cameraMap(node))
    val makeModelMap = (cameraMaps.groupBy(_._2).mapValues(_.keys.toSet))

    val titleMessage = "Camera Makes"
    val navMessage = "I'm the NAV"

    //*************
    val head = "<!DOCTYPE html>\n<html><head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">"
    val title = "<title>" + "Navigation for Works" + "</title>"

    val style = "  <style type=\"text/css\">\n    nav {\n      margin: 10px;\n    }\n  </style>\n</head>\n<body>\n  <header>"
    val header = "<h1>" + titleMessage + "</h1>"

    //cameras.foreach(x => println(s"Thumbs ${ (x.thumbNailURLs \ "url")(0).text}") )

    val navCameraMakes = (for( (camera, make) <- makeModelMap) yield {
      "<nav><a href=" + "\"/Users/tomklimovski/exercism/scala/redbubble2/target/HTML/CameraMake/" + camera + ".html\">" + camera + "</a></nav>\n"
    }).mkString("")

    val navThumbNails = (for(camera <- cameras) yield {
        "<img height=50 width=50 src=\"" + (camera.thumbNailURLs \ "url").head.text + "\">\n"
    }).mkString("")

    val endHeader = "</header>"
    val end = "</body></html>"

    val html = head + title + style + header + navCameraMakes + navThumbNails + endHeader + end

    // FileWriter
    val file = new File("/Users/tomklimovski/exercism/scala/redbubble2/target/HTML/index.html")
    val bw = new BufferedWriter(new FileWriter(file))
    bw.write(html)
    bw close
  }

  def printHTMLcameras(camera: Cameras) = {
    val titleMessage = s"${camera.cameraMake}"

    //*************
    val head = "<!DOCTYPE html>\n<html><head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">"
    val title = "<title>" + "Navigation for Works" + "</title>"
    val style = "  <style type=\"text/css\">\n    nav {\n      margin: 10px;\n    }\n  </style>\n</head>\n<body>\n  <header>"
    val header = "<h1>" + titleMessage + "</h1>"

    //cameras.foreach(x => println(s"Thumbs ${ (x.thumbNailURLs \ "url")(0).text}") )

    val navCameraModels = "<nav>" + camera.cameraModels + "</nav>\n"

    val navThumbNails = "<img height=50 width=50 src=\"" + (camera.thumbNailURLs \ "url").head.text + "\">\n"

    val endHeader = "</header>"
    val end = "</body></html>"

    val html = head + title + style + header + navCameraModels + navThumbNails + endHeader + end


    val file = new File(s"/Users/tomklimovski/exercism/scala/redbubble2/target/HTML/CameraMake/${camera.cameraMake}.html")
    val bw = new BufferedWriter(new FileWriter(file))
    bw.write(html.toString)
    bw.close()
  }

  def main(args: Array[String]): Unit = {
    println("Hello, world!")
    val node = scala.xml.XML.loadString(getFile)

    val something = {
      for {n <- node.child
           if (!((n \\ "make").text.trim.isEmpty))
      } yield {
        fromXML(n)
      }
    }

    printHTMLindex(node)

    for(cameraMake <- something) yield {
      printHTMLcameras(cameraMake)
    }

  }
}