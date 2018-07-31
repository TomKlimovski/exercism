

object PythagoreanTriplet {
  def pythagoreanTriplets(lower: Int, upper: Int): Seq[(Int, Int, Int)] = {
    buildCombos(lower, upper).filter(x => (x._1 < x._2) && (x._2 < x._3) && isPythagorean(x._1, x._2, x._3))
  }

  def isPythagorean(tuple: (Int, Int, Int)): Boolean = {
    val aSquared = squared(tuple._1)
    val bSquared = squared(tuple._2)
    val cSquared = squared(tuple._3)

    val tupleList: List[Int] = tuple.productIterator.toList.map(_.toString.toInt).sorted
//    println(s"three ${tupleList.sorted}")

//    val answer = squared(tuple._1) + squared(tuple._2)  = squared(tuple._3)
//    println(s"a: $tupleList")
    ~=(squared(tupleList(0)) + squared(tupleList(1)), squared(tupleList(2)), 0.0001)

  }

  def ~=(x: Double, y: Double, precision: Double) = {
    if ((x - y).abs < precision) true else false
  }

  def squared: PartialFunction[Int, Double] ={
    case d: Int if d > 0 => scala.math.pow(d, 2)
  }


  def buildCombos(lower: Int, upper: Int): Seq[(Int, Int, Int)] = {
    (lower to upper).flatMap { x =>
      (lower to upper).flatMap { y =>
        (lower to upper).map { z => (x, y, z)}}}
  }
}

PythagoreanTriplet.pythagoreanTriplets(11, 20)
