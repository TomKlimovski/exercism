class Clock(hour: Int, minute: Int) {
  lazy val actualHours = mod((hour * 60 + minute) / 60.0, 24)
  lazy val actualMinutes = mod(minute, 60)

  private def mod(x: Double, y: Double) = (((x % y) + y) % y).toInt

  override def toString = f"$actualHours%02d:$actualMinutes%02d"
  override def equals(obj: Any) = obj.isInstanceOf[Clock] && obj.asInstanceOf[Clock].toString == this.toString

  def -(that: Clock) = new Clock(this.actualHours - that.actualHours, this.actualMinutes - that.actualMinutes)
  def +(that: Clock) = new Clock(this.actualHours + that.actualHours, this.actualMinutes + that.actualMinutes)

}

object Clock {
  def apply(hours: Int, minutes: Int) = new Clock(hours, minutes)
  def apply(minutes: Int) = new Clock(0, minutes)
}