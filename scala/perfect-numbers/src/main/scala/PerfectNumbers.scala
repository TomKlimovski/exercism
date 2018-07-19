
object PerfectNumbers {

  def findFactors(num: Int) = {
    (1 to num).filter { divisor =>
      num % divisor == 0
    }
  }

  def classify(i: Int): Either[String, NumberType.NumberType] = {
    val factors = findFactors(i).dropRight(1)
    val aliquot = factors.foldLeft(0)(_+_)

    if(i <= 0) (Left("Classification is only possible for natural numbers."))
    else if (i == aliquot) (Right(NumberType.Perfect))
    else if (aliquot > i) (Right(NumberType.Abundant))
    else (Right(NumberType.Deficient))

  }

}

object NumberType {
  type value = NumberType
  sealed trait NumberType {}
  case object Perfect extends NumberType {}
  case object Deficient extends NumberType {}
  case object Abundant extends NumberType {}
}