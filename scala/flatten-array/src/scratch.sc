import scala.reflect.ClassTag
import scala.reflect.runtime.universe._

val x = List(0,1,null, List("Shmoopy", List(null)))

def flatten[T](ls: List[T])(implicit m: TypeTag[T]): List[T] = ls flatMap {
  case i: List[T] => flatten(i)
  case e => List(e).filter(_ != null)

}

flatten(x)