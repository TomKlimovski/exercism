case class Dna() {
  val dNAcomplement = Map('G' -> 'C', 'C' -> 'G', 'T' -> 'A', 'A' -> 'U')
  def toRna(s1: String) = {
    s1 map dNAcomplement
    }

}