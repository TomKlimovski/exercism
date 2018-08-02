object Series {

  def slices(i: Int, str: String): List[List[Int]] = {
    str.map(_.asDigit).toList.sliding(i).toList
  }
}