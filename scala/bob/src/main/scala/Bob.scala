
class Bob {
  val Yell = "([0-9A-Z\\,a-z ]+!)".r
  val Question = "([0-9A-Z\\,\\.\\!a-z ]+\\?)".r
  val Forceful = "([A-Z ]+\\?)".r
  val Caps = "([A-Z ]+)".r

  def hey(s: String) = s match {
    case s if(s.trim.isEmpty) => "Fine. Be that way!"
    case Yell(_) | Forceful(_) | Caps(_) => "Whoa, chill out!"
    case Question(_) => "Sure."
    case _ => "Whatever."
  }
}

