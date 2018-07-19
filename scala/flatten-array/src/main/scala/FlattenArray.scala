
object FlattenArray {

  def flatten[T](ls: List[T]): List[T] = ls flatMap {
    case i: List[T] => flatten(i)
    case e => List(e).filter(_ != null)

  }
}

