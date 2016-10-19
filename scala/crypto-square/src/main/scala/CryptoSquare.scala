case class CryptoSquare() {

  def normalizePlaintext(s1: String) = {
    s1.filter(_.isLetterOrDigit).toLowerCase
  }

  def squareSize(s2: String) = {
    math.ceil(math.sqrt(normalizePlaintext(s2).length)).toInt

  }

  def plaintextSegments(s3: String) = {
    val clean = s3.filter(_.isLetterOrDigit).toLowerCase
    val sqrSize = squareSize(clean)
    clean.grouped(sqrSize).toList
  }

  def ciphertext(s4: String) = {
    ciphertextSegments(s4).mkString
  }

  def ciphertextSegments(s6: String): List[String] = {
    if(s6.isEmpty)
      return List.empty

    (0 until squareSize(s6)).map { i =>
      { for{values <- plaintextSegments(s6)
            chars <- charAt(i, values)
        } yield {chars}
      }.mkString
    }.toList
  }

  def normalizedCiphertext(s5: String) = {
    ciphertextSegments(s5).mkString(" ")

  }

  def charAt(i: Int, s: String): Option[Char] = {
    if (i < s.length)
      Some(s(i))
    else
      None
  }
}