import scala.collection.mutable.Stack

object Brackets {
  val validBrackets = "(){}[]}".toSet
  val bal: Map[Char, Char] = Map(']' -> '[', '}' -> '{', ')' -> '(')

  def areBalanced(s1: String): Boolean = {
    val cleaned = s1.filter(validBrackets)
    val s = Stack[Char]()
    for{char <- cleaned}
      yield {
        if(!s.isEmpty && s.top == bal.getOrElse(char, "")) {
          s.pop
        } else s.push(char)
      }
    if(!s.isEmpty) false
    else true
  }
}
