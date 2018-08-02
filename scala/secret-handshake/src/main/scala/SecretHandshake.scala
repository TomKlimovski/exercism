object SecretHandshake {
  def commands(binary: Int) = {

    val bMap: Map[String, String] =
      Map("1" -> "wink",
        "10" -> "double blink",
        "100" -> "close your eyes",
        "1000" -> "jump",
        "10000" -> "reverse")

    val ones = buildOutCommands(binary.toBinaryString)

    val output = ones.map(bMap(_)).toList

    if(output.contains("reverse"))
      output.filter(_ != "reverse").reverse
    else
      output

  }

  def buildOutCommands(bString: String) = {
    //all we care about is the 1's and their positional notation
    val ones = 0.until(bString.length).filter(bString.reverse.startsWith("1", _))
    //when we find the 1's and their position, pad out remaining length with 0's to match bMap
    ones.map{x => "1" + ("0" * x)}
  }
}