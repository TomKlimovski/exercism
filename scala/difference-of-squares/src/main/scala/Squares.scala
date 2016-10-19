import scala.math.pow

case class Squares() {

  implicit class PowerInt(i: Int) {def ** (b: Int): Int = pow(i, b).intValue}

  def squareOfSums(i: Int) = (1 to i).sum.**(2)
  def sumOfSquares(i: Int) = (1 to i).map(_.**(2)).sum
  def difference(i: Int) = squareOfSums(i) - sumOfSquares(i)

}