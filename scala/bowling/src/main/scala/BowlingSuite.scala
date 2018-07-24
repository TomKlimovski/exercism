sealed trait Bowling {
  def roll(pins: Int): Bowling

  def score(): Either[Error, Int]
}

object Bowling {
  def apply(): Bowling = {
    BowlingStart
  }
  private val BowlingStart = {
    BowlingScore(List(Frame(List(), false)))
  }

}

case class BowlingScore(frames: List[Frame]) extends Bowling {
  override def roll(pins: Int): Bowling = {

    val addRollToStandardFrame: PartialFunction[List[Frame], Bowling] = {
      case f::_ if f.complete => {
        //        println(s"""first case! $f""")
        BowlingScore(Frame(List(pins), false)::frames)
      }
      case f::fs => {
        BowlingScore(f.addRoll(pins) :: fs)
      }
    }

    (addRollToStandardFrame)(frames)
  }

  override def score(): Either[Error, Int] = {
    val theScore: Int = frames.foldLeft(0) {
      (score, frames) =>
        score + frames.pins
    }
    Right(theScore)
  }

}

case class Frame(rolls: List[Int], finalFrame: Boolean) {
  def addRoll(roll: Int): Frame = {
    Frame(rolls :+ roll, false)
  }

  val pins: Int = rolls.sum
  val throws = rolls.length
  val allPinsDown = pins == 10

  val strike: Boolean = throws == 1 && allPinsDown
  val spare: Boolean = throws == 2 && allPinsDown

  val complete: Boolean = {
    throws == 2
  }

}
