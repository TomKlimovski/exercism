import scala.util.Random

class Robot {
  private val chars=('A'to 'Z')

  private def generateName(): String = {
    val generateLetters = List.fill(2)(chars( Random.nextInt(chars.length)))
    val generateNumbers = List.fill(3)(Random.nextInt(10))

    (generateLetters ::: generateNumbers).mkString

  }

  private var generatedName:String = generateName()

  def name= generatedName

  def reset():Unit = {
    generatedName = generateName()
  }
}