case class Atbash() {
  private val alph = 'A' to 'Z'
  private val revAlph = alph.reverse

  def encode(str: String) = {
    val clean = str.replaceAll("""[\p{Punct}[" "]]""", "").toUpperCase
    val newStr = clean.map{x => if(x.isDigit) x else revAlph(alph.indexOf(x)).toLower}
    newStr.replaceAll("(.{5})", "$1 ").trim
  }
}