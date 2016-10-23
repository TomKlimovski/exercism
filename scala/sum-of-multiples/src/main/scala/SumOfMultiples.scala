object SumOfMultiples {
  def sumOfMultiples(ls: List[Int], end: Int) = {
    (1 until end).filter(x => ls.exists(i => x%i==0)).sum
  }
}
