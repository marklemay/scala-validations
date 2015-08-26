package validation.runtime

import java.net.URL

//TODO: would a stream version be more useful?

/**
 * this is the unvalidated version of validation.compiletime.ClassPath.
 * It has the same public interface, and runtime behavior
 */
object ClassPath {

  /**
   * this is the unvalidated version of validation.compiletime.ClassPath.ClassPathHelper
   * It has the same public interface, and runtime behavior.
   *
   * use like resource"/path/to/the/resource.ext"
   *
   * @param sc the string context (use the implicit interpolator instead)
   */
  implicit class ClassPathHelper(val sc: StringContext) extends AnyVal {
    def resource(args: Any*): URL = ClassPath.parse(sc, args)
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
  def parse(sc: StringContext, args: Seq[Any]): URL =
    parse(sc.standardInterpolator({ x => x }, args))

  /**
   * This is the most literal interface, used in other places for consistency
   *
   * @param str the class path
   * @return the URL
   */
  def parse(str: String): URL = "".getClass.getResource(str)

}