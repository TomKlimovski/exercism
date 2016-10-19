object Pangrams {

  def isPangram(s1: String): Boolean = {

    val alphabet = 'a' to 'z'

    alphabet.forall(s1.toLowerCase contains _)

  }
}