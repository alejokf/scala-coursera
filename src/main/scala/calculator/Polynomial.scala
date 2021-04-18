package calculator

object Polynomial {
  def computeDelta(a: Signal[Double], b: Signal[Double],
      c: Signal[Double]): Signal[Double] = {
    Signal {
      Math.pow(b(), 2) - 4 * a() * c()
    }
  }

  def computeSolutions(a: Signal[Double], b: Signal[Double],
      c: Signal[Double], delta: Signal[Double]): Signal[Set[Double]] = {
    Signal {
      val deltaVal = delta()
      if (deltaVal < 0) {
        Set()
      } else {
        val aVal = a()
        val bVal = b()
        Set((-bVal + Math.sqrt(deltaVal)) / 2 * aVal, (-bVal - Math.sqrt(deltaVal)) / 2 * aVal)
      }
    }
  }
}
