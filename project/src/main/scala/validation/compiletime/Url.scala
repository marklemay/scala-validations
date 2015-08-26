package validation.compiletime

import java.net.URL
import java.net.MalformedURLException
import scala.reflect.macros.blackbox.Context
import scala.language.experimental.macros

/**
 * This class contains a macro that will validate your Url at compiletime.
 */
// * TODO: link to usage example
object Url {
  import scala.reflect.internal.util.RangePosition
  import language.experimental.macros
  import scala.reflect.internal._

  /**
   * This class contains a macro that will warn you at compile time if you have url format errors
   *
   * use like url"http://www.google.com"
   *
   * @param sc (use the implicit interpolator instead)
   */
  implicit class UrlHelper(val sc: StringContext) extends AnyVal {
    /**
     * will return a valid URL
     * @param sc
     */
    // TODO: link to javadoc
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
              //TODO: make a 200 check on this url in UrlLiveValidator?
            } catch {
              case ex: MalformedURLException => {
                //TODO: could put more work into underlining the problem bits (See the regex validator_
                c.error(pos, ex.getMessage())
              }
              
              case ex: Exception => {
                c.error(pos, "this was a very unexpected error, please file a bug on github (https://github.com/marklemay/scala-validations): " + ex)
              }
            }
            
            //then parse at compile time
           c.Expr[java.net.URL]( q" validation.runtime.Url.parse($raw) ")
          }

          //don't forget the null case
          case List() => {
            c.abort(c.enclosingPosition, "invalid")
          }

          // if there is more then 1 string chunck i.e.   r"regex_${2 + 2}ex" 
          // fall back to runtime interpolation
          case _ =>
            c.Expr[java.net.URL](q" validation.runtime.Url.parse(StringContext(..$rawParts), Seq[Any](..$args) ) ")

        }

      case _ =>
        c.abort(c.enclosingPosition, "invalid")
    }
  }
}