package validation.compiletime

import scala.reflect.macros.blackbox.Context
import scala.language.experimental.macros


/**
 * This class contains a macro that will validate your BigInt at compiletime.
 */
object BigInt {

  /**
   * This class contains a macro that will warn you at compile time if your integer has errors
   *
   *  use like: int"99999999999999999999999999999999999999999"
   *
   * @param sc (use the implicit interpolator instead)
   */
  implicit class BigIntHelper(val sc: StringContext) extends AnyVal {
    /**
     * will return a BigInt
     * @param sc
     */
    def int(args: Any*): scala.math.BigInt = macro BigIntHelperimpl
  }

  def BigIntHelperimpl(c: Context)(args: c.Expr[Any]*): c.Expr[scala.math.BigInt] = {
    import c.universe._

    c.prefix.tree match {
      // access data of string interpolation
      case Apply(_, List(Apply(_, rawParts))) =>

        // `parts` contain the strings and its position
        val parts = rawParts map { case t @ Literal(Constant(const: String)) => (const, t.pos) }

        parts match {
          //there is only one string literal
          case List((raw, pos)) => {

            //realtime validation here
            try {
              //TODO: could copy the java source to get better error messages, but thats a bit much for this
              val p = scala.math.BigInt(raw)
            } catch {
              case ex: NumberFormatException => {
                if (ex.getMessage != null) {
                  c.error(pos, ex.getMessage)
                } else {
                  c.error(pos, "there was a formatting error in your number")
                }
              }

              //catch other errors and hendle sensably
              case ex: Exception => {
                c.error(pos, "this was a very unexpected error, please file a bug on github (https://github.com/marklemay/scala-validations): " + ex)
              }
            }

            //then parse at compile time
            c.Expr[scala.math.BigInt](q" validation.runtime.BigInt.parse($raw) ")

            //TODO: we could inject the copiled BigInt into the scala AST, using dark magic, but that's a little too complicated for this case
          }

          case List() => {
            //don't forget the null case
            c.abort(c.enclosingPosition, "invalid")
          }

          //fall back to runtime interpolation
          case _ =>
            c.Expr[scala.math.BigInt](q" validation.runtime.BigInt.parse(StringContext(..$rawParts), Seq[Any](..$args) ) ")
        }

      case _ =>
        c.abort(c.enclosingPosition, "invalid")
    }

  }

}