package validation.compiletime

import scala.reflect.macros.blackbox.Context
import scala.language.experimental.macros

/**
 * This class contains a macro that will validate your decimal number at compile time.
 */
object BigDecimal {

  /**
   * This class contains a macro that will warn you at compile time if you have regular expression errors
   *
   * use like dec"999999999999999999.99"
   *
   * @param sc (use the implicit interpolator instead)
   */
  //TODO: Javadoc, basicly https://docs.oracle.com/javase/7/docs/api/java/math/BigDecimal.html#BigDecimal%28java.lang.String%29
  implicit class BigDecimalHelper(val sc: StringContext) extends AnyVal {
    /**
     * will return a BigDecimal
     * @param sc
     */
    def dec(args: Any*): BigDecimal = macro BigDecimalHelperimpl
  }

  def BigDecimalHelperimpl(c: Context)(args: c.Expr[Any]*): c.Expr[scala.math.BigDecimal] = {
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
              //TODO: could copy the java source to get better error messages, but thats a bit much for this
              val p = scala.math.BigDecimal(raw)
            } catch {
              case ex: NumberFormatException => {
                if (ex.getMessage != null) {
                  c.error(pos, ex.getMessage)
                } else {
                  c.error(pos, "there was a formatting error in your number")
                }
              }

              //catch other errors and hendle sensably
              // TODO: make a WHAT macro that logs as much as it can, and tells the user where to file a bug report, from the maven project info?
              case ex: Exception => {
                c.error(pos, "this was a very unexpected error, please file a bug on github: " + ex)
              }
            }

            //then parse at compile time
           c.Expr[scala.math.BigDecimal]( q" validation.runtime.BigDecimal.parse($raw) ")
           
            //TODO: we could inject the copiled BigDecimal into the scala AST, using dark magic, but that's a little too complicated for this case

          }

          case List() => {
            //don't forget the null case
            c.abort(c.enclosingPosition, "invalid")
          }

          //TODO: for the love of god write a test for this
          // fall back to runtime interpolation
          case _ =>
           c.Expr[scala.math.BigDecimal]( q" validation.runtime.BigDecimal.parse(StringContext(..$rawParts), Seq[Any](..$args) ) ")
           
        }

      case _ =>
        c.abort(c.enclosingPosition, "invalid")
    }

  }

}