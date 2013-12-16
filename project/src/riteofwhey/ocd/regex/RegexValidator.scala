package riteofwhey.ocd.regex

import java.util.regex.Pattern
import java.util.regex.PatternSyntaxException

object RegexValidator {

  import scala.reflect.macros.Context
  import language.experimental.macros

  import reflect.macros.Context
  import java.util.Date
  import scala.reflect.internal._

  implicit class RegexHelper(val sc: StringContext) extends AnyVal {
    def r(args: Any*): Pattern = macro RegexHelperimpl
  }

  def RegexHelperimpl(c: Context)(args: c.Expr[Any]*): c.Expr[Pattern] = {
    import c.universe._

    c.prefix.tree match {
      // access data of string interpolation
      case Apply(_, List(Apply(_, rawParts))) =>

        // `parts` contain the strings a string interpolation is built of
        val parts = rawParts map { case t @ Literal(Constant(const: String)) => (const, t.pos) }

        parts match {
          case List((raw, pos)) => {
            //realtime validation here
            try {
              val p = Pattern.compile(raw)
            } catch {
              case ex: PatternSyntaxException => {
                val enc = c.enclosingPosition
                //  val ppp = new c.Position(2323)
                enc.endOrPoint
                val str = "" + enc.start + " " //+ ppp.

                //pos.start
                c.error(pos, ex.getDescription())
              }
            }
            //TODO: then parse for real

            reify { Pattern.compile(c.Expr[String] { Literal(Constant(raw)) }.splice) }
          }

          case List() => {
            //don't forget the null case
            c.abort(c.enclosingPosition, "invalid")
          }
          
          case _ => {
            //fall back to runtime interpolation

            reify { Pattern.compile(".*") }
          }
        }

      case _ =>
        c.abort(c.enclosingPosition, "invalid")
    }

  }

}