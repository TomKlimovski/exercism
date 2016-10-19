import scala.math.pow

object AllYourBase {

  implicit class PowerInt(i: Int) {def ** (b: Int): Int = pow(i, b).intValue}

  def rebase(oldBase: Int, number: List[Int], newBase: Int) = {
    def base10toAny(i: Int, ls: List[Int], b: Int): List[Int] = {
      if(i == 0) ls
      else base10toAny(i/b, i%b :: ls, b)
    }

    lazy val anyTobase10 = number.reverse.zipWithIndex.foldLeft(0)((acc, next) => {
      val (num, i) = next
      acc + (num*(oldBase**(i)))
    })

    val notValid = if(oldBase ==1 || newBase==1 || oldBase ==0 || newBase==0 || oldBase <0 ||
                        newBase<0 || number.contains(-1) || (!number.forall(_ < oldBase))) true else false

    if(notValid)
      None
    else {
      val nextBase = base10toAny(anyTobase10, List.empty, newBase)
      Some(nextBase)
    }
  }
}