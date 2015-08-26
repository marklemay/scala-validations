package validation.compiletime

import java.net.MalformedURLException
import scala.reflect.macros.blackbox.Context
import scala.language.experimental.macros

/**
 * This class contains a macro that will validate your SystemProperty request at compiletime.
 */
object SystemProperty {

  /**
   * This class contains a macro that will warn you at compile time if the system property you request could not be found in the compile context.  If it is not contained it will give a warning, not an error.
   *
   * use like sp"os.name"
   *
   * @param sc (use the implicit interpolator instead)
   */
  // * TODO: link to system property javadoc
  implicit class SystemPropertyHelper(val sc: StringContext) extends AnyVal {

    /**
     * will return the system property as a string, or null if the system property is unfound
     * @param sc
     */
    // TODO: use some and none.
    def sp(args: Any*): String = macro SystemPropertyHelperimpl
  }

  def SystemPropertyHelperimpl(c: Context)(args: c.Expr[Any]*): c.Expr[String] = {
    import c.universe._

    c.prefix.tree match {
      // access data of string interpolation
      case Apply(_, List(Apply(_, rawParts))) =>

        // `parts` contain the strings a string interpolation is built of, TODO grammar?
        val parts = rawParts map { case t @ Literal(Constant(const: String)) => (const, t.pos) }

        parts match {
          //there is only one string literal
          case List((raw, pos)) => {

            //realtime validation here
            try {
              //TODO: we could "spell check" these
              val p = java.lang.System.getProperty(raw)

              if (p == null) {
                c.warning(pos, "there was no system property with the name '" + raw + "' in the compile context, if you expect it to be in the runtime context, ignore this warning")
              }

            } catch {
              case ex: IllegalArgumentException => {
                if (ex.getMessage != null) {
                  c.error(pos, ex.getMessage)
                } else {
                  c.error(pos, "there was a big problem")
                }
              }

              //catch other errors and hendle sensably
              case ex: Exception => {
                c.error(pos, "this was a very unexpected error, please file a bug on github (https://github.com/marklemay/scala-validations): " + ex)
              }
            }

            //then parse at compile time
            c.Expr[String](q" validation.runtime.SystemProperty.parse($raw) ")

          }

            //don't forget the null case
          case List() => 
            c.abort(c.enclosingPosition, "invalid")
          
          // if there is more then 1 string chunck i.e.   r"regex_${2 + 2}ex" 
          // fall back to runtime interpolation
          case _ => 
            c.Expr[String](q" validation.runtime.SystemProperty.parse(StringContext(..$rawParts), Seq[Any](..$args) ) ")
        }

      case _ =>
        c.abort(c.enclosingPosition, "invalid")
    }
  }
}