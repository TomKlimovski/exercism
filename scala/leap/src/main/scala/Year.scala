
case class Year(year: Int) {
  private def div(n: Int) = year % n == 0
  val isLeap = div(4) && ( !div(100) || div(400))
}