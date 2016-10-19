class Anagram(s1: String) {

  def matches(sq: Seq[String]) = {
    val ret = for(
      name <- sq
      if( (s1.size == name.size)
        && (s1.toLowerCase diff name.toLowerCase).size == 0)
        && (s1.toLowerCase != name.toLowerCase))
      yield {
        name
      }

    ret
  }
}