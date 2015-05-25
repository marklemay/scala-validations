package riteofwhey.ocd.systemProperty


import scala.language.experimental.macros
import scala.reflect.macros.blackbox.Context
import scala.reflect.internal.util.RangePosition



//TODO: Javadoc
object SystemPropertyValidator {

//TODO: Javadoc, basicly copy the system property javadoc
  implicit class SystemPropertyHelper(val sc: StringContext) extends AnyVal {
    def sp(args: Any*): String = macro SystemPropertyHelperimpl
  }

  def SystemPropertyHelperimpl(c: Context)(args: c.Expr[Any]*): c.Expr[String] = {
    import c.universe._

    c.prefix.tree match {
      // access data of string interpolation
      case Apply(_, List(Apply(_, rawParts))) =>

        // `parts` contain the strings a string interpolation is built of, TODO grammar?
        val parts = rawParts map { case t @ Literal(Constant(const: String)) => (const, t.pos) }

        parts match {
          //there is only one string literal
          case List((raw, pos)) => {
            
            
            //realtime validation here
            try {
              //TODO: we could "spell check" these
              val p = java.lang.System.getProperty(raw)

              if(p==null){
                c.warning(pos, "there was no system property with the name '"+raw+"' in the compile context, if you expect it to be in the runtime context, ignore this warning")
              }
              
            } catch {
              case ex: IllegalArgumentException => {
                if(ex.getMessage!=null){
                c.error(pos, ex.getMessage)
                }else{
                c.error(pos, "there was a big problem")
                }
              }
              
              //catch other errors and hendle sensably
              case ex: Exception => {
                c.error(pos, "this was a very unexpected error, please file a bug on github: "+ex)
              }
            }
            
            //then parse at compile time
            reify { SystemProperty.parse(c.Expr[String] { Literal(Constant(raw)) }.splice) }
            
            //TODO: we could inject the copiled BigDecimal into the scala AST, using dark magic, but that's a little too complicated for this case
            
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
                SystemProperty.parse(
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