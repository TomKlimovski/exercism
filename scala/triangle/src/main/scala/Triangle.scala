
object TriangleType extends Enumeration {
  type TriangleType = Value
  val Equilateral, Isosceles, Scalene, Illogical = Value
}

case class Triangle(a: Int, b: Int, c: Int) {
  private def valid = List(a, b, c).forall(_ > 0) && (a + b > c) && (b + c > a) && (a + c > b)

  def triangleType = {
    if(!valid) TriangleType.Illogical
    else if(a == b && b == c) TriangleType.Equilateral
    else if(a == c || b == c) TriangleType.Isosceles
    else TriangleType.Scalene
  }
}



