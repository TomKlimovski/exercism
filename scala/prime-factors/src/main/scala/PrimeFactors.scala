import scala.annotation.tailrec

case class PrimeFactors() {
  def primeFactors(num: Long) = {
    @tailrec
    def calcFactors(n: Long, xs: List[Long]): List[Long] = {
      if(n <= 1 )
        xs.reverse
      else {
        val divisor = findDivisor(2, n)
        val excess = n / divisor
        calcFactors(excess, divisor :: xs)
      }
    }

    @tailrec
    def findDivisor(divisor: Int, numToDivide: Long): Int = {
      if(numToDivide % divisor == 0)
        divisor
      else
        findDivisor(divisor+1, numToDivide)
    }
    calcFactors(num, List())
  }
}