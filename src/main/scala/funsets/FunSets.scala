package funsets

/**
 * 2. Purely Functional Sets.
 *
 * We will work with sets of integers.
 *
 * As an example to motivate our representation, how would you represent the set of all negative integers? You cannot
 * list them all… one way would be so say: if you give me an integer, I can tell you whether it’s in the set or not:
 * for 3, I say ‘no’; for -1, I say yes.
 *
 * Mathematically, we call the function which takes an integer as argument and which returns a boolean indicating
 * whether the given integer belongs to a set, the characteristic function of the set. For example, we can characterize
 * the set of negative integers by the characteristic function (x: Int) => x < 0.
 */
object FunSets {
  /**
   * We represent a set by its characteristic function, i.e.
   * its `contains` predicate.
   */
  type Set = Int => Boolean

  /**
   * Indicates whether a set contains a given element.
   */
  def contains(s: Set, elem: Int): Boolean = s(elem)

  /**
   * Returns the set of the one given element.
   */
  def singletonSet(elem: Int): Set = Set(elem)


  /**
   * Returns the union of the two given sets,
   * the sets of all elements that are in either `s` or `t`.
   */
  def union(s: Set, t: Set): Set = elem => s(elem) || t(elem)

  /**
   * Returns the intersection of the two given sets,
   * the set of all elements that are both in `s` and `t`.
   */
  def intersect(s: Set, t: Set): Set = elem => s(elem) && t(elem)

  /**
   * Returns the difference of the two given sets,
   * the set of all elements of `s` that are not in `t`.
   */
  def diff(s: Set, t: Set): Set = elem => s(elem) && !t(elem)

  /**
   * Returns the subset of `s` for which `p` holds.
   */
  def filter(s: Set, p: Int => Boolean): Set = intersect(s, p)

  /**
   * The bounds for `forall` and `exists` are +/- 1000.
   */
  val bound = 1000

  /**
   * Returns whether all bounded integers within `s` satisfy `p`.
   */
  def forall(s: Set, p: Int => Boolean): Boolean = {
    def iter(a: Int): Boolean = {
      if (a > 1000) true
      else if (contains(s, a) && !filter(s, p)(a)) false
      else iter(a + 1)
    }

    iter(-bound)
  }

  /**
   * Returns whether there exists a bounded integer within `s`
   * that satisfies `p`.
   */
  def exists(s: Set, p: Int => Boolean): Boolean = !(forall(s, diff(s, p)))

  /**
   * Returns a set transformed by applying `f` to each element of `s`.
   */
  def map(s: Set, f: Int => Int): Set = elem => exists(s, (x: Int) => f(x) == elem)

  /**
   * Displays the contents of a set
   */
  def toString(s: Set): String = {
    val xs = for (i <- -bound to bound if contains(s, i)) yield i
    xs.mkString("{", ",", "}")
  }

  /**
   * Prints the contents of a set on the console.
   */
  def printSet(s: Set) {
    println(toString(s))
  }
}
