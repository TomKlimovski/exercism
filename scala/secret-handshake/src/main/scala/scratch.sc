import scala.collection.BitSet

object SecretHandshake {
  def commands(binary: Int) = {

    val bMap: Map[String, String] =
      Map("1" -> "wink",
      "10" -> "double blink",
      "100" -> "close your eyes",
        "1000" -> "jump",
        "10000" -> "reverse")


    val ones = buildOutCommands(binary.toBinaryString)

    val output = ones.map(bMap(_) ).toList

    println(s"output $output")
    if(output.contains("reverse"))
      output.filter(x => x != "reverse").reverse
    else
      output
  }

  def buildOutCommands(bString: String) = {
    val ones = 0.until(bString.length).filter(bString.reverse.startsWith("1", _))
    ones.map{x => "1" + ("0" * x)}
  }


}

SecretHandshake.commands(0) //should be (List("wink"))

val x = 17
val y = 3
val z = "100"
val w = "10011"

val ones = 0.until(w.length).filter(w.reverse.startsWith("1", _))

ones.map{x => "1" + ("0" * x)}