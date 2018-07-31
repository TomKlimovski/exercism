

def collatz(x: Int, c: Int): Option[Int] = {
  if (x == 1) Some(c-1)
  else if (x == 0) None
  else {
    val next: Int = if ((x % 2) == 0) x/2 else (x*3+1)
    println(next)
    (collatz(next, c+1))
  }
}

collatz(16, 1)

