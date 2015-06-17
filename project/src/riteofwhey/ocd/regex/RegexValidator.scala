package riteofwhey.ocd.regex

import java.util.regex.Pattern
import java.util.regex.PatternSyntaxException

import scala.language.experimental.macros
import scala.reflect.macros.blackbox.Context
import scala.reflect.internal.util.RangePosition

/**
 * This class contains a macro that will validate your your regular expressions at compiletime.
 * 
 * TODO: link to usage example
 */
object RegexValidator {

  /**
   * This class contains a macro that will warn you at compile time if you have regular expression errors
   *
   * use like r"some reg(ular)+ex(pression)*"
   *
   * @param sc (use the implicit interpolator instead)
   */
  implicit class RegexHelper(val sc: StringContext) extends AnyVal {
      /**
   * will return a compiled Pattern, TODO: link to javadoc
 * @param sc
 */
    def r(args: Any*): Pattern = macro RegexHelperimpl
  }

  
  def RegexHelperimpl(c: Context)(args: c.Expr[Any]*): c.Expr[Pattern] = {
    import c.universe._

    c.prefix.tree match {
      // access data of string interpolation
      case Apply(_, List(Apply(_, rawParts))) =>

        // `parts` contain the strings a string interpolation is built from
        val parts = rawParts map { case t @ Literal(Constant(const: String)) => (const, t.pos) }

        parts match {
          // if there is only one string literal
          case List((raw, pos)) => {

            if (raw.isEmpty()) {
              c.warning(pos, "regex is empty") //somehow this can still be compiled
            }

            //compiletime validation here
            try {
              val p = Pattern.compile(raw)
            } catch {
              case ex: PatternSyntaxException => {

                //fancyness with underlineing

                //TODO: this seems a little iffy...
                val rpos = pos.asInstanceOf[scala.reflect.internal.util.OffsetPosition]

                //TODO: better class?
                val outpos = new RangePosition(rpos.source, rpos.start + ex.getIndex, rpos.start + ex.getIndex, rpos.start + ex.getIndex)

                c.error(outpos.asInstanceOf[c.universe.Position], ex.getDescription())
              }

              //catch other errors and handle sensibly
              case ex: Exception => {
                c.error(pos, "this was a very unexpected error, please file a bug on github: " + ex)
              }
            }

            //then parse at compile time
           c.Expr[Pattern]( q" riteofwhey.ocd.regex.RegexRuntime.parse($raw) ")
           
            //TODO: we could inject the compiled regex into the scala AST, using dark magic, but that's a little too complicated for this case
          }

          //don't forget the null case
          case List() => 
            c.abort(c.enclosingPosition, "invalid")

          // if there is more then 1 string chunck i.e.   r"regex_${2 + 2}ex" 
          // fall back to runtime interpolation
          case _ =>
           c.Expr[Pattern]( q" riteofwhey.ocd.regex.RegexRuntime.parse(StringContext(..$rawParts), Seq[Any](..$args) ) ")
           
        }

      //If somehow there is anything else, it's wrong
      case _ =>
        c.abort(c.enclosingPosition, "invalid")
    }

  }

}