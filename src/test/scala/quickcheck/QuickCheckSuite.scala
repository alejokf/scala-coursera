package quickcheck

import org.junit.runner.RunWith
import org.scalacheck.Test.{Result, TestCallback}
import org.scalacheck.{Properties, Test}
import org.scalatest.FunSuite
import org.scalatest.junit.JUnitRunner
import org.scalatest.prop.Checkers

object QuickCheckBinomialHeap extends QuickCheckHeap with BinomialHeap

@RunWith(classOf[JUnitRunner])
class QuickCheckSuite extends FunSuite with Checkers {
  def checkBogus(p: Properties) {
    var ok = false
    val prms = Test.Parameters.default.withTestCallback(new TestCallback {
      override def onTestResult(name: String, result: Result): Unit = {
        if (!result.passed) {
          ok = true
        }
      }
    })
    p.check(prms)

    assert(ok, "A bogus heap should NOT satisfy all properties. Try to find the bug!")
  }

  test("Binomial heap satisfies properties.") {
    (new QuickCheckHeap with quickcheck.test.BinomialHeap).check()
  }

  test("Bogus (1) binomial heap does not satisfy properties.") {
    checkBogus(new QuickCheckHeap with quickcheck.test.Bogus1BinomialHeap)
  }

  test("Bogus (2) binomial heap does not satisfy properties.") {
    checkBogus(new QuickCheckHeap with quickcheck.test.Bogus2BinomialHeap)
  }

  test("Bogus (3) binomial heap does not satisfy properties.") {
    checkBogus(new QuickCheckHeap with quickcheck.test.Bogus3BinomialHeap)
  }

  test("Bogus (4) binomial heap does not satisfy properties.") {
    checkBogus(new QuickCheckHeap with quickcheck.test.Bogus4BinomialHeap)
  }

  test("Bogus (5) binomial heap does not satisfy properties.") {
    checkBogus(new QuickCheckHeap with quickcheck.test.Bogus5BinomialHeap)
  }
}
