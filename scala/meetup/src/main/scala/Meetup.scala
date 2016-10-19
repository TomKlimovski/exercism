
import java.util.{GregorianCalendar, Calendar}

object Meetup {

  class Meetup(m: Int, year: Int) {

    val month = m-1
    val teen = Set(13,14,15,16,17,18,19)

    def teenth(day: Meetup.Day): GregorianCalendar =
      (for {
        week <- 1 to 5
        possibleMatch = selectNthDayOfMonth(day, week)
        if teen.contains(possibleMatch.get(Calendar.DAY_OF_MONTH))
      } yield possibleMatch).head

    //DAY_OF_WEEK_IN_MONTH: A constant representing a value for how many times a given day has occurred in the month.
    //DAY_OF_WEEK: A constant representing the day of the week.
    def selectNthDayOfMonth(day: Meetup.Day, nth: Int) = {
      val calendar = new GregorianCalendar(year, month, 1)
      calendar.set(Calendar.DAY_OF_WEEK, day.calendarDay)
      calendar.set(Calendar.DAY_OF_WEEK_IN_MONTH, nth)
      calendar
    }

    def first(day: Meetup.Day) = selectNthDayOfMonth(day, 1)
    def last(day: Meetup.Day) = selectNthDayOfMonth(day, -1)
    def fourth(day: Meetup.Day) = selectNthDayOfMonth(day, 4)
    def third(day: Meetup.Day) = selectNthDayOfMonth(day, 3)
    def second(day: Meetup.Day) = selectNthDayOfMonth(day, 2)

  }

  trait Day { def calendarDay: Int}

  case object Mon extends Day {  def calendarDay: Int = Calendar.MONDAY }

  case object Tue extends Day {  def calendarDay: Int = Calendar.TUESDAY }

  case object Wed extends Day {  def calendarDay: Int = Calendar.WEDNESDAY }

  case object Thu extends Day {  def calendarDay: Int = Calendar.THURSDAY }

  case object Fri extends Day {  def calendarDay: Int = Calendar.FRIDAY }

  case object Sat extends Day {  def calendarDay: Int = Calendar.SATURDAY }

  case object Sun extends Day {  def calendarDay: Int = Calendar.SUNDAY }

  def apply(month: Int, year: Int) = new Meetup(month, year)
}