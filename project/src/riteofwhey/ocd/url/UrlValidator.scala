package riteofwhey.ocd.url

import java.net.URL
import java.net.MalformedURLException

object UrlValidator {
  import scala.language.experimental.macros
  import scala.reflect.macros.blackbox.Context
  import scala.reflect.internal.util.RangePosition

  import language.experimental.macros

  import scala.reflect.internal._

  implicit class UrlHelper(val sc: StringContext) extends AnyVal {
    def url(args: Any*): URL = macro UrlHelperimpl
  }

  def UrlHelperimpl(c: Context)(args: c.Expr[Any]*): c.Expr[URL] = {
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
              val url = new URL(raw)
              //TODO: make a 200 check on this url in UrlLiveValidator
            } catch {
              case ex: MalformedURLException => {
                //TODO: could put more work into underlining the problem bits
                c.error(pos, ex.getMessage())
              }
              
              case ex: Exception => {
                c.error(pos, "this was a very unexpected error, please file a bug on github: "+ex)
              }
              
            }
            //then parse for real

            reify { Url.parse(c.Expr[String] { Literal(Constant(raw)) }.splice) }
          }

          case List() => {
            //don't forget the null case
            c.abort(c.enclosingPosition, "invalid")
          }


                    //TODO: for the love of god write a test for this
          case _ => {
            //fall back to runtime interpolation

//ewwwwwww
            reify { 
                Url.parse(
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
        c.abort(c.enclosingPosition, "invalid")
    }

  }

}