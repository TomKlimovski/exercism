

object sandbox {
  val tom = <a>this is tom</a>

  println(s"Does it work? $tom")

  val sets: Seq[String] = Seq("Sets", "Usual")

  sets.zipWithIndex

  val tb = Thumbnail("this", "sucks")

  case class Thumbnail(size: String, url: String)

  tb.size

  def getTB(tb: Thumbnail): Seq[Thumbnail] = {
    Seq(Thumbnail("this", "sucks"), Thumbnail("yes", "does"))
  }

  val nails = Thumbnail("small","http://ih1.redbubble.net/work.31820.1.flat,135x135,075,f.jpg")

  nails.url.split(",")(1).split('x')

}