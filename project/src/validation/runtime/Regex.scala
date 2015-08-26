package validation.runtime

import java.util.regex.Pattern

/**
 * this is the unvalidated version of validation.compiletime.Regex.
 * It has the same public interface, and runtime behavior
 */
object Regex {
  
  /**
   * this is the unvalidated version of validation.compiletime.Regex.RegexHelper
   * It has the same public interface, and runtime behavior.
   *
   * use like r"some reg(ular)+ex(pression)*"
   *
   * @param sc the string context (use the implicit interpolator instead)
   */
  implicit class RegexHelper(val sc: StringContext) extends AnyVal {
    def r(args: Any*): Pattern = Regex.parse(sc, args)
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
   * @param str the regular expression string
   * @return the compiled regular expression
   */
  // TODO: refference Pattern.compile doc
  def parse(str: String): Pattern = Pattern.compile(str)

}