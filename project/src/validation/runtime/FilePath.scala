package validation.runtime

import java.io.File

/**
 * this is the unvalidated version of validation.compiletime.FilePath.
 * It has the same public interface, and runtime behavior
 */
object FilePath {

  /**
   * this is the unvalidated version of validation.compiletime.FilePath.FilePathHelper
   * It has the same public interface, and runtime behavior.
   *
   * use like file"C:/tmp/somefile.txt"
   *
   * @param sc the string context (use the implicit interpolator instead)
   */
  implicit class FilePathHelper(val sc: StringContext) extends AnyVal {
    def file(args: Any*): File = FilePath.parse(sc, args)
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
  def parse(sc: StringContext, args: Seq[Any]): File =
    parse(sc.standardInterpolator({ x => x }, args))

  /**
   * This is the most literal interface, used in other places for consistency
   *
   * @param str the file path
   * @return the File class
   */
  def parse(str: String): File = new File(str)

}