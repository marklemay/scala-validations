package riteofwhey.ocd.url

import java.net.URL
import java.net.MalformedURLException

object UrlValidator {

  import scala.reflect.macros.Context
  import language.experimental.macros

  import reflect.macros.Context
  import java.util.Date
  import scala.reflect.internal._

  implicit class UrlHelper(val sc: StringContext) extends AnyVal {
    def url(args: Any*): URL = macro UrlHelperimpl
  }

  def UrlHelperimpl(c: Context)(args: c.Expr[Any]*): c.Expr[URL] = {
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
              val url = new URL(raw)
              //TODO: make a 200 check on this url in UrlLiveValidator
            } catch {
              case ex: MalformedURLException => {

                //pos.start
                c.error(pos, ex.getMessage())
              }
            }
            //TODO: then parse for real

            reify { new URL(c.Expr[String] { Literal(Constant(raw)) }.splice) }
          }

          case List() => {
            //don't forget the null case
            c.abort(c.enclosingPosition, "invalid")
          }
          case _ => {
            //fall back to runtime interpolation

            reify { new URL("/") }
          }
        }

      case _ =>
        c.abort(c.enclosingPosition, "invalid")
    }

  }

}