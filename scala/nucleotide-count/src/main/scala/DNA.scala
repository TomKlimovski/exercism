
class DNA(s1: String) {
  val initial = Set('A', 'C', 'G', 'T')
  require(s1.forall(initial.contains(_)))

  val nucleotideCounts = {
    s1.foldLeft(Map('A' -> 0, 'C' -> 0, 'G' -> 0, 'T' -> 0))(
      (m, char) => { m + (char -> (m(char) + 1)) }
    )
  }
}