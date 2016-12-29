case class Luhn(input: Long) {

  private def luhns: Seq[(Char, Int)] = input.toString.reverse.zipWithIndex

  private def testSecond(num: Int): Boolean = num % 2 != 0

  lazy val addends: Seq[Int] = {luhns map { x =>
    {
      lazy val digit = x._1.asDigit
      lazy val pos = x._2
      lazy val doubleDigit = digit * 2

      if (testSecond(pos)) {
        if (doubleDigit >= 10) doubleDigit - 9
        else doubleDigit
      }
      else digit
    }
  }}.reverse

  lazy val checkDigit: Int = addends last

  lazy val checksum: Int = {addends.sum}%10

  lazy val isValid: Boolean = checksum == 0

  lazy val create: Long = {
    val candidates = (0 to 9) map (input*10 + _)
    val isValidLuhn = Luhn(_:Long).isValid
    candidates filter isValidLuhn head
  }
}
