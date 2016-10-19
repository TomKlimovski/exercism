class PhoneNumber(ph: String) {

  val clean = ph.filter(_.isDigit)
  val bad = "0000000000"

  def number = {
    if(clean.size == 11)
      if(clean(0) == '1') clean drop 1
        else bad
    else if(clean.size <= 9) bad
    else clean
  }

  def areaCode = {
    number take 3
  }

  val middle = number.slice(3,6)
  val end = number takeRight(4)

  override def toString = "("+areaCode+")"+" "+middle+"-"+end
}