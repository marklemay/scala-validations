package riteofwhey.ocd.regex

import java.util.regex.Pattern
import java.util.regex.PatternSyntaxException
import java.io.File

object FilePathValidator {
  //TODO: deal with the scala io lib

  import scala.reflect.macros.Context
  import language.experimental.macros

  import reflect.macros.Context
  import java.util.Date
  import scala.reflect.internal._

  implicit class FilePathHelper(val sc: StringContext) extends AnyVal {
    def FilePath(args: Any*): File = macro FilePathimpl
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
            //realtime validation here
            try {
              val p = new File(raw)
              if (!p.exists()) { //these are warnings, not errors because a file may be created after compile time
                //TODO: recursively check up the directeries, and only highlight the part that is in error
                c.warning(pos, "Hey, no file exists at path " + p.getAbsolutePath())
              } else if (!p.canRead()) {
                c.warning(pos, "well a file exists, but you can't read it " + p.getAbsolutePath())
              }
              //p.canWrite()
              //p.canExecute()
            } catch {
              case ex: Exception => {
                c.error(pos, ex.getMessage())
              }
            }

            //TODO: then parse for real

            reify { new File(c.Expr[String] { Literal(Constant(raw)) }.splice) }
          }

          case List() => {
            //don't forget the null case
            c.abort(c.enclosingPosition, "invalid")
          }

          case _ => {
            //fall back to runtime interpolation

            reify { new File("/") }
          }
        }
        
      case _ =>
        c.abort(c.enclosingPosition, "invalid") //TODO: change this to a sensible defult
    }

  }

}