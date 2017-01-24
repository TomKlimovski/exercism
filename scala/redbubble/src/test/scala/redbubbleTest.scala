
import org.scalatest.selenium.{HtmlUnit}
import org.scalatest.{FunSuite, Matchers}
import com.typesafe.config.ConfigFactory

import scala.xml.{Elem, XML}

class redbubble_Index_Pages_Test extends FunSuite with Matchers with HtmlUnit {

  val url = ConfigFactory.load().getConfig("redbubble").getString("API_URL")

  val node: Elem = XML.loadString(Redbubble.getFileFromUrl(url))
  val rb = new Redbubble(node)

  val cameraMakes: Seq[String] = Seq("NIKON CORPORATION", "Canon", "FUJIFILM", "None", "LEICA",
    "FUJI PHOTO FILM CO., LTD.", "Panasonic")

  val _BaseDir = ConfigFactory.load().getConfig("redbubble").getString("base_dir")
  val BaseDir = if (_BaseDir.trim.isEmpty) System.getProperty("user.dir") else _BaseDir
  val TargetHtml = ConfigFactory.load().getConfig("redbubble").getString("html_path")
  val CameraMakePath = ConfigFactory.load().getConfig("redbubble").getString("camera_make_path")
  val CameraModelPath = ConfigFactory.load().getConfig("redbubble").getString("camera_model_path")
  //file:///Users/tomklimovski/exercism/scala/redbubble2/target/HTML/index.html
  val host = "file://" + BaseDir + TargetHtml

  val NumberOfWorktoDisplay = ConfigFactory.load().getConfig("redbubble").getInt("number_of_work_to_display")

  //  The index page must contain:
  //    Thumbnail images for the first 10 work;
  //    Navigation that allows the user to browse to all camera makes.
  //***********
  //index web pages
  //***********
  test("The index page should have the correct title") {
    go to (host + "/index.html")
    pageTitle should be("Navigation for Works")
  }
  test("Go to Index.html") {
    go to (host + "/index.html")
    currentUrl should be("file:" + BaseDir + TargetHtml + "/index.html")
  }
  test("Check all Camera Makes Exist on Index Page") {
    for (cameraTitle <- cameraMakes if (cameraTitle != "None")) {
      go to (host + "/index.html")
      //      Console.err.println(s"Clicking on $cameraTitle")
      click on linkText(cameraTitle)
    }
  }
  test("Check all thumbnails exist on Index Page") {
    go to (host + "/index.html")
    //val path: Option[Element] = find(xpath("//img"))
    val eles: Iterator[Element] = findAll(xpath("//img"))
    val nums = (eles map { x => 1 }).size
    val validCondition: Boolean = nums <= NumberOfWorktoDisplay && nums > 0

    //Console.err.println(s"Number of thumbnails: ${nums}")
    validCondition should be(true)
  }
}

class redbubble_CameraMake_Pages_Test extends FunSuite with Matchers with HtmlUnit {
  val url = ConfigFactory.load().getConfig("redbubble").getString("API_URL")

  val node: Elem = XML.loadString(Redbubble.getFileFromUrl(url))
  val rb = new Redbubble(node)

  val cameraMakes: Seq[String] = Seq("NIKON CORPORATION", "Canon", "FUJIFILM", "None", "LEICA",
    "FUJI PHOTO FILM CO., LTD.", "Panasonic")

  val _BaseDir = ConfigFactory.load().getConfig("redbubble").getString("base_dir")
  val BaseDir = if (_BaseDir.trim.isEmpty) System.getProperty("user.dir") else _BaseDir
  val TargetHtml = ConfigFactory.load().getConfig("redbubble").getString("html_path")
  val CameraMakePath = ConfigFactory.load().getConfig("redbubble").getString("camera_make_path")
  val CameraModelPath = ConfigFactory.load().getConfig("redbubble").getString("camera_model_path")
  //file:///Users/tomklimovski/exercism/scala/redbubble2/target/HTML/index.html
  val host = "file://" + BaseDir + TargetHtml

  val NumberOfWorktoDisplay = ConfigFactory.load().getConfig("redbubble").getInt("number_of_work_to_display")
  //  Each camera make HTML page must contain:
  //    Thumbnail images of the first 10 works for that camera make;
  //    Navigation that allows the user to browse to the index page and to all camera models of that make.
  //***********
  //CameraMake web pages
  //***********
  test("Check all CameraMake pages exist") {
    for (cameraTitle <- cameraMakes if (cameraTitle != "None")) {
      go to (host + CameraMakePath + s"/$cameraTitle.html")
      currentUrl should be("file:" + BaseDir + TargetHtml + CameraMakePath + s"/$cameraTitle.html")
    }
  }
  test("Check all Camera Models Exist on CameraMake Page") {
    //pending
    for (cameraTitle <- cameraMakes if (cameraTitle != "None")) {
      go to (host + CameraMakePath + s"/$cameraTitle.html")
      val models = rb.allCameraModels(cameraTitle).distinct
      //      Console.err.println(s"Checking $models")
      for (model <- models) {
        go to (host + CameraMakePath + s"/$cameraTitle.html")
        click on linkText(model)
      }
    }
  }
  test("Check all thumbnails exist on CameraMake Page") {
    //pending
    for (cameraTitle <- cameraMakes if (cameraTitle != "None")) {
      go to (host + CameraMakePath + s"/$cameraTitle.html")
      val eles: Iterator[Element] = findAll(xpath("//img"))
      val nums = (eles map { x => 1 }).size
      val validCondition: Boolean = nums <= NumberOfWorktoDisplay && nums > 0

      //      Console.err.println(s"Number of thumbnails: ${nums}")
      validCondition should be(true)
    }
  }
  test("Check Index link exists for CameraMake") {
    //pending
    for (cameraTitle <- cameraMakes if (cameraTitle != "None")) {
      go to (host + CameraMakePath + s"/$cameraTitle.html")
      click on linkText("Index")
    }
  }
}

class redbubble_CameraModel_Pages_Test extends FunSuite with Matchers with HtmlUnit {
  val url = ConfigFactory.load().getConfig("redbubble").getString("API_URL")

  val node: Elem = XML.loadString(Redbubble.getFileFromUrl(url))
  val rb = new Redbubble(node)

  val cameraMakes: Seq[String] = Seq("NIKON CORPORATION", "Canon", "FUJIFILM", "None", "LEICA",
    "FUJI PHOTO FILM CO., LTD.", "Panasonic")

  val _BaseDir = ConfigFactory.load().getConfig("redbubble").getString("base_dir")
  val BaseDir = if (_BaseDir.trim.isEmpty) System.getProperty("user.dir") else _BaseDir
  val TargetHtml = ConfigFactory.load().getConfig("redbubble").getString("html_path")
  val CameraMakePath = ConfigFactory.load().getConfig("redbubble").getString("camera_make_path")
  val CameraModelPath = ConfigFactory.load().getConfig("redbubble").getString("camera_model_path")
  //file:///Users/tomklimovski/exercism/scala/redbubble2/target/HTML/index.html
  val host = "file://" + BaseDir + TargetHtml

  val NumberOfWorktoDisplay = ConfigFactory.load().getConfig("redbubble").getInt("number_of_work_to_display")

  //  Thumbnail images of all works for that camera make and model;
  //  Navigation that allows the user to browse to the index page and the camera make.
  //***********
  //CameraModel web pages
  //***********
  test("Check all CameraModel pages exist") {
    for (model <- rb.allCameraModels) {
      go to (host + CameraModelPath + s"/$model.html")
      //      Console.err.println(s"Model: $model")
      currentUrl should be("file:" + BaseDir + TargetHtml + CameraModelPath + s"/$model.html")
    }
  }
  test("Check all thumbnails exist on CameraModel Page") {
    //pending
    for (model <- rb.allCameraModels) {
      go to (host + CameraModelPath + s"/$model.html")
      val eles: Iterator[Element] = findAll(xpath("//img"))
      val nums = (eles map { x => 1 }).size
      val validCondition: Boolean = nums <= NumberOfWorktoDisplay // && nums > 0

      //      Console.err.println(s"Number of thumbnails: ${nums} for Model: $model")
      validCondition should be(true)
    }
  }
  test("Check Index link exists for CameraModel") {
    //pending
    for (model <- rb.allCameraModels) {
      go to (host + CameraModelPath + s"/$model.html")
      click on linkText("Index")
    }
  }
  test("Check link to Camera Make exists") {
    //pending
    for (cameraTitle <- cameraMakes if (cameraTitle != "None")) {
      val models = rb.allCameraModels(cameraTitle).distinct
      for (model <- models) {
        go to (host + CameraModelPath + s"/$model.html")
        click on linkText(cameraTitle)
      }
    }
  }
}

class redbubble_File_Connections_Test extends FunSuite with Matchers with HtmlUnit {
  val url = ConfigFactory.load().getConfig("redbubble").getString("API_URL")

  val node: Elem = XML.loadString(Redbubble.getFileFromUrl(url))
  val rb = new Redbubble(node)

  val cameraMakes: Seq[String] = Seq("NIKON CORPORATION", "Canon", "FUJIFILM", "None", "LEICA",
    "FUJI PHOTO FILM CO., LTD.", "Panasonic")

  val _BaseDir = ConfigFactory.load().getConfig("redbubble").getString("base_dir")
  val BaseDir = if (_BaseDir.trim.isEmpty) System.getProperty("user.dir") else _BaseDir
  val TargetHtml = ConfigFactory.load().getConfig("redbubble").getString("html_path")
  val CameraMakePath = ConfigFactory.load().getConfig("redbubble").getString("camera_make_path")
  val CameraModelPath = ConfigFactory.load().getConfig("redbubble").getString("camera_model_path")
  //file:///Users/tomklimovski/exercism/scala/redbubble2/target/HTML/index.html
  val host = "file://" + BaseDir + TargetHtml

  val NumberOfWorktoDisplay = ConfigFactory.load().getConfig("redbubble").getInt("number_of_work_to_display")
  //***********
  //test file-connections
  //***********
  test("can't find URL file -> notExist") {
    Redbubble.getFileFromUrl("notExist") should include("Error")
  }
  test("can't find URL file -> malformed URL") {
    val badURL = ConfigFactory.load().getConfig("test").getString("Bad_API_URL")
    Redbubble.getFileFromUrl(badURL) should include("Error")
  }
  test("successful XML file read") {
    //pending
    Redbubble.getFileFromUrl(url) should include("xml version")
  }
  test("unsuccessful XML read") {
    Redbubble.getFileFromUrl("http://www.redbubble.com/") should include("Error")
  }
}

class redbubble_write_to_file_Test extends FunSuite with Matchers with HtmlUnit {
  val url = ConfigFactory.load().getConfig("redbubble").getString("API_URL")

  val node: Elem = XML.loadString(Redbubble.getFileFromUrl(url))
  val rb = new Redbubble(node)

  val cameraMakes: Seq[String] = Seq("NIKON CORPORATION", "Canon", "FUJIFILM", "None", "LEICA",
    "FUJI PHOTO FILM CO., LTD.", "Panasonic")

  val _BaseDir = ConfigFactory.load().getConfig("redbubble").getString("base_dir")
  val BaseDir = if (_BaseDir.trim.isEmpty) System.getProperty("user.dir") else _BaseDir
  val TargetHtml = ConfigFactory.load().getConfig("redbubble").getString("html_path")
  val CameraMakePath = ConfigFactory.load().getConfig("redbubble").getString("camera_make_path")
  val CameraModelPath = ConfigFactory.load().getConfig("redbubble").getString("camera_model_path")
  //file:///Users/tomklimovski/exercism/scala/redbubble2/target/HTML/index.html
  val host = "file://" + BaseDir + TargetHtml

  val NumberOfWorktoDisplay = ConfigFactory.load().getConfig("redbubble").getInt("number_of_work_to_display")
  //writeFile
  //def writeFile(path: String, stringToWrite: String)
  //*********
  test("writeFile - bad path") {
    rb.writeFile("/User/tomklimovski/DoesntExist", "fileName", "BadPath") should be(false)
  }
  test("writeFile - good path") {
    rb.writeFile(BaseDir, "/test.html", "topPath") should be(true)
  }
  test("createDirectoryStructure - bad Path") {
    rb.createDirectoryStructure should be(false)
  }
}

class redbubble_data_structures_Test extends FunSuite with Matchers with HtmlUnit {
  val url = ConfigFactory.load().getConfig("redbubble").getString("API_URL")

  val node: Elem = XML.loadString(Redbubble.getFileFromUrl(url))
  val rb = new Redbubble(node)

  val cameraMakes: Seq[String] = Seq("NIKON CORPORATION", "Canon", "FUJIFILM", "None", "LEICA",
    "FUJI PHOTO FILM CO., LTD.", "Panasonic")

  val _BaseDir = ConfigFactory.load().getConfig("redbubble").getString("base_dir")
  val BaseDir = if (_BaseDir.trim.isEmpty) System.getProperty("user.dir") else _BaseDir
  val TargetHtml = ConfigFactory.load().getConfig("redbubble").getString("html_path")
  val CameraMakePath = ConfigFactory.load().getConfig("redbubble").getString("camera_make_path")
  val CameraModelPath = ConfigFactory.load().getConfig("redbubble").getString("camera_model_path")
  //file:///Users/tomklimovski/exercism/scala/redbubble2/target/HTML/index.html
  val host = "file://" + BaseDir + TargetHtml

  val NumberOfWorktoDisplay = ConfigFactory.load().getConfig("redbubble").getInt("number_of_work_to_display")
  //test data structures
  //***********
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
    rb.allCameraModels("Canon").distinct should be(Seq("Canon EOS 20D", "Canon EOS 400D DIGITAL"))
  }
  test("allCameraModels - FUJIFILM ") {
    rb.allCameraModels("FUJIFILM").distinct should be(Seq("FinePix S6500fd"))
  }
  test("allCameraModels - FUJI PHOTO FILM CO., LTD.") {
    rb.allCameraModels("FUJI PHOTO FILM CO., LTD.").distinct should be(Seq("SLP1000SE"))
  }
  test("allCameraModels - Panasonic") {
    rb.allCameraModels("Panasonic").distinct should be(Seq("DMC-FZ30"))
  }

  //cameraMakeThumbNails
  //get thumbnails for a camera make
  //*********
  test("cameraMakeThumbNails - Panasonic - Count Number of Elements") {
    //it's 6 elements because Panasonic appears 2 times, with 3 differently sized thumbnails
    rb.cameraMakeThumbNails("Panasonic").length should be(6)
  }
  test("cameraMakeThumbNails - FUJI PHOTO FILM CO., LTD. - Count Number of Elements") {
    //it's 6 elements because Panasonic appears 2 times, with 3 differently sized thumbnails
    rb.cameraMakeThumbNails("FUJI PHOTO FILM CO., LTD.").length should be(3)
  }
  test("cameraMakeThumbNails - FUJIFILM - Count Number of Elements") {
    //it's 6 elements because Panasonic appears 2 times, with 3 differently sized thumbnails
    rb.cameraMakeThumbNails("FUJIFILM").length should be(3)
  }
  test("cameraMakeThumbNails - Canon - Count Number of Elements") {
    //it's 6 elements because Panasonic appears 2 times, with 3 differently sized thumbnails
    rb.cameraMakeThumbNails("Canon").length should be(6)
  }
  test("cameraMakeThumbNails - NIKON CORPORATION - Count Number of Elements") {
    //it's 6 elements because Panasonic appears 2 times, with 3 differently sized thumbnails
    rb.cameraMakeThumbNails("FUJI PHOTO FILM CO., LTD.").length should be(3)
  }
  test("cameraMakeThumbNails - LEICA - Count Number of Elements") {
    //it's 6 elements because Panasonic appears 2 times, with 3 differently sized thumbnails
    rb.cameraMakeThumbNails("LEICA").length should be(15)
  }
  test("cameraMakeThumbNails - NONE - Count Number of Elements") {
    //it's 6 elements because Panasonic appears 2 times, with 3 differently sized thumbnails
    rb.cameraMakeThumbNails("None").length should be(6)
  }

  //allIndexThumbnails
  //needed because not all works contain a camera make. requirement is to grab first 10 thumbnail-works, but doesn't
  //mention whether a camera created the photo
  //*********
  test("allIndexThumbnails - NONE - Count Number of Elements") {
    rb.allIndexThumbnails.length should be(42)
  }
}