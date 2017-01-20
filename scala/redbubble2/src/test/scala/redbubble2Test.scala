import org.scalatest.{FunSuite, Matchers}

class redbubble2Test extends FunSuite with Matchers {
  test("single bit one to decimal") {
    redbubble2.rebase(2, List(1),
      10) should be (Some(List(1)))
  }

  test("binary to single decimal") {
    //pending
    redbubble2.rebase(2, List(1, 0, 1),
      10) should be (Some(List(5)))
  }

  test("single decimal to binary") {
    //pending
    redbubble2.rebase(10, List(5),
      2) should be (Some(List(1 ,0 ,1)))
  }

  test("binary to multiple decimal") {
    //pending
    redbubble2.rebase(2, List(1, 0, 1, 0, 1, 0),
      10) should be (Some(List(4 ,2)))
  }

  test("decimal to binary") {
    //pending
    redbubble2.rebase(10, List(4, 2),
      2) should be (Some(List(1 ,0 ,1 ,0 ,1 ,0)))
  }

  test("trinary to hexadecimal") {
    //pending
    redbubble2.rebase(3, List(1, 1, 2, 0),
      16) should be (Some(List(2 ,10)))
  }

  test("hexadecimal to trinary") {
    //pending
    redbubble2.rebase(16, List(2, 10),
      3) should be (Some(List(1 ,1 ,2 ,0)))
  }

  test("15-bit integer") {
    //pending
    redbubble2.rebase(97, List(3, 46, 60),
      73) should be (Some(List(6 ,10 ,45)))
  }

  test("empty list") {
    //pending
    redbubble2.rebase(2, List(),
      10) should be (Some(List()))
  }

  test("single zero") {
    //pending
    redbubble2.rebase(10, List(0),
      2) should be (Some(List()))
  }

  test("multiple zeros") {
    //pending
    redbubble2.rebase(10, List(0, 0, 0),
      2) should be (Some(List()))
  }

  test("leading zeros") {
    //pending
    redbubble2.rebase(7, List(0, 6, 0),
      10) should be (Some(List(4, 2)))
  }

  test("negative digit") {
    //pending
    redbubble2.rebase(2, List(1, -1, 1, 0, 1, 0),
      10) should be (None)
  }

  test("invalid positive digit") {
    //pending
    redbubble2.rebase(2, List(1, 2, 1, 0, 1, 0),
      10) should be (None)
  }

  test("first base is one") {
    //pending
    redbubble2.rebase(1, List(),
      10) should be (None)
  }

  test("second base is one") {
    //pending
    redbubble2.rebase(2, List(1, 0, 1, 0, 1, 0),
      1) should be (None)
  }

  test("first base is zero") {
    //pending
    redbubble2.rebase(0, List(),
      10) should be (None)
  }

  test("second base is zero") {
   // pending
    redbubble2.rebase(10, List(7),
      0) should be (None)
  }

  test("first base is negative") {
    //pending
    redbubble2.rebase(-2, List(1),
      10) should be (None)
  }

  test("second base is negative") {
    //pending
    redbubble2.rebase(2, List(1),
      -7) should be (None)
  }

  test("both bases are negative") {
    //pending
    redbubble2.rebase(-2, List(1),
      -7) should be (None)
  }
}