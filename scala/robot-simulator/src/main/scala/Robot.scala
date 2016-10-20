case class Robot(direction: Bearing.value, coords: (Int, Int)) {
  private val x = coords._1
  private val y = coords._2
  private val directions = List(Bearing.North, Bearing.East, Bearing.South, Bearing.West)

  private def wrapAround(op: Int) = {
    val idx = directions.indexOf(direction)+op
    if(idx >= directions.size) 0
    else if(idx < 0) directions.size-1
    else idx
  }

  def bearing = direction
  def coordinates = coords

  def advance = direction match {
    case Bearing.North => Robot(direction, (x, y+1))
    case Bearing.South => Robot(direction, (x, y-1))
    case Bearing.East => Robot(direction, (x+1, y))
    case Bearing.West => Robot(direction, (x-1, y))
  }
  def turnRight = {
    Robot(directions(wrapAround(1)), coords)
  }
  def turnLeft = {
    Robot(directions(wrapAround(-1)), coords)
  }

  def simulate(command: String): Robot = {
    command.foldLeft(this) { (acc, e) =>
      e match {
        case 'R' => acc.turnRight
        case 'L' => acc.turnLeft
        case 'A' => acc.advance
      }
    }
  }
}

object Bearing {
  type value = Bearing
  sealed trait Bearing {}
  case object North extends Bearing {}
  case object South extends Bearing {}
  case object East extends Bearing {}
  case object West extends Bearing {}
}