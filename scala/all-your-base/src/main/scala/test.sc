
object sandbox {
  val tom = <a>this is tom</a>

  println(s"Does it work? $tom")

  val url = "http://take-home-test.herokuapp.com/api/v1/works.xml"
  scala.io.Source.fromURL(url).mkString
}