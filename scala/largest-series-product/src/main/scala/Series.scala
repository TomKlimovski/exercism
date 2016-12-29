object Series {

  def sList(s: String): Seq[Int] = s map (_.asDigit)

  def slices(size: Int, str: String): Option[Seq[Seq[Int]]] = {
    val digitSliding: String => Seq[Seq[Int]] =
      sList(_) sliding size toSeq

    Option(str).filter(_.length >= size).map(digitSliding)
  }

  def largestProduct(size: Int, digitStr: String): Option[Int] = {
    def getLarge(sSeq: Seq[Seq[Int]]) =
      sSeq map (_.product) max

    if (size == 0)
      Some(1)
    else
      slices(size, digitStr) map getLarge
  }

}