package validation.runtime

import java.net.URL

/**
 * this is the unvalidated version of validation.compiletime.Url.
 * It has the same public interface, and runtime behavior
 */
object Url {

  /**
   * this is the unvalidated version of validation.compiletime.Url.UrlHelper
   * It has the same public interface, and runtime behavior.
   *
   * use like url"http://www.google.com"
   *
   * @param sc the string context (use the implicit interpolator instead)
   */
  implicit class UrlHelper(val sc: StringContext) extends AnyVal {
    //TODO: this is like the one time ever HLists would be better

    def url(args: Any*): URL = Url.parse(sc, args)
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
   * @param str the URL string
   * @return the URL class
   */
  // TODO: refference java.net.URL doc
  def parse(str: String): URL = new URL(str)

}