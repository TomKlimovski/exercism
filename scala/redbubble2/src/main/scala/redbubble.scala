/**
  * Created by tomklimovski on 20/1/17.
  */

import java.io.{FileWriter, BufferedWriter, File}
import java.nio.file.{Paths, Files}
import scala.util.{Try, Success, Failure}
import com.typesafe.config.ConfigFactory
import scala.xml._


case class Thumbnail(size: String, url: String)
case class MakeAndThumbNail(make: String, thumbnail: Thumbnail)
case class ModelAndThumbNail(model: String, thumbnail: Thumbnail)
case class AllMakeModelThumbNails(make: String, model: String, thumbnail: Thumbnail)

class Redbubble(node: scala.xml.Elem) {

  //**************
  //URL's and Directory paths that are common to all functions in class Redbubble that don't change.
  //**************
  val Url_head = "<!DOCTYPE html>\n<html><head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">"
  val Url_style = "<style type=\"text/css\">\nnav { \nmargin: 10px;}\n</style>\n</head>\n<body>\n<header>"
  val Url_end = "</header>\n</body></html>"

  val _BaseDir = ConfigFactory.load().getConfig("redbubble").getString("base_dir")
  val BaseDir = if (_BaseDir.trim.isEmpty) System.getProperty("user.dir") else _BaseDir
  val TargetHtml = ConfigFactory.load().getConfig("redbubble").getString("html_path")
  val IndexPath: String = s"$BaseDir$TargetHtml"
  val CameraMakePath = ConfigFactory.load().getConfig("redbubble").getString("camera_make_path")
  val CameraModelPath = ConfigFactory.load().getConfig("redbubble").getString("camera_model_path")

  val Url_index: String = "<nav>\n<a href=\"" + IndexPath + "/index.html"+ "\">Index</a>\n</nav>"

  val DefaultImageHeight = ConfigFactory.load().getConfig("redbubble").getString("thumbnail_image_height")
  val DefaultImageWidth = ConfigFactory.load().getConfig("redbubble").getString("thumbnail_image_width")
  val AllPictureSize = ConfigFactory.load().getConfig("redbubble").getString("all_picture_size")
  val NumberOfWorktoDisplay = ConfigFactory.load().getConfig("redbubble").getInt("number_of_work_to_display")
  //**************

  def createDirectoryStructure: Boolean = {
    val htmlDir: File = new File(BaseDir + TargetHtml)
    val cameraMakeDir: File = new File(BaseDir + TargetHtml + CameraMakePath)
    val cameraModelDir: File = new File(BaseDir + TargetHtml + CameraModelPath)

    val checkBasePathExists: Boolean = Files.exists(Paths.get(BaseDir + TargetHtml))

    if (!checkBasePathExists) {
      htmlDir.mkdir()
      cameraMakeDir.mkdir()
      cameraModelDir.mkdir()
      true
    }
    else {
      Console.err.println(s"Error: Could not create directory structure. Paths already exist")
      false
    }
  }

  //case class AllMakeModelThumbNails(make: String, model: String, thumbnail: Thumbnail)
  //contains all mapping from make -> model(s) -> thumbnail(s)
  //**************
  private val allMakeModelThumbNails: Seq[AllMakeModelThumbNails] = {
    for {n <- node.child
         urls <- (n \\ "url")
    } yield {
      val make = if ((n \\ "make").text.trim.isEmpty) "None" else (n \\ "make").text.trim

      AllMakeModelThumbNails(make, (n \\ "model").text, (Thumbnail((urls \ "@type").mkString, urls.text)))
    }
  }

  //get all thumbNails from file with cameras. Where a cameraMake doesn't exist, substitute empty string with 'None'
  private def allMakeAndThumbnails: Seq[MakeAndThumbNail] = {
    for {n <- node.child
         urls <- (n \\ "url")
    } yield {
      val make = if ((n \\ "make").text.trim.isEmpty) "None" else (n \\ "make").text.trim

      MakeAndThumbNail(make, (Thumbnail((urls \ "@type").mkString, urls.text)))
    }
  }

  //get ALL camera models that exist in the file, even duplicates
  def allCameraModels(cameraMake: String): Seq[String] =
    allMakeModelThumbNails.filter(_.make == cameraMake.trim).map(_.model)

  //get thumbNails specific to a camera make
  def cameraMakeThumbNails(cameraMake: String): Seq[Thumbnail] =
    allMakeAndThumbnails.filter(_.make == cameraMake).map(_.thumbnail)

  def cameraModelThumbNails(cameraModel: String): Seq[Thumbnail] =
    allMakeModelThumbNails.filter(_.model == cameraModel).map(_.thumbnail)

  val allCameraMakes: Seq[String] =
    allMakeModelThumbNails.map(_.make).distinct

  //needed because not all works contain a camera make. requirement is to grab first 10 thumbnail-works, but doesn't
  //mention whether a camera created the photo
  val allIndexThumbnails: Seq[Thumbnail] =
    allMakeAndThumbnails.map(_.thumbnail)

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
    "<img height=\"" + getThumbnailHeight(url) + "\"width=\"" + getThumbnailWidth(url) + "\"src=\"" + (url) + "\">\n"
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

  private def applyConstraints(tb: Seq[Thumbnail]): String = {
    tb.filter(_.size == AllPictureSize)
      .take(NumberOfWorktoDisplay)
      .map(x => htmlIMG(x.url))
      .mkString("")
  }

  private def checkPathExists(path: String): Boolean = {
    Files.exists(Paths.get(path))
  }

  //**************
  //methods to print to HTML
  //**************
  def printHTML = {

    val title: String = htmlTitle("Navigation for Works")
    val header: String = htmlHeader("Camera Makes")

    val cameraMakes = (
      for {cameraMake <- allCameraMakes if (cameraMake != "None")
      } yield {
        printCameraMakeWebPages(cameraMake)
        htmlAhref(cameraMake, CameraMakePath)
      }).mkString("")

    val navCameraMakes = s"<nav>$cameraMakes</nav>"

    val navThumbNails = applyConstraints(allIndexThumbnails)

    val html = Url_head + title + Url_style + header + navCameraMakes + navThumbNails + Url_end

    writeFile(IndexPath, "/index.html", html)
  }

  def printCameraMakeWebPages(cameraMake: String) = {

    //*************
    val title: String = "<title>Navigation for Works</title>"
    val header: String = s"<h1>$cameraMake</h1>"

    val cameraModels = (for (model <- allCameraModels(cameraMake).distinct) yield {
      printCameraModelWebPages(model)
      htmlAhref(model, CameraModelPath)
    }).mkString("")

    val navCameraModels = s"<nav>$cameraModels</nav>"

    val navCameraMakeThumbNails = applyConstraints(cameraMakeThumbNails(cameraMake))

    val html = Url_head + title + Url_style + header + Url_index + navCameraModels + navCameraMakeThumbNails + Url_end

    writeFile(s"$BaseDir$TargetHtml$CameraMakePath", s"/$cameraMake.html", html.toString)

  }

  def printCameraModelWebPages(cameraModel: String) = {

    //*************
    val title: String = "<title>Navigation for Works</title>"
    val header: String = s"<h1>$cameraModel</h1>"

    val modelThumbNails: String = applyConstraints(cameraModelThumbNails(cameraModel))

    val html: String = Url_head + title + Url_style + header + Url_index + modelThumbNails + Url_end

    writeFile(s"$BaseDir$TargetHtml$CameraModelPath", s"/$cameraModel.html", html.toString)

  }

  def writeFile(path: String, fileName: String, stringToWrite: String): Boolean = {

    if (checkPathExists(path)) {
      val file: File = new File(s"$path$fileName")
      val bw = new BufferedWriter(new FileWriter(file))
      bw.write(stringToWrite)
      bw.close()
      true
    } else {
      Console.err.println(s"Error: $path does not exist")
      false
    }
  }

}

//***************
//Companion object to class Redbubble.class
//First we read in the XML file, load it via class scala.xml.XML and use that to process and print HTML
//***************
object Redbubble {

  def getFileFromUrl(file: String): String = {
    val readAPIFile: Try[List[String]] = {
      Try(scala.io.Source.fromURL(file).getLines.toList)
    }

    readAPIFile match {
      case Success(lines) => lines.mkString("")
      case Failure(f) => s"Error: NOT FOUND: $file\n$f"
    }
  }

  def main(args: Array[String]): Unit = {

    println("Hello, world! ")

    val Url = ConfigFactory.load().getConfig("redbubble").getString("API_URL")
    val loadFile = getFileFromUrl(Url)

    if(!loadFile.contains("Error") && loadFile.contains("xml")) {
      val node = XML.loadString(getFileFromUrl(Url))
      val rb = new Redbubble(node)
      rb.createDirectoryStructure
      rb.printHTML
    }
    else
      Console.err.println(s"****\nGracefully aborting program. Could not find $Url\n****")

  }
}