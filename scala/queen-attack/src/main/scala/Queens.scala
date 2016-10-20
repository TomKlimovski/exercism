case class Queens() {

  private def multiTable() = {
    val tableSeq =
      for(row <- 1 to 8)
        yield "_ _ _ _ _ _ _ _"

    tableSeq.mkString("\n")
  }

  def boardString(x: Option[Position], y: Option[Position]) = {
    (multiTable)
  }

  def canAttack(pos1: Position, pos2: Position) = {
    true
  }
}

case class Position(x: Int, y: Int) {

}