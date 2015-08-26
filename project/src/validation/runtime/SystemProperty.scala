package validation.runtime

/**
 * this is the unvalidated version of validation.compiletime.SystemProperty.
 * It has the same public interface, and runtime behavior
 */
object SystemProperty {

  /**
   * this is the unvalidated version of validation.compiletime.SystemProperty.SystemPropertyHelper
   * It has the same public interface, and runtime behavior.
   *
   * use like sp"os.name"
   *
   * @param sc the string context (use the implicit interpolator instead)
   */
  implicit class SystemPropertyHelper(val sc: StringContext) extends AnyVal {
    def sp(args: Any*): String = SystemProperty.parse(sc, args)
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
  def parse(sc: StringContext, args: Seq[Any]): String =
    parse(sc.standardInterpolator({ x => x }, args))

  /**
   * This is the most literal interface, used in other places for consistency
   *
   * @param str the getProperty string
   * @return the value or null
   */
  def parse(str: String): String = java.lang.System.getProperty(str)

}