
object sandbox {
  val tom = <a>this is tom</a>

  println(s"Does it work? $tom")

  val url = "http://take-home-test.herokuapp.com/api/v1/works.xml"
  val fileString = scala.io.Source.fromURL(url).mkString

  val map: Map[String, String] = Map("Leica" -> "Lux", "Seik" -> "Lux")

  map.groupBy(_._2).mapValues(_.keys.toSet)

  val node = scala.xml.XML.loadString(fileString)

  for {n <- node.child
       if (!((n \\ "make").text.trim.isEmpty))
  } yield {
    println((n \\ "model").text + " - " +  (n \\ "make").text)
  }
}