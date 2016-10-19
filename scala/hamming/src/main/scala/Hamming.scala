
object Hamming {
  def compute(s1: String, s2: String) = {
    require (s1.length() == s2.length())

    s1.size - s1.zip(s2).map{case(a,b) => if(a == b) 1 else 0 }.foldLeft(0)(_+_)
  }
}