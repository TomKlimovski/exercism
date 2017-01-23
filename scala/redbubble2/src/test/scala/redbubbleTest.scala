import org.scalatest.{FunSuite, Matchers}
import com.typesafe.config.ConfigFactory

import scala.xml.XML

class redbubbleTest extends FunSuite with Matchers {

  val url = ConfigFactory.load().getConfig("redbubble").getString("API_URL")

  val node = XML.loadString(Redbubble.getFileFromUrl(url))
  val rb = new Redbubble(node)

  val cameraMakes: Seq[String] = Seq("NIKON CORPORATION", "Canon","FUJIFILM","None","LEICA",
                                      "FUJI PHOTO FILM CO., LTD.","Panasonic")

  val _BaseDir = ConfigFactory.load().getConfig("redbubble").getString("base_dir")
  val BaseDir = if (_BaseDir.trim.isEmpty) System.getProperty("user.dir") else _BaseDir
  val TargetHtml = ConfigFactory.load().getConfig("redbubble").getString("html_path")

  //test files
  test("can't find URL file -> notExist") {
    Redbubble.getFileFromUrl("notExist") should include ("Error")
  }

  test("can't find URL file -> malformed URL") {
    val badURL = ConfigFactory.load().getConfig("test").getString("Bad_API_URL")
    Redbubble.getFileFromUrl(badURL) should include ("Error")
  }

  test("successful XML file read") {
    //pending
    Redbubble.getFileFromUrl(url) should include ("xml version")
  }

  test("unsuccessful XML read") {
    Redbubble.getFileFromUrl("http://www.redbubble.com/") should include ("Error")
  }

  //writeFile
  //def writeFile(path: String, stringToWrite: String)
  //*********
  test("writeFile - bad path") {
    rb.writeFile("/User/tomklimovski/DoesntExist", "fileName", "BadPath") should be (false)
  }

  test("writeFile - good path") {
    rb.writeFile(BaseDir, "/test.html", "topPath") should be (true)
  }

  //createDirectoryStructure
  //create new directory structure in new installation
  //*********


  //test data structures

  //all camera makes
  //***********
  test("allCameraMakes") {
    rb.allCameraMakes should be(cameraMakes)
  }

  //allCameraModels
  //*********

  test("allCameraModels - negative test - white spaces in camera make") {
    rb.allCameraModels("LEICA ").distinct should be(Seq("D-LUX 3"))
  }

  test("allCameraModels - LEICA") {
      rb.allCameraModels("LEICA").distinct should be(Seq("D-LUX 3"))
  }

  test("allCameraModels - NIKON CORPORATION") {
    rb.allCameraModels("NIKON CORPORATION").distinct should be(Seq("NIKON D80"))
  }

  test("allCameraModels - Canon ") {
    rb.allCameraModels("Canon").distinct should be(Seq("Canon EOS 20D","Canon EOS 400D DIGITAL"))
  }

  test("allCameraModels - FUJIFILM ") {
    rb.allCameraModels("FUJIFILM").distinct should be(Seq("FinePix S6500fd"))
  }

  //FUJI PHOTO FILM CO., LTD.
  test("allCameraModels - FUJI PHOTO FILM CO., LTD.") {
    rb.allCameraModels("FUJI PHOTO FILM CO., LTD.").distinct should be(Seq("SLP1000SE"))
  }

  //Panasonic
  test("allCameraModels - Panasonic") {
    rb.allCameraModels("Panasonic").distinct should be(Seq("DMC-FZ30"))
  }

  //cameraMakeThumbNails
  //get thumbnails for a camera make
  //*********
  test("cameraMakeThumbNails - Panasonic - Count Number of Elements") {
    //it's 6 elements because Panasonic appears 2 times, with 3 differently sized thumbnails
    rb.cameraMakeThumbNails("Panasonic").length should be( 6 )
  }

  test("cameraMakeThumbNails - FUJI PHOTO FILM CO., LTD. - Count Number of Elements") {
    //it's 6 elements because Panasonic appears 2 times, with 3 differently sized thumbnails
    rb.cameraMakeThumbNails("FUJI PHOTO FILM CO., LTD.").length should be( 3 )
  }

  test("cameraMakeThumbNails - FUJIFILM - Count Number of Elements") {
    //it's 6 elements because Panasonic appears 2 times, with 3 differently sized thumbnails
    rb.cameraMakeThumbNails("FUJIFILM").length should be( 3 )
  }

  test("cameraMakeThumbNails - Canon - Count Number of Elements") {
    //it's 6 elements because Panasonic appears 2 times, with 3 differently sized thumbnails
    rb.cameraMakeThumbNails("Canon").length should be( 6 )
  }

  test("cameraMakeThumbNails - NIKON CORPORATION - Count Number of Elements") {
    //it's 6 elements because Panasonic appears 2 times, with 3 differently sized thumbnails
    rb.cameraMakeThumbNails("FUJI PHOTO FILM CO., LTD.").length should be( 3 )
  }

  test("cameraMakeThumbNails - LEICA - Count Number of Elements") {
    //it's 6 elements because Panasonic appears 2 times, with 3 differently sized thumbnails
    rb.cameraMakeThumbNails("LEICA").length should be( 15 )
  }

  test("cameraMakeThumbNails - NONE - Count Number of Elements") {
    //it's 6 elements because Panasonic appears 2 times, with 3 differently sized thumbnails
    rb.cameraMakeThumbNails("None").length should be( 6 )
  }

  //allIndexThumbnails
  //needed because not all works contain a camera make. requirement is to grab first 10 thumbnail-works, but doesn't
  //mention whether a camera created the photo
  //*********
  test("allIndexThumbnails - NONE - Count Number of Elements") {
    rb.allIndexThumbnails.length should be( 42 )
  }



}