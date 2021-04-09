package quickcheck

import common._

import org.scalacheck._
import Arbitrary._
import Gen._
import Prop._

abstract class QuickCheckHeap extends Properties("Heap") with IntHeap {

  lazy val genHeap: Gen[H] = for {
    x <- arbitrary[Int]
    h <- oneOf(const(empty), genHeap)
  } yield insert(x, h)
  implicit lazy val arbHeap: Arbitrary[H] = Arbitrary(genHeap)

  property("gen1") = forAll { (h: H) =>
    val m = if (isEmpty(h)) 0 else findMin(h)
    findMin(insert(m, h)) == m
  }

  property("min1") = forAll { a: Int =>
    val h = insert(a, empty)
    findMin(h) == a
  }

  property("twoValues") = forAll { (a: Int, b: Int) =>
    val h = insert(b, insert(a, empty))
    findMin(h) == (a min b)
  }

  property("deleteOneLengthHeap") = forAll { (a: Int) =>
    val h = insert(a, empty)
    deleteMin(h) == empty
  }

  property("orderedHeap") = forAll { (h: H) =>
    val vals = findAll(h)
    vals.sorted == vals
  }

  def findAll(h: H): List[A] = {
    if(isEmpty(h)) Nil
    else findMin(h) :: findAll(deleteMin(h))
  }

  property("minOfTwoMeldedHeaps") = forAll { (h1: H, h2: H) =>
    val h = meld(h1, h2)
    val min = findMin(h)
    (min == findMin(h1)) || (min == findMin(h2))
  }

  property("meldTwoHeaps") = forAll { (h1: H, h2: H) =>
    val h3 = meld(h1, h2)
    val h4= meld(deleteMin(h1), insert(findMin(h1), h2))
    compareHeaps(h3,h4)
  }

  def compareHeaps(h1: H, h2: H): Boolean = {
    if(h1 == empty || h2 == empty) true
    else {
      val v1 = findMin(h1)
      val v2 = findMin(h2)
      if(v1 != v2) false
      else compareHeaps(deleteMin(h1), deleteMin(h2))
    }
  }

}
