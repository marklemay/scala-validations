package validation.compiletime

import scala.reflect.macros.blackbox.Context
import scala.language.experimental.macros
import scala.reflect.internal.util.RangePosition
import java.io.File

/**
 * This class contains a macro that will validate your File Paths at compiletime.
 */
object FilePath {
  //TODO: known issue multiple slashes file"C:\Users\Mark\Documents\\\GitHub\scala-validations\"
  //TODO: also paths at runtime may differ from paths at compile time
  //TODO: deal with the scala io lib

  import scala.reflect.internal._

  /**
   * This class contains a macro that will warn you at compile time if your file path does not exist
   *
   * use like file"C:/tmp/somefile.txt"
   *
   * @param sc (use the implicit interpolator instead)
   */
  implicit class FilePathHelper(val sc: StringContext) extends AnyVal {
    /**
     * will return a valid File
     * @param sc
     */
    def file(args: Any*): File = macro FilePathimpl
  }

  def FilePathimpl(c: Context)(args: c.Expr[Any]*): c.Expr[File] = {
    import c.universe._

    c.prefix.tree match {
      // access data of string interpolation
      case Apply(_, List(Apply(_, rawParts))) =>

        // `parts` contain the strings a string interpolation is built of
        val parts = rawParts map { case t @ Literal(Constant(const: String)) => (const, t.pos) }

        parts match {
          case List((raw, pos)) => {

            //compiletime validation here
            try {
              val p = new File(raw)
              if (!p.exists()) { //these are warnings, not errors because a file may be created after compile time

                //TODO: these could be "spell" checked

                //recursively check up the directories, and only highlight the part of the path that does not exist

                //TODO: find a more functional way to do this
                var l = List(new File(raw))
                while (l.last.getParentFile != null) {
                  l = l.:+(l.last.getParentFile)
                }

                val existingPaths = l.filter { _.exists() }
                if (existingPaths.isEmpty) { //couldn't make sense of any of the file path
                  c.warning(pos, "no file exists at path in the comile context " + p.getAbsolutePath())

                } else {
                  val maxpath = existingPaths.maxBy { _.toString().length }

                  val rpos = pos.asInstanceOf[scala.reflect.internal.util.OffsetPosition]

                  val outpos = new RangePosition(rpos.source, rpos.start + 1 + maxpath.toString().length(), rpos.start + 1 + maxpath.toString().length(), rpos.end - 1)

                  c.warning(outpos.asInstanceOf[c.universe.Position], "no file exists at path in the comile context " + p.getAbsolutePath())
                }
              } else if (!p.canRead()) {

                //TODO: warn on just the file, not the dir
                c.warning(pos, "well a file exists, but you can't read it in the comile context " + p.getAbsolutePath())
              }
              //TODO: p.canWrite()
              //TODO: p.canExecute()
            } catch {
              case ex: Exception => {
                c.error(pos, "this was a very unexpected error, please file a bug on github (https://github.com/marklemay/scala-validations): " + ex)
              }
            }

            //then parse for real
            c.Expr[File](q" validation.runtime.FilePath.parse($raw) ")
          }

          case List() => {
            //don't forget the null case
            c.abort(c.enclosingPosition, "invalid")
          }

          //TODO: for the love of god write a test for this
          // get the first part of the file path, before interpolators
          case (raw, pos) :: _ => {
            //fall back to runtime interpolation

            //                  c.warning(pos, "hi")

            //TODO: need a way better way to deal with .../prefix-${...}
            // vs                                      .../dir/${...}
            // becuase we could check prefixes for correctness
            // of course it's possible to abuse ".." in the string interpolator to make all this pointless

            //TODO: we could also check prefixes

            //this is hacky
            val safeParent = if (raw.endsWith("""\""") || raw.endsWith("/")) {
              new File(raw)
            } else {
              (new File(raw)).getParentFile
            }

            if (safeParent != null && !safeParent.exists) {

              //TODO: this common functionality could be factored out

              var l = List(safeParent)
              while (l.last.getParentFile != null) {
                l = l.:+(l.last.getParentFile)
              }

              val existingPaths = l.filter { _.exists() }
              if (!existingPaths.isEmpty) {
                val maxpath = existingPaths.maxBy { _.toString().length }

                val rpos = pos.asInstanceOf[scala.reflect.internal.util.OffsetPosition]

                val outpos = new RangePosition(rpos.source, rpos.start + 1 + maxpath.toString().length(), rpos.start + 1 + maxpath.toString().length(), rpos.end - 1)

                c.warning(outpos.asInstanceOf[c.universe.Position], "no directory exists at path in the compile context ")
              }
            }

            c.Expr[File](q" validation.runtime.FilePath.parse(StringContext(..$rawParts), Seq[Any](..$args) ) ")

          }
        }

      case _ =>
        c.abort(c.enclosingPosition, "invalid") //TODO: change this to a sensible defult
    }

  }

}