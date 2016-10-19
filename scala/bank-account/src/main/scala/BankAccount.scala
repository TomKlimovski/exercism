class BankAccount {
  var balance: Option[Int] = Some(0)

  def getBalance = balance

  def incrementBalance(increment: Int) = {
    synchronized{ balance = balance map (_ + increment)}
    balance
  }

  def closeAccount() = balance = None
}

object BankAccount {
  def apply() = new BankAccount
}