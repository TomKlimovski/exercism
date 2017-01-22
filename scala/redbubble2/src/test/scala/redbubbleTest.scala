import org.scalatest.{FunSuite, Matchers}
import com.typesafe.config.ConfigFactory

import scala.xml.XML

class redbubbleTest extends FunSuite with Matchers {

  val url = ConfigFactory.load().getConfig("test").getString("API_URL")

  val node = XML.loadString(Redbubble.getFileFromUrl(url))
  val rb = new Redbubble(node)
  val URL = "http://take-home-test.herokuapp.com/api/v1/works.xml"

  test("Hello should have tests") {
    val configText1 = ConfigFactory.load().getConfig("test").getString("text1")
    val configText2 = ConfigFactory.load().getConfig("test").getString("text2")
    val configText3 = ConfigFactory.load().getConfig("test").getString("text3")
    println(configText1)
    println(configText2)
    println(configText3)

    true shouldBe true
  }

  //test files
  test("can't find file gracefully") {
    Redbubble.getFileFromUrl("notExist") should be ("NOT FOUND: notExist")
  }

  test("successful file read") {
    //pending
    Redbubble.getFileFromUrl(URL) should include ("xml version")
  }

  //test data structures
  test("test mapping of models to makes") {
    pending
    val node = scala.xml.XML.loadString(Redbubble.getFileFromUrl(URL))
    rb.cameraMap(node) should be (" Map(D-LUX 3 -> LEICA, DMC-FZ30 -> Panasonic, FinePix S6500fd -> FUJIFILM, Canon EOS 20D -> Canon, SLP1000SE -> FUJI PHOTO FILM CO., LTD., NIKON D80 -> NIKON CORPORATION, Canon EOS 400D DIGITAL -> Canon)")
  }

}