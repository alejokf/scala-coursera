package calculator

sealed abstract class Expr
final case class Literal(v: Double) extends Expr
final case class Ref(name: String) extends Expr
final case class Plus(a: Expr, b: Expr) extends Expr
final case class Minus(a: Expr, b: Expr) extends Expr
final case class Times(a: Expr, b: Expr) extends Expr
final case class Divide(a: Expr, b: Expr) extends Expr

object Calculator {
  def computeValues(namedExpressions: Map[String, Signal[Expr]]): Map[String, Signal[Double]] = {
    namedExpressions map {
      case (name, expr) => (name, Signal {
        val exprVal = expr()
        if (hasInfiniteCycle(name, exprVal, namedExpressions)) Double.NaN
        else eval(exprVal, namedExpressions)
      })
    }
  }

  def hasInfiniteCycle(name: String, expr: Expr, references: Map[String, Signal[Expr]]): Boolean = {
    expr match {
      case Literal(_) => false
      case Ref(refName) => {
        if (name == refName) true
        else references.get(refName) match {
          case Some(refExpr) => hasInfiniteCycle(name, refExpr(), references)
          case None => false
        }
      }
      case Plus(a, b) => hasInfiniteCycle(name, a, references) || hasInfiniteCycle(name, b, references)
      case Minus(a, b) => hasInfiniteCycle(name, a, references) || hasInfiniteCycle(name, b, references)
      case Times(a, b) => hasInfiniteCycle(name, a, references) || hasInfiniteCycle(name, b, references)
      case Divide(a, b) => hasInfiniteCycle(name, a, references) || hasInfiniteCycle(name, b, references)
    }
  }

  def eval(expr: Expr, references: Map[String, Signal[Expr]]): Double = {
    expr match {
      case Literal(v) => v
      case Ref(name) => eval(getReferenceExpr(name, references), references)
      case Plus(a, b) => eval(a, references) + eval(b, references)
      case Minus(a, b) => eval(a, references) - eval(b, references)
      case Times(a, b) => eval(a, references) * eval(b, references)
      case Divide(a, b) => eval(a, references) / eval(b, references)
    }
  }

  /** Get the Expr for a referenced variables.
   *  If the variable is not known, returns a literal NaN.
   */
  private def getReferenceExpr(name: String, references: Map[String, Signal[Expr]]) = {
    references.get(name).fold[Expr](Literal(Double.NaN))(exprSignal => exprSignal())
  }
}
