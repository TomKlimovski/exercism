class Accumulate() {

  def accumulate[A, B](f: A => B, ls: List[A]) = {
    ls.foldRight(List[B]())((elem, acc) => f(elem) :: acc)
  }
}