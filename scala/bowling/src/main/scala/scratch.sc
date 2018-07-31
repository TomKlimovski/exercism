sealed trait Bowling {
  def roll(pins: Int): Bowling

  def score(): Either[String, Int]
}

object Bowling {
  def apply(): Bowling = {
//    println("Applied the Bowling")
    BowlingStart
  }
  private val BowlingStart = {
//    println("It's time to start bowling!")
    BowlingScore(List(Frame(List(), false)))
  }

}

case class BowlingScore(frames: List[Frame]) extends Bowling {
  override def roll(pins: Int): Bowling = {

    def addFillBall(f: Frame, fs: List[Frame]): Bowling = {
      val List(firstRoll, secondRoll) = f.rolls
      println(s"Fill Ball ${firstRoll}, ${secondRoll}, ${pins}, ${f.spare}, ${f.throws}")
      if ( (firstRoll >= 10 && secondRoll >= 10 && pins >= 10) )
        BowlingError("Too many pins")
      else
        BowlingScore(f.addRoll(pins)::fs)
    }

    val addToFinalFrame: PartialFunction[List[Frame], Bowling] = {
      case (f@Frame(ts, true))::fs if ts.length < 2 => {
//        println(s"""final frame -> $f""")
        BowlingScore(f.addRoll(pins) :: fs)
      }
      case (f@Frame(ts, true))::fs if f.throws == 2 && (ts.head == 10 || f.spare) => {
        println(s""" fill ball $f""")
        addFillBall(f, fs)
      }
      case Frame(ts, true)::_ =>
        BowlingError("no more rolls possible")

    }

    val addRollToStandardFrame: PartialFunction[List[Frame], Bowling] = {
      //rolls complete, close off frame
      case f::_ if f.complete => {
//        println(s"""Complete! ${pins}""")
        BowlingScore(Frame(List(pins), frames.length == 9)::frames)
      }
      case f::fs if(f.pins + pins <= 10 )=> {
//        println(s"Just a normal roll ${pins} AND ${f.pins}")
        BowlingScore(f.addRoll(pins) :: fs)
      }
    }

    val badRoll: PartialFunction[List[Frame], Bowling] = {
      case _ => {
//        println("Is this an error?")
        BowlingError("Bad Roll")
      }
    }

    if(pins < 0 || pins > 10 || frames.length > 10) {
      BowlingError(s"Wrong number of pins -> $pins")
    }
    else
      (addToFinalFrame orElse addRollToStandardFrame orElse badRoll)(frames)
  }

  override def score(): Either[String, Int] = {
    println(s"""Frames -> ${frames}""")
    if (frames.length < 10  || frames.exists(!_.complete)) Left("Invalid Bowl")
    else {
      val theScore: Int = frames.reverse.tails.take(10).foldLeft(0) {
        (score, frames) =>
          //        if (frames.length != 0) {
          //          println(s""" frames score -> ${frames.head}""")
          score + frames.head.score(frames.tail)
        //        } else score

      }
      Right(theScore)
    }

  }

}

case class BowlingError(Error: String) extends Bowling {
  override def roll(pins: Int): BowlingError = this

  override def score(): Either[String, Nothing] = Left(Error)
}

case class Frame(rolls: List[Int], finalFrame: Boolean) {
  def addRoll(roll: Int): Frame = {
    Frame(rolls :+ roll, finalFrame)
  }

  val pins: Int = rolls.sum
  val throws = rolls.length
  val allPinsDown = pins == 10

  val strike: Boolean = throws == 1 && allPinsDown
  val spare: Boolean = throws == 2 && allPinsDown

  def complete: Boolean =
    if (!finalFrame) strike || throws == 2
    else {
      if (rolls.head == 10 || rolls.take(2).sum == 10) throws == 3
      else throws == 2
    }

  def score(nextFrame: List[Frame]): Int = {

    val bonus: Int = {
      if(finalFrame) 0
      else {
        val nextRoll = nextFrame(0).rolls.head
        val nextNextRoll: Int = nextFrame(0).rolls.tail.headOption getOrElse
          nextFrame(1).rolls.head
//        println(s"""nextNextRoll -> ${nextNextRoll} frame length -> ${nextFrame.length}""")

        if (spare) nextRoll
        else if (strike) nextRoll + nextNextRoll
        else 0
      }

    }
//    println(s"""bonus $bonus pins $pins""")
    pins + bonus
  }
}

val score = List(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 10).foldLeft(Bowling())((acc, roll) => acc.roll(roll)).score()
