package riteofwhey.ocd.regex

//TODO: Rename object?
/**
 * this is the unvalidated version of RegexValidator.
 * It has the same public interface, and runtime behavior
 */
object RegexRuntime {
  import java.util.regex.Pattern

  /**
   * this is the unvalidated version of RegexValidator.RegexHelper
   * It has the same public interface, and runtime behavior.
   *
   * use like r"some reg(ular)+ex(pression)*"
   *
   * @param sc the strung context (use the implicit interpolator instead
   */
  implicit class RegexHelper(val sc: StringContext) extends AnyVal {
    def r(args: Any*): Pattern = RegexRuntime.parse(sc, args)
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
  def parse(sc: StringContext, args: Seq[Any]): Pattern =
    parse(sc.standardInterpolator({ x => x }, args))

  /**
   * This is the most literal interface, used in other places for consistency
   *
   * TODO: refference Pattern.compile doc
   *
   * @param str the regular expression string
   * @return the compiled regular expression
   */
  def parse(str: String): Pattern = Pattern.compile(str)

}