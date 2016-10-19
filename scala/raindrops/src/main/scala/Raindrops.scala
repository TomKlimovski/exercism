case class Raindrops() {
  val factors = List((3, "Pling"),(5, "Plang"),(7, "Plong"))

  def convert(i: Int) = {
    val divs = factors.map{case(x, y) => if(i%x == 0) y else ""}
    val finalList = divs.filterNot(_.isEmpty)
    if(finalList.nonEmpty) finalList.mkString else i.toString
  }
}