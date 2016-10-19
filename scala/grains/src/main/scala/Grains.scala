import scala.language.postfixOps
import scala.annotation.tailrec

object Grains {

  def square(i: Int): BigInt = {
    @tailrec
    def calcSquare(sq: Int, acc: BigInt): BigInt = {
      if(sq == 1) acc else calcSquare(sq-1, acc*2)
    }
    calcSquare(i, 1)
}
  def total: BigInt = (1 to 64) map square sum

}