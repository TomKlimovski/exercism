
object ETL {
  //def transform(m: Map[Int, Seq[String]]) = m.flatMap { case (k, innerValues) => innerValues.map(_.toLowerCase -> k) }
  def transform(m: Map[Int, Seq[String]]) = {
    for {
      (key, values) <- m
      e <- values
    } yield (e.toLowerCase -> key)
  }
}