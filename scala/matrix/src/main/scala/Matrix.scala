
class Matrix(matrix: String) {

  lazy val transformed = (matrix split("\n"))

  def rows(idx: Int): Seq[Int] =
    (transformed(idx).split(" ").map{_.toInt})

  def cols(idx: Int): Seq[Int] = {
    val line = for{lines <- transformed} yield {lines.split(" ").map{_.toInt}}
    val rowToColumn = line.transpose
    rowToColumn(idx)
  }

  override def toString = f"$matrix"
  override def equals(obj: Any) = obj.isInstanceOf[Matrix] && obj.asInstanceOf[Matrix].toString == this.toString
}

object Matrix {

  def apply(matrix: String) = new Matrix(matrix)
}