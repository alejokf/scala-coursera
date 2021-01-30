package recfun

object Main {
  def main(args: Array[String]) {
    println("Pascal's Triangle")
    for (row <- 0 to 10) {
      for (col <- 0 to row)
        print(pascal(col, row) + " ")
      println()
    }
  }

  /**
   * Exercise 1
   */
  def pascal(c: Int, r: Int): Int = {
    if (c == 0 || c == r) 1
    else pascal(c - 1, r - 1) + pascal(c, r - 1)
  }

  /**
   * Exercise 2
   */
  def balance(chars: List[Char]): Boolean = {
    def balanceRec(count: Int, charsPar: List[Char]): Boolean = {
      if (count < 0) false
      else if (charsPar.isEmpty) count == 0
      else balanceRec(if (charsPar.head == '(') count + 1 else count - 1, charsPar.tail)
    }

    balanceRec(0, chars.filter(x => x == '(' || x == ')'))
  }

  /**
   * Exercise 3
   */
  def countChange(money: Int, coins: List[Int]): Int = {
    def countChangeRec(money: Int, sortedCoins: List[Int]): Int = {
      if (money == 0) 1
      else if (money < 0 || sortedCoins.isEmpty) 0
      else countChangeRec(money - sortedCoins.head, sortedCoins) + countChangeRec(money, sortedCoins.tail)
    }

    countChangeRec(money, coins.sorted(Ordering.Int.reverse))
  }
}
