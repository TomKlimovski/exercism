object SumOfMultiples {
  def sumOfMultiples(ls: List[Int], end: Int) = if(ls.forall(_ > end)) 0
  else {
    (1 until end).flatMap{x =>
      for(i <- ls) yield {if(x%i==0 && i != end) x else 0}
    }.distinct.filter(_ != 0).sum
  }
}