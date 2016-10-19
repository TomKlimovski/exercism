import java.util.{TimeZone, GregorianCalendar, Calendar}


case class Gigasecond(g: GregorianCalendar) {
  val GigaSec = 1000000000
  val date = {
    val dt = g.clone().asInstanceOf[GregorianCalendar]
    dt.add(Calendar.SECOND, GigaSec)
    dt
  }

}