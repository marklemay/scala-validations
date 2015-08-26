package validation.compiletime

import scala.reflect.macros.blackbox.Context
import scala.reflect.internal.util.RangePosition
import scala.language.experimental.macros
import java.nio.file.Paths
import scala.reflect.internal.util.RangePosition
import java.io.File
import java.net.URL

/**
 * This class contains a macro that will validate your classpaths at compiletime.
 */
object ClassPath {
  // known issues:
  //TODO: does not yet look in jar files for resources
  //TODO: not sure what build systems this actually works with
  //TODO: multiple slashes file"C:\Users\Mark\Documents\\\GitHub\scala-validations\"
  //TODO: deal with the scala io lib

  //TODO: add a What's my class path def macro that just warns out ${c.classPath}, so users can tell what's going on.

  import scala.reflect.internal._

  implicit class ClassPathHelper(val sc: StringContext) extends AnyVal {
    /**
     * Return a URL to a resource on the class path
     */
    def resource(args: Any*): URL = macro ClassPathimpl
  }

  def ClassPathimpl(c: Context)(args: c.Expr[Any]*): c.Expr[URL] = {
    import c.universe._

    c.prefix.tree match {
      // access data of string interpolation
      case Apply(_, List(Apply(_, rawParts))) =>

        // `parts` contain the strings a string interpolation is built of
        val parts = rawParts map { case t @ Literal(Constant(const: String)) => (const, t.pos) }

        parts match {
          case List((raw, pos)) => {

            //compile time validation here
            try {

              val paths = c.classPath.map { url => Paths.get(url.toURI()).toFile() }.filter(_.isDirectory())

              //TODO: recursively explore the jars, till then this should only be a warning

              val potentialLocations = paths.map { dir => new File(dir.getCanonicalPath() + raw) }

              if (!potentialLocations.exists { f => f.exists() }) {

                // break the file up into a list of paths
                //TODO: not idomatic
                var l = List(new File(raw))
                while (l.last.getParentFile != null) {
                  l = l.:+(l.last.getParentFile)
                }

                //TODO: this will not handle edge casses like path//to/file
                val maxPathLength = l.filter { pp => paths.exists { dir => (new File(dir.getCanonicalPath() + pp)).exists() } }.maxBy { _.toString().length }

                val rpos = pos.asInstanceOf[scala.reflect.internal.util.OffsetPosition]

                val outpos = new RangePosition(rpos.source, rpos.start + 1 + maxPathLength.toString().length(), rpos.start + 1 + maxPathLength.toString().length(), rpos.end - 1)

                //may require a build to move the file
                c.warning(outpos.asInstanceOf[c.universe.Position], s"Could not find the file at ${raw} are you sure it exists?  (triggrting a recompile may make this error go away)")
              }

            } catch {
              case ex: Exception => {
                c.error(pos, "this was a very unexpected error, please file a bug on github: " + ex)
              }
            }

            //then parse for real

            c.Expr[URL](q" validation.runtime.ClassPath.parse($raw) ")

          }

          case List() => {
            //don't forget the null case
            c.abort(c.enclosingPosition, "invalid")
          }

          //TODO: for the love of god write a test for this
          case (raw, pos) :: _ => {
            //fall back to runtime interpolation, but still validate what we can

            //TODO: need a waybetter way to deal with .../prefix-${...}
            // vs                           .../dir/${...}
            //right now I ignore that distinction

            //TODO: we could also check prefixes

            //this is hacky, it breaks DRY so bad
            val safeParent = if (raw.endsWith("""\""") || raw.endsWith("/")) {
              new File(raw)
            } else {
              (new File(raw)).getParentFile
            }

            if (safeParent != null) {
              val paths = c.classPath.map { url => Paths.get(url.toURI()).toFile() }.filter(_.isDirectory())

              val potentialLocations = paths.map { dir => new File(dir.getCanonicalPath() + safeParent) }

              if (!potentialLocations.exists { f => f.exists() }) {

                //TODO: this common functionality could be factored out

                var l = List(safeParent)
                while (l.last.getParentFile != null) {
                  l = l.:+(l.last.getParentFile)
                }

                //TODO: this will not handle edge casses like path//to/file
                val maxPathLength = l.filter { pp => paths.exists { dir => (new File(dir.getCanonicalPath() + pp)).exists() } }.maxBy { _.toString().length }

                val rpos = pos.asInstanceOf[scala.reflect.internal.util.OffsetPosition]

                val outpos = new RangePosition(rpos.source, rpos.start + 1 + maxPathLength.toString().length(), rpos.start + 1 + maxPathLength.toString().length(), rpos.end - 1)

                //may require a build to move the file
                c.warning(outpos.asInstanceOf[c.universe.Position], s"Could not find the directory at ${safeParent} are you sure it exists?  (triggrting a recompile may make this error go away)")
              }
            }

            c.Expr[URL](q" validation.runtime.ClassPath.parse(StringContext(..$rawParts), Seq[Any](..$args) ) ")
          }
        }

      case _ =>
        c.abort(c.enclosingPosition, "invalid") //TODO: change this to a sensible defult
    }

  }

}