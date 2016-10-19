object Sublist extends Enumeration {
  type Sublist = Value
  val Equal, Sublist, Superlist, Unequal = Value

  def sublist[A](left: List[A], right: List[A]): Sublist = {
    if(left == right) Equal
    else if(left.containsSlice(right)) Superlist
    else if (right.containsSlice(left)) Sublist
    else Unequal
  }

}