class Scrabble() {

  val lMap: Map[Int, List[Char]] = Map(1 -> List('A', 'E', 'I', 'O', 'U', 'L', 'N', 'R', 'S', 'T'),
  2 -> List('D', 'G'),
  3 -> List('B', 'C', 'M', 'P'),
  4 -> List('F', 'H', 'V', 'W', 'Y'),
  5 -> List('K'),
  8 -> List('J', 'X'),
  10 -> List('Q', 'Z'))

  val letters = for{
    (key, values) <- lMap
    e <- values
  } yield (e, key)

  def scoreLetter(char: Char) = {
    letters getOrElse(char.toUpper, 0)
  }

  def scoreWord(str: String) = {
      str.map(scoreLetter).sum
  }
}