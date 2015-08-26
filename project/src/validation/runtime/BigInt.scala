package validation.runtime

/**
 * this is the unvalidated version of validation.compiletime.BigInt.
 * It has the same public interface, and runtime behavior
 */
object BigInt {

  /**
   * this is the unvalidated version of validation.compiletime.BigInt.BigIntHelper
   * It has the same public interface, and runtime behavior.
   *
   * use like int"99999999999999999999999999999999999999999"
   *
   * @param sc the string context (use the implicit interpolator instead)
   */
  implicit class BigIntHelper(val sc: StringContext) extends AnyVal {
    def int(args: Any*): BigInt = BigInt.parse(sc, args)
  }

  /**
   * use this function to get at the parsing behavior.
   *
   * this parse handles the runtime context and interpolators
   * it uses the default string interpolator
   *
   * @param sc
   * @param args
   * @return
   */
  def parse(sc: StringContext, args: Seq[Any]): BigInt =
    parse(sc.standardInterpolator({ x => x }, args))

  /**
   * This is the most literal interface, used in other places for consistency
   *
   * @param str the int string
   * @return the scala.math.BigInt class
   */
  def parse(str: String): BigInt = scala.math.BigInt(str)

}