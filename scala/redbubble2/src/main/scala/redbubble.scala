/**
  * Created by tomklimovski on 20/1/17.
  */

import java.io.{FileWriter, BufferedWriter, File}
import scala.util.{Try,Success,Failure}
import scala.io.Source
import com.typesafe.config.ConfigFactory
import scala.xml._


case class Thumbnail(size: String, url: String)
case class MakeAndThumbNail(make: String, thumbnail: Thumbnail)
case class ModelAndThumbNail(model: String, thumbnail: Thumbnail)
case class AllMakeModelThumbNails(make: String, model: String, thumbnail: Thumbnail)

abstract class Cameras {
  val cameraMake: String
  val cameraModels: Seq[String]
  val thumbNailURLs: Seq[Thumbnail]
}

class Redbubble(node: scala.xml.Elem) {

  //**************
  //URL's and Directory paths that are common to all functions in class Redbubble that don't change.
  //**************
  val Url_head = "<!DOCTYPE html>\n<html><head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">"
  val Url_style = "<style type=\"text/css\">\nnav { \nmargin: 10px;}\n</style>\n</head>\n<body>\n<header>"
  val Url_end = "</header>\n</body></html>"

  val BaseDir = System.getProperty("user.dir")
  val TargetHtml = ConfigFactory.load().getConfig("redbubble").getString("html_path")
  val IndexPath:  String = s"$BaseDir$TargetHtml/index.html"
  val CameraMakePath = ConfigFactory.load().getConfig("redbubble").getString("camera_make_path")
  val CameraModelPath = ConfigFactory.load().getConfig("redbubble").getString("camera_model_path")

  val Url_index:  String = "<nav>\n<a href=\"" + IndexPath + "\">Index</a>\n</nav>"

  val DefaultImageHeight = ConfigFactory.load().getConfig("redbubble").getString("thumbnail_image_height")
  val DefaultImageWidth = ConfigFactory.load().getConfig("redbubble").getString("thumbnail_image_width")
  val AllPictureSize = ConfigFactory.load().getConfig("redbubble").getString("all_picture_size")
  val NumberOfWorktoDisplay = ConfigFactory.load().getConfig("redbubble").getInt("number_of_work_to_display")

  def fromXML(make: String, model: Seq[String], thumbNails: Seq[Thumbnail]): Cameras =
    new Cameras {
      val cameraMake: String = make
      val cameraModels: Seq[String] = model
      val thumbNailURLs: Seq[Thumbnail] = thumbNails
    }

  def cameraMap: Map[String, Set[String]] = {

    //maps every model to it's make, trimming works that don't have a camera defined
    //Map(D-LUX 3 -> LEICA, DMC-FZ30 -> Panasonic, ......
    val modelMakeMap = (
      for {n <- node.child
           if (!((n \\ "make").text.trim.isEmpty))
      } yield {
        (n \\ "model").text -> (n \\ "make").text
      }
      ).toMap

    //reverse the mapping above, and groups models to makes
    //Map(LEICA -> Set(D-LUX 3),FUJI PHOTO FILM CO., LTD. -> Set(SLP1000SE), NIKON CORPORATION -> Set(NIKON D80),
    (modelMakeMap.groupBy(_._2).mapValues(_.keys.toSet))
  }

  private def processXML(cameraMaps: Map[String, Set[String]]): Seq[Cameras] = {

    (for {(cameraMake, cameraModel) <- cameraMaps}
      yield {
        fromXML(cameraMake, getAllCameraModels(cameraMake), getCameraMakeThumbNails(cameraMake))
      }).toSeq
  }

  //get all thumbNails from file with cameras. Where a cameraMake doesn't exist, substitute empty string with 'None'
  def getAllMakeAndThumbnails: Seq[MakeAndThumbNail] = {
    for {n <- node.child
         urls <- (n \\ "url")
    } yield {
      val make = if( (n \\ "make").text.trim.isEmpty ) "None" else (n \\ "make").text.trim

      MakeAndThumbNail(make , (Thumbnail((urls \ "@type").mkString, urls.text)))
    }
  }

  //case class AllMakeModelThumbNails(make: String, model: String, thumbnail: Thumbnail)
  val allMakeModelThumbNails: Seq[AllMakeModelThumbNails] = {
    for {n <- node.child
         urls <- (n \\ "url")
    } yield {
      val make = if( (n \\ "make").text.trim.isEmpty ) "None" else (n \\ "make").text.trim

      AllMakeModelThumbNails(make , (n \\ "model").text, (Thumbnail((urls \ "@type").mkString, urls.text)))
    }
  }

  //get ALL camera models that exist in the file, even duplicates
  private def getAllCameraModels(cameraMake: String): Seq[String] =
     allMakeModelThumbNails.filter(_.make == cameraMake).map(_.model)

  //get thumbNails specific to a camera make
  private def getCameraMakeThumbNails(cameraMake: String): Seq[Thumbnail] =
    getAllMakeAndThumbnails.filter(_.make == cameraMake).map(_.thumbnail)

  private def getCameraModelThumbNails(cameraModel: String): Seq[Thumbnail] =
    allMakeModelThumbNails.filter(_.model == cameraModel).map(_.thumbnail)

  private val allCameraMakes: Seq[String] =
    allMakeModelThumbNails.map(_.make).distinct

  //needed because not all works contain a camera make. requirement is to grab first 10 thumbnail-works, but doesn't
  //mention whether a camera created the photo
  private val getIndexThumbnails: Seq[Thumbnail] =
    getAllMakeAndThumbnails.map(_.thumbnail)

  private def htmlAhref(toPrint: String, path: String): String = {
    ("<a href=\"" + BaseDir + TargetHtml + path + "/" +
      toPrint + ".html\">" + toPrint + "</a> | \n").mkString("")
  }
  private def htmlTitle(toPrint: String): String = {
    s"<title>$toPrint</title>"
  }
  private def htmlHeader(toPrint: String): String = {
    s"<h1>$toPrint</h1>"
  }
  private def htmlIMG(url: String): String = {
    "<img height=\""+ getThumbnailHeight(url) + "\"width=\"" + getThumbnailWidth(url) + "\"src=\"" + (url)+ "\">\n"
  }

  private def getThumbnailHeight(url: String) = {

    val getHeight: Try[String] = {
      Try(url.split(",")(1).split('x')(0))
    }

    getHeight match {
      case Success(value) => value.mkString("")
      case Failure(f) => DefaultImageHeight
    }
  }
  private def getThumbnailWidth(url: String) = {
    val getWidth: Try[String] = {
      Try(url.split(",")(1).split('x')(0))
    }

    getWidth match {
      case Success(value) => value.mkString("")
      case Failure(f) => DefaultImageWidth
    }
  }

  private def getThumbNails(whichPage: String, generic: String) = {

  }

  private def applyConstraints(tb: Seq[Thumbnail]): String = {
                                      tb.filter(_.size == AllPictureSize)
                                      .take(NumberOfWorktoDisplay)
                                      .map(x => htmlIMG(x.url))
                                      .mkString("")
  }

  def printHTMLindex(allWorks: Seq[Cameras]) = {

    val title:  String = htmlTitle("Navigation for Works")
    val header: String = htmlHeader("Camera Makes")

    val cameraMakes = (
      for{ cameraMake <- allCameraMakes if(cameraMake != "None")
      } yield {
          printCameraMakeWebPages(cameraMake)
          htmlAhref(cameraMake, CameraMakePath)
      }).mkString("")

    val navCameraMakes = s"<nav>$cameraMakes</nav>"

    val navThumbNails = applyConstraints(getIndexThumbnails)

    val html = Url_head + title + Url_style + header + navCameraMakes + navThumbNails + Url_end

    writeFile(IndexPath, html)
  }

  def printCameraMakeWebPages(cameraMake: String) = {

    //*************
    val title:        String = "<title>Navigation for Works</title>"
    val header:       String = s"<h1>$cameraMake</h1>"

    val cameraModels = (for(model <- getAllCameraModels(cameraMake).distinct) yield {
      printCameraModelWebPages(model)
      htmlAhref(model, CameraModelPath)
    }).mkString("")

    val navCameraModels = s"<nav>$cameraModels</nav>"

    val navCameraMakeThumbNails = applyConstraints(getCameraMakeThumbNails(cameraMake))

    val html = Url_head + title + Url_style + header + Url_index + navCameraModels + navCameraMakeThumbNails + Url_end

    writeFile(s"$BaseDir$TargetHtml$CameraMakePath/$cameraMake.html", html.toString)

  }

  def printCameraModelWebPages(cameraModel: String) = {

    //*************
    val title:        String = "<title>Navigation for Works</title>"
    val header:       String = s"<h1>$cameraModel</h1>"

    val modelThumbNails: String = applyConstraints(getCameraModelThumbNails(cameraModel))

    val html: String = Url_head + title + Url_style + header + Url_index + modelThumbNails + Url_end

    writeFile(s"$BaseDir$TargetHtml$CameraModelPath/$cameraModel.html", html.toString)

  }

  def writeFile(path: String, stringToWrite: String) = {
    val file = new File(s"$path")
    val bw = new BufferedWriter(new FileWriter(file))
    bw.write(stringToWrite)
    bw.close()
  }
}

object Redbubble {

  def getFileFromUrl(file: String): String = {
    val readAPIFile: Try[List[String]] = {
      Try(Source.fromURL(file).getLines.toList)
    }

    readAPIFile match {
      case Success(lines) => lines.mkString("")
      case Failure(f) => s"NOT FOUND: $file"
    }
  }

  def main(args: Array[String]): Unit = {

    println("Hello, world! ")

    val url = ConfigFactory.load().getConfig("redbubble").getString("API_URL")
    val node = XML.loadString(getFileFromUrl(url))

    val rb = new Redbubble(node)

    //println(s"In it's glory: ${rb.getAllMakeModelThumbNails}")

    //translates the entire XML to the abstract class Cameras for easy access when creating HTML
    val allWorks: Seq[Cameras] = rb.processXML(rb.cameraMap)

    rb.printHTMLindex(allWorks)

  }
}