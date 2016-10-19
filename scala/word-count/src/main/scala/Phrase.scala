class Phrase(s1: String) {
  def wordCount = {
    val words = s1.replaceAll("[^0-9a-zA-Z\\,\\' ]", "").split("\\s*(,|\\s)\\s*")
    words.groupBy(_.toLowerCase).mapValues(_.size)
  }
}
object Phrase {

}