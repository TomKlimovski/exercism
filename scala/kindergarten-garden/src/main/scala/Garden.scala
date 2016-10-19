
case class Garden(gl: List[String], str: String) {

  def sections(num: Int) = if(num <= 0) 0 else if(num%2==0)num/2 else ((num+1)/2)

  //Map(Name -> Column)
  val children = if(gl.isEmpty) Map("Alice" -> 0, "Bob" -> 1, "Charlie" -> 2, "David" -> 3,
                      "Eve" -> 4, "Fred" -> 5, "Ginny" -> 6, "Harriet" -> 7,
                      "Ileana" -> 8, "Joseph" -> 9, "Kincaid" -> 10, "Larry" -> 11)
                else gl.sorted.zipWithIndex.toMap
  val plants = Map('R' -> Plant.Radishes, 'C' -> Plant.Clover, 'G' -> Plant.Grass, 'V' -> Plant.Violets)

  def this() = this(List.empty, "")

  def getPlants(name: String) = {
    val gardenPairs = str.split('\n')

    val firstGarden = {for((s, i) <- gardenPairs(0).zipWithIndex) yield {
      plants(s) -> sections(i-1)
    }}.toList
    val secondGarden = {for((s, i) <- gardenPairs(1).zipWithIndex) yield {
      plants(s) -> sections(i-1)
    }}.toList

    val nameSection: Int = children.getOrElse(name, -1)
    val fg = firstGarden.filter(_._2 == nameSection)
    val sg = secondGarden.filter(_._2 == nameSection)
    val result = (fg ::: sg).map(_._1).toList
    if(nameSection == -1) List.empty else result
  }

}

object Plant {
  sealed trait Plant {}
  case object Radishes extends Plant {}
  case object Clover extends Plant {}
  case object Grass extends Plant {}
  case object Violets extends Plant {}
}

object Garden {
  def defaultGarden(str: String) = new Garden(List.empty, str)
}