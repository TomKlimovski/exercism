import Chessboard._

case class Queens() {
  type Queen = Option[Position]

  def boardString(white: Queen, black: Queen) = {
    def getSquare(posi: Position) = {
      def theSquare(queen: Queen, square: Square) = {
        queen filter (_ == posi) map (x => square)
      }
      theSquare(white, 'W') orElse theSquare(black, 'B') getOrElse '_'
    }
    val squares: Seq[Char] = {
      for{row <- Rows
          column <- Columns
          square = getSquare(Position(row, column))
      } yield square
    }
    val rows: Seq[Seq[Char]] = squares.grouped(BoardSize).toSeq
    val rowsAsStrings: Seq[String] = rows map (_.mkString("", " ", "\n"))
    rowsAsStrings mkString

  }

  def canAttack(pos1: Position, pos2: Position) = {
    val deltaRow = math.abs(pos1.x - pos2.x)
    val deltaColumn = math.abs(pos1.y - pos2.y)

    def onSameRow = deltaRow == 0
    def onSameColumn = deltaColumn == 0
    def onDiag = deltaRow == deltaColumn

    onSameRow || onSameColumn || onDiag
  }
}

case class Position(x: Int, y: Int)

object Chessboard {
  type Square = Char
  type Squares = Seq[Square]

  val BoardSize = 8
  val Rows = (0 until BoardSize)
  val Columns = (0 until BoardSize)
}