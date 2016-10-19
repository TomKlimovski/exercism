
case class SpaceAge(ageSeconds: Long) {

  private def roundAt(p: Int, n: Double): Double = { val s = math pow (10, p); (math round n * s) / s }
  private def round = roundAt(2, _: Double)

  private val planets = Map("Earth" -> 31557600.0, "Mercury" -> 0.2408467, "Venus" -> 0.61519726,
    "Mars" -> 1.8808158, "Jupiter" -> 11.862615, "Saturn" -> 29.447498,
    "Uranus" -> 84.016846, "Neptune" -> 164.79132)

  lazy val seconds = ageSeconds
  private lazy val LongEarth = (seconds / planets.getOrElse("Earth", 1.0))

  val onEarth = round(LongEarth)
  val onMercury = round(LongEarth / planets.getOrElse("Mercury", 1.0))
  val onVenus = round(LongEarth / planets.getOrElse("Venus", 1.0))
  val onSaturn = round(LongEarth / planets.getOrElse("Saturn", 1.0))
  val onUranus = round(LongEarth / planets.getOrElse("Uranus", 1.0))
  val onNeptune = round(LongEarth / planets.getOrElse("Neptune", 1.0))
  val onMars = round(LongEarth / planets.getOrElse("Mars", 1.0))
  val onJupiter = round(LongEarth / planets.getOrElse("Jupiter", 1.0))
}