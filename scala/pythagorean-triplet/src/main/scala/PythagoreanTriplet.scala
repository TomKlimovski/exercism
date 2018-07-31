object PythagoreanTriplet {
  def pythagoreanTriplets(lower: Int, upper: Int): Seq[(Int, Int, Int)] = {
    buildCombos(lower, upper).filter(x => (x._1 < x._2) && (x._2 < x._3) && isPythagorean(x._1, x._2, x._3))
  }

  def isPythagorean(tuple: (Int, Int, Int)): Boolean = {

    val tupleList: List[Int] = tuple.productIterator.toList.map(_.toString.toInt).sorted

    ~=(squared(tupleList(0)) + squared(tupleList(1)), squared(tupleList(2)), 0.0001)

  }

  def ~=(x: Double, y: Double, precision: Double) = {
    ((x - y).abs < precision)
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