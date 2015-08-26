package validation.runtime

/**
 * this is the unvalidated version of validation.compiletime.Url.
 * It has the same public interface, and runtime behavior
 */
object BigDecimal {

  /**
   * this is the unvalidated version of validation.compiletime.Url.UrlHelper
   * It has the same public interface, and runtime behavior.
   *
   * use like dec"999999999999999999.99"
   *
   * @param sc the string context (use the implicit interpolator instead)
   */
  implicit class BigDecimalHelper(val sc: StringContext) extends AnyVal {
    def dec(args: Any*): BigDecimal = BigDecimal.parse(sc, args)
    
    //TODO: could easily extend this to other bases, if people wanted that
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
  def parse(sc: StringContext, args: Seq[Any]): BigDecimal =
    parse(sc.standardInterpolator({ x => x }, args))

  /**
   * This is the most literal interface, used in other places for consistency
   *
   * @param str the decimal number as a string
   * @return the BigDecimal
   */
  def parse(str: String): BigDecimal = scala.math.BigDecimal(str)

}