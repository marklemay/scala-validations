package riteofwhey.ocd.filepath

import scala.language.experimental.macros
import scala.reflect.macros.blackbox.Context
import java.nio.file.Paths
import scala.reflect.internal.util.RangePosition
import java.io.File

object FilePathValidator {
  //TODO: kknown issue multiple slashes file"C:\Users\Mark\Documents\\\GitHub\scala-validations\"
  //TODO: also paths of the runtime
  //TODO: deal with the scala io lib

  import language.experimental.macros

  import java.util.Date
  import scala.reflect.internal._

  implicit class FilePathHelper(val sc: StringContext) extends AnyVal {
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
            
            //TODO: deal with empty
            
            //realtime validation here
            try {
              val p = new File(raw)
              if (!p.exists()) { //these are warnings, not errors because a file may be created after compile time
                //TODO: recursively check up the directeries, and only highlight the part that is in error

                //TODO: these could be spell checked

                //TODO: find a more functional way to do this
                var l = List(new File(raw))
                while (l.last.getParentFile != null) {
                  l = l.:+(l.last.getParentFile)
                }

                val existingPaths = l.filter { _.exists() }
                if (existingPaths.isEmpty) {
                  c.warning(pos, "well a file exists, but you can't read it in the comile context " + p.getAbsolutePath())

                } else {
                  val maxpath = existingPaths.maxBy { _.toString().length }

                  val rpos = pos.asInstanceOf[scala.reflect.internal.util.OffsetPosition]

                  val outpos = new RangePosition(rpos.source, rpos.start  + maxpath.toString().length(), rpos.point, rpos.end)

                  c.warning(outpos.asInstanceOf[c.universe.Position], "no file exists at path in the comile context " + p.getAbsolutePath())
                }
              } else if (!p.canRead()) {
                
                //TODO: warn on just the file, not the dir
                c.warning(pos, "well a file exists, but you can't read it in the comile context " + p.getAbsolutePath())
              }
              //p.canWrite()
              //p.canExecute()
            } catch {
              case ex: Exception => {
                c.error(pos, "this was a very unexpected error, please file a bug on github: "+ex)
              }
            }

            //then parse for real

            reify { FilePath.parse(c.Expr[String] { Literal(Constant(raw)) }.splice) }
          }

          case List() => {
            //don't forget the null case
            c.abort(c.enclosingPosition, "invalid")
          }

                    //TODO: for the love of god write a test for this
          case (raw, pos)::_ => {
            //fall back to runtime interpolation

            
//                  c.warning(pos, "hi")
            
            
            
            //TODO: need a waybetter way to deal with .../prefix-${...}
            // vs                           .../dir/${...}
            //right now I ignore that distinction
            
            //TODO: we could also check prefixes
            
            //this is hacky
            val safeParent= if(raw.endsWith("""\""")||raw.endsWith("/")){
              new File(raw)
            }else{
              (new File(raw)).getParentFile
            }
            
            
            if(!safeParent.exists){
              
            //TODO: this common functionality could be factored out
       
            var l = List(safeParent)
                while (l.last.getParentFile != null) {
                  l = l.:+(l.last.getParentFile)
                }
            
//            
//                  c.warning(pos, l.mkString("\n"))

                val existingPaths = l.filter { _.exists() }
                if (!existingPaths.isEmpty) {
                  val maxpath = existingPaths.maxBy { _.toString().length }

                  val rpos = pos.asInstanceOf[scala.reflect.internal.util.OffsetPosition]

                  val outpos = new RangePosition(rpos.source, rpos.start  + maxpath.toString().length(), rpos.point, rpos.end)

                  c.warning(outpos.asInstanceOf[c.universe.Position], "no directory exists at path in the compile context " )
                }
            }
            
            
//ewwwwwww
            reify { 
                FilePath.parse(
                    //string context
                (c.Expr[StringContext] {
                Apply(Select(Ident(typeOf[StringContext].typeSymbol.companion), TermName("apply")), rawParts)
              }.splice),
                    //seq of trees
              (c.Expr[Seq[Any]] {
                Apply(Select(Ident(typeOf[Seq[Any]].typeSymbol.companion), TermName("apply")), 
                    args.map {_.tree}.toList
                    )
              }.splice)
              
              )
              
               }
          }
        }
        
      case _ =>
        c.abort(c.enclosingPosition, "invalid") //TODO: change this to a sensible defult
    }

  }

}