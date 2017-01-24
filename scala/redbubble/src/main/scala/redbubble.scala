/**
  * Created by tomklimovski on 20/1/17.
  */

import java.io.{FileWriter, BufferedWriter, File}
import java.nio.file.{Paths, Files}
import scala.util.{Try, Success, Failure}
import com.typesafe.config.ConfigFactory
import scala.xml._


case class Thumbnail(size: String, url: String)
case class AllMakeModelThumbNails(make: String, model: String, thumbnail: Thumbnail)

class Redbubble(node: scala.xml.Elem) {

  //**************
  //URL's and Directory paths that are common to all functions in class Redbubble that don't change.
  //**************
  lazy val Url_head = "<!DOCTYPE html>\n<html><head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">"
  lazy val Url_style = "<style type=\"text/css\">\nnav { \nmargin: 10px;}\n</style>\n</head>\n<body>\n<header>"
  lazy val Url_end = "</body></html>"
  lazy val Url_end_header = "</header>\n"

  //default to user.dir if no directory given
  lazy val _BaseDir = ConfigFactory.load().getConfig("redbubble").getString("base_dir")
  lazy val BaseDir = if (_BaseDir.trim.isEmpty) System.getProperty("user.dir") else _BaseDir
  lazy val TargetHtml = ConfigFactory.load().getConfig("redbubble").getString("html_path")
  lazy val IndexPath: String = BaseDir+TargetHtml
  lazy val CameraMakePath = ConfigFactory.load().getConfig("redbubble").getString("camera_make_path")
  lazy val CameraModelPath = ConfigFactory.load().getConfig("redbubble").getString("camera_model_path")

  lazy val Url_index: String = "<nav>\n<a href=\"" + IndexPath + "/index.html"+ "\">Index</a>\n</nav>"

  val imageSizes: Seq[String] = Seq("small", "medium", "large")
  lazy val DefaultImageHeight = ConfigFactory.load().getConfig("redbubble").getString("thumbnail_image_height")
  lazy val DefaultImageWidth = ConfigFactory.load().getConfig("redbubble").getString("thumbnail_image_width")
  lazy val _AllPictureSize = ConfigFactory.load().getConfig("redbubble").getString("all_picture_size")
  //default to small if no or invalid picture size given
  lazy val AllPictureSize = if(imageSizes contains _AllPictureSize ) _AllPictureSize else "small"
  lazy val NumberOfWorktoDisplay = ConfigFactory.load().getConfig("redbubble").getInt("number_of_work_to_display")
  //**************

  def createDirectoryStructure: Boolean = {
    val targetDir:      File = new File(BaseDir + "/target")
    val htmlDir:        File = new File(BaseDir + TargetHtml)
    val cameraMakeDir:  File = new File(BaseDir + TargetHtml + CameraMakePath)
    val cameraModelDir: File = new File(BaseDir + TargetHtml + CameraModelPath)

    val checkBasePathExists: Boolean = Files.exists(Paths.get(BaseDir))
    val checkBasePathAndTargetExists: Boolean = Files.exists(Paths.get(BaseDir + TargetHtml))

    if (!checkBasePathAndTargetExists && checkBasePathExists) {

      val targetResult:       Boolean = targetDir.mkdir()
      val htmlResult:         Boolean = htmlDir.mkdir()
      val cameraMakeResult:   Boolean = cameraMakeDir.mkdir()
      val cameraModelResult:  Boolean= cameraModelDir.mkdir()

      true

    } else false
  }

  //Implementation for case class AllMakeModelThumbNails(make: String, model: String, thumbnail: Thumbnail)
  //contains all mapping with following structure:
  // make -> model -> thumbnail
  // example: List(AllMakeModelThumbNails(NIKON CORPORATION,NIKON D80,Thumbnail(small,http://ih1.redbubble.net/
  //                  work.31820.1.flat,135x135,075,f.jpg)), AllMakeModelThumbNails(NIKON CORPORATION,NIKON D80,
  //                  Thumbnail(medium,http://ih1.redbubble.net/work.31820.1.flat,300x300,075,f.jpg)),....
  //**************
  private lazy val allMakeModelThumbNails: Seq[AllMakeModelThumbNails] = {
    for {n <- node.child
         urls <- (n \\ "url")
    } yield {
      val make = if ((n \\ "make").text.trim.isEmpty) "None" else (n \\ "make").text.trim

      AllMakeModelThumbNails(make, (n \\ "model").text, (Thumbnail((urls \ "@type").mkString, urls.text)))
    }
  }

  //get ALL camera models that exist in the file, even duplicates
  def allCameraModels(cameraMake: String): Seq[String] =
    allMakeModelThumbNails.filter(_.make == cameraMake.trim).map(_.model)

  val allCameraModels: Seq[String] =
    allMakeModelThumbNails.map(_.model).filter(!_.isEmpty).distinct

  //camera make for specific model
  def cameraMakeForModel(cameraModel: String): String =
    allMakeModelThumbNails.filter(_.model == cameraModel).map(_.make).head

  //get thumbNails specific to a camera make
  def cameraMakeThumbNails(cameraMake: String): Seq[Thumbnail] =
    allMakeModelThumbNails.filter(_.make == cameraMake.trim).map(_.thumbnail)

  //thumbnails specific to a camera model
  def cameraModelThumbNails(cameraModel: String): Seq[Thumbnail] =
    allMakeModelThumbNails.filter(_.model == cameraModel).map(_.thumbnail)

  val allCameraMakes: Seq[String] =
    allMakeModelThumbNails.map(_.make).distinct

  //needed because not all works contain a camera make. requirement 1.1.1 is to grab first 10 thumbnail-works, but doesn't
  //mention whether a camera created the photo
  val allIndexThumbnails: Seq[Thumbnail] =
    allMakeModelThumbNails.map(_.thumbnail)

  //few helper functions to wrap HTML code around desired string toPrint
  //Probably could have used ScalaTags here
  private def htmlAhref(toPrint: String, path: String): String = {
    ("<a href=\"" + BaseDir + TargetHtml + path + "/" +
      toPrint + ".html\">" + toPrint + "</a> | \n").mkString
  }
  private def htmlTitle(toPrint: String): String = {
    "<title>" + toPrint + "</title>"
  }
  private def htmlHeader(toPrint: String): String = {
    "<h1>"+ toPrint + "</h1>"
  }
  private def htmlIMG(url: String): String = {
    "<img height=\"" + getThumbnailHeight(url) + "\"width=\"" + getThumbnailWidth(url) + "\"src=\"" + (url) + "\">\n"
  }

  //below is the path to the thumbnails
  //http://ih1.redbubble.net/work.31820.1.flat,135x135,075,f.jpg
  //these functions pull the XxY coordinates from the second token of the thumbnail url-string.
  private def getThumbnailHeight(url: String) = {

    val getHeight: Try[String] = {
      Try(url.split(",")(1).split('x')(0))
    }

    getHeight match {
      case Success(value) => value.mkString
      case Failure(f) => DefaultImageHeight
    }
  }
  private def getThumbnailWidth(url: String) = {
    val getWidth: Try[String] = {
      Try(url.split(",")(1).split('x')(0))
    }

    getWidth match {
      case Success(value) => value.mkString
      case Failure(f) => DefaultImageWidth
    }
  }

  //common constraints needed across thumbnails for each webpage
  private def applyConstraintsToThumbnail(tb: Seq[Thumbnail]): String = {
    tb.filter(_.size == AllPictureSize)
      .take(NumberOfWorktoDisplay)
      .map(x => htmlIMG(x.url))
      .mkString
  }

  private def checkPathExists(path: String): Boolean = {
    Files.exists(Paths.get(path))
  }

  //**************
  //methods to print to HTML
  //**************
  //this method is what's called from Main()
  def printHTML = {

    //ScalaTags
    val title:  String = htmlTitle("Navigation for Works")
    val header: String = htmlHeader("Camera Makes")

    val cameraMakes: Seq[String] = {
      for {cameraMake <- allCameraMakes if (cameraMake != "None")} yield {
        //while we have each camera make name, call the method to printHTML all camera makes
        printCameraMakeWebPages(cameraMake)

        //make links to new camera make
        htmlAhref(cameraMake, CameraMakePath)
      }
    }

    val navCameraMakes: String = "<nav>" + cameraMakes.mkString + "</nav>"

    //grab all thumbnails for the index page, and apply the constraints to it
    val navThumbNails:  String = applyConstraintsToThumbnail(allIndexThumbnails)

    //put together the entire HTML page string
    val html: String = Url_head + title + Url_style + header + navCameraMakes + Url_end_header + navThumbNails + Url_end

    //...and print it to file
    writeFile(IndexPath, "/index.html", html)
  }
  def printCameraMakeWebPages(cameraMake: String) = {

    //*************
    val title:  String = "<title>Navigation for Works</title>"
    val header: String = "<h1>"+cameraMake+"</h1>"

    val cameraModels: Seq[String] = for (model <- allCameraModels(cameraMake).distinct) yield {
      //while we have the camera models, print each html page for models
      printCameraModelWebPages(model)
      htmlAhref(model, CameraModelPath)
    }

    val navCameraModels = "<nav>"+ cameraModels.mkString +"</nav>"

    val navCameraMakeThumbNails = applyConstraintsToThumbnail(cameraMakeThumbNails(cameraMake))

    val html = Url_head + title + Url_style + header + Url_index + navCameraModels  + Url_end_header +
                    navCameraMakeThumbNails + Url_end

    writeFile(BaseDir+TargetHtml+CameraMakePath, "/" + cameraMake + ".html", html.toString)

  }
  def printCameraModelWebPages(cameraModel: String) = {

    val title:  String = "<title>Navigation for Works</title>"
    val header: String = "<h1>" + cameraModel + " </h1>"

    val modelThumbNails: String = applyConstraintsToThumbnail(cameraModelThumbNails(cameraModel))
    val cameraMakeforThisModel: String = cameraMakeForModel(cameraModel)

    val navCameraMake = "<nav>"+ htmlAhref(cameraMakeforThisModel, CameraMakePath) +"</nav>"

    val html: String = Url_head + title + Url_style + header + Url_index + navCameraMake + Url_end_header + modelThumbNails + Url_end

    writeFile(BaseDir+TargetHtml+CameraModelPath, "/"+cameraModel+".html", html)

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

  //method to read in file /api/v1/works.xml
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

    //retrieve config value from src/main/resources/application.conf for API URL to read in
    val Url:        String = ConfigFactory.load().getConfig("redbubble").getString("API_URL")
    val loadFileToString: String = getFileFromUrl(Url)

    //if we successfully loaded the file, and it is indeed XML
    if(!loadFileToString.contains("Error") && loadFileToString.contains("<?xml version=\"1.0\"?>")) {

      //load fileString as an XML Elem for easy parsing
      val node: Elem = XML.loadString(loadFileToString)

      //create new instance of Redbubble class with XML node
      val rb: Redbubble = new Redbubble(node)

      //if this is a clean project, i.e. HTML folders don't exist, create the necessary folder structure
      if(!rb.checkPathExists(rb.BaseDir + rb.TargetHtml)) {
        val createSuccess: Boolean = rb.createDirectoryStructure

        //Create all HTML required.
        //1st print Index.html, then 2. all camera makes, then 3. all camera models
        if(createSuccess)
          rb.printHTML
        else
          Console.err.println(s"****\nGracefully aborting program. Could not find ${rb.BaseDir}\n****")
      }

    }
    else
      Console.err.println(s"****\nGracefully aborting program. Could not find $Url\n****")

  }
}