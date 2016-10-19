object SumOfMultiples {
  def sumOfMultiples(ls: List[Int], end: Int) = {
    (for(i <- 1 until end
      if(ls.exists(x => (i % x) == 0))) yield i
      ).sum
  }
}