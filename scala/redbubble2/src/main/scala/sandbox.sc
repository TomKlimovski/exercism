

object sandbox {

  val tom = <a>this is tom</a>

  println(s"Where is he? $tom so it OWKRS!")

  val yearMade = 1995

  val t = <a>
    {if (yearMade < 2000) <old>
      {yearMade}
    </old>
    else xml.NodeSeq.Empty}
  </a>
  val joe = <employee
    name="Joe"
    rank="code monkey"
    serial="123"/>

  t \ "old"

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
}