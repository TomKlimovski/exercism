object Allergen extends Enumeration{
  type Allergen = Value
  val Peanuts, Cats, Strawberries, Eggs, Tomatoes, Pollen, Chocolate, Shellfish = Value
}

case class Allergies() {
  private lazy val allergenList = List(Allergen.Eggs, Allergen.Peanuts, Allergen.Shellfish, Allergen.Strawberries,
    Allergen.Tomatoes, Allergen.Chocolate, Allergen.Pollen, Allergen.Cats)

  private def toBinary(n: Int): List[Char] = n.toBinaryString.toList

  def isAllergicTo(aType: Allergen.Value, score: Int): Boolean = {
    allergies(score).contains(aType)
  }

  def allergies(score: Int) = {
    val allergies = allergenList zip toBinary(score).reverse
    allergies.filter(_._2 == '1').map{case(a,b) => a}
  }
}