import scala.annotation.tailrec

case class RomanNumeral(num: Int) {

  //moved 4 and 9 logic into the Map values
  private lazy val romanMap = Map(1 -> "I", 4 -> "IV", 5 -> "V", 9 -> "IX", 10 -> "X",
    40 -> "XL", 50 -> "L", 90 -> "XC", 100 -> "C", 400 -> "CD", 500 -> "D",
    900 -> "CM", 1000 -> "M")

  private lazy val sortedKeyRoman = collection.immutable.SortedSet[Int]() ++ romanMap.keySet

  @tailrec
  private def buildNum(x: Int, y: List[String]): List[String] = {
    if(x == 0)
      y
    else {
      //find romanNumber
      val findValue = sortedKeyRoman.map{case a if(a <= `x`) => a case _ => 0}.last
      //find roman equivalent for decimal to subtract for next iteration
      val findNum = romanMap getOrElse(findValue, "")
      buildNum(x-findValue, findNum :: y)
    }
  }

  private lazy val numList = num.toString.toList

  //this breaks out numbers into decimal units e.g. 924 becomes (900, 20, 4)
  private lazy val extractUnits =
    for((x,i) <- numList.reverse.zipWithIndex) yield {
    (x + "0"*i).toInt
    }

  def value = extractUnits.map{buildNum(_, List())}.flatten.reverse.mkString
}


