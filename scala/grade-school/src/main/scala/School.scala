import scala.collection.immutable.SortedMap

class School {

  private var database: Map[Int, Seq[String]] = Map()

  def add(student: String, g: Int) = database = database + (g -> (grade(g) :+ student ))

  def grade(grade: Int) = db getOrElse (grade, List())

  def sorted = SortedMap(db.toSeq: _*).mapValues(_.sorted)

  def db: Map[Int, Seq[String]] = database

}