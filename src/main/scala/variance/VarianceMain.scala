package variance

import sets.{Empty, IntSet, NonEmpty}

object VarianceMain extends App {
  val a: Array[NonEmpty] = Array(new NonEmpty(1, Empty, Empty))
  // This doesn't compile because in Scala, Array is not covariant
  //val b: Array[IntSet] = a
  //b(0) = Empty
  val s: NonEmpty = a(0)

  val aList: List[NonEmpty] = List(new NonEmpty(1, Empty, Empty))
  // This compiles because in Scala, List is covariant
  val bList: List[IntSet] = aList
  // This is possible because List is immutable. This doesn't compile:
  //bList.head = Empty
}
