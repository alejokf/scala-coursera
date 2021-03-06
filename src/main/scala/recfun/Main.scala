package recfun

/**
 * 1. Recursion
 */
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
   * Exercise 1: Pascal’s Triangle
   *
   * The numbers at the edge of the triangle are all 1, and each number inside the triangle is the sum of the two
   * numbers above it. Write a function that computes the elements of Pascal’s triangle by means of a recursive process.
   * 1
   * 1 1
   * 1 2 1
   * 1 3 3 1
   * 1 4 6 4 1
   */
  def pascal(c: Int, r: Int): Int = {
    if (c == 0 || c == r) 1
    else pascal(c - 1, r - 1) + pascal(c, r - 1)
  }

  /**
   * Exercise 2: Parentheses Balancing
   *
   * Write a recursive function which verifies the balancing of parentheses in a string, which we represent as a
   * List[Char] not a String.
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
   * Exercise 3: Counting Change
   *
   * Write a recursive function that counts how many different ways you can make change for an amount, given a list of
   * coin denominations. For example, there are 3 ways to give change for 4 if you have coins with denomination 1 and 2:
   * 1+1+1+1, 1+1+2, 2+2.
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
