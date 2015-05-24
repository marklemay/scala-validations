package riteofwhey.ocd.regex

import java.util.regex.Pattern
import java.util.regex.PatternSyntaxException

import scala.language.experimental.macros
import scala.reflect.macros.blackbox.Context
import scala.reflect.internal.util.RangePosition



//TODO: Javadoc
object RegexValidator {

//TODO: Javadoc
  implicit class RegexHelper(val sc: StringContext) extends AnyVal {
    def r(args: Any*): Pattern = macro RegexHelperimpl
  }

  def RegexHelperimpl(c: Context)(args: c.Expr[Any]*): c.Expr[Pattern] = {
    import c.universe._

    c.prefix.tree match {
      // access data of string interpolation
      case Apply(_, List(Apply(_, rawParts))) =>

        // `parts` contain the strings a string interpolation is built of, TODO grammar?
        val parts = rawParts map { case t @ Literal(Constant(const: String)) => (const, t.pos) }

        parts match {
          //there is only one string literal
          case List((raw, pos)) => {
            
            if(raw.isEmpty()){
               c.warning(pos, "regex is empty") //somehow this can still be compiled
            }
            
            //realtime validation here
            try {
              val p = Pattern.compile(raw)
            } catch {
              case ex: PatternSyntaxException => {

                //fancyness with underlineing
                //TODO: catch the shit out of this

                val rpos = pos.asInstanceOf[scala.reflect.internal.util.OffsetPosition]

                //TODO: better class?
                val outpos = new RangePosition(rpos.source, rpos.start + ex.getIndex, rpos.start + ex.getIndex, rpos.start + ex.getIndex) 
                
                c.error(outpos.asInstanceOf[c.universe.Position], ex.getDescription())
              }
              
              //catch other errors and hendle sensably
              case ex: Exception => {
                c.error(pos, "this was a very unexpected error, please file a bug on github"+ex)
              }
            }
            
            //then parse at compile time
            reify { RegexRuntime.parse(c.Expr[String] { Literal(Constant(raw)) }.splice) }
            
            //TODO: we could inject the copiled regex into the scala AST, using dark magic, but that's a little too complicated for this case
            
          }

          case List() => {
            //don't forget the null case
            c.abort(c.enclosingPosition, "invalid")
          }
          
          
          //TODO: for the love of god write a test for this
          case _ => {
            //fall back to runtime interpolation
            
            //TODO: throw a warning to use a runtime interpolator instead?
            //get the sc
            //get args
            
//            val sc=Expr(Apply(Select(Ident(scala.StringContext), TermName("apply")), List(Literal(Constant("a")), Literal(Constant("b")), Literal(Constant("c")))))
            
            val s=c.symbolOf[List[String]]
//TODO: this is so wrong!!!!!
            reify { 
////              (c.Expr[StringContext] { rawParts }.splice
////              println(StringContext(c.Expr[Seq[String]] { rawParts }.splice))
//              val s=StringContext(c.Expr[String] { Literal(Constant("hi")) }.splice, c.Expr[String] { Literal(Constant("hi")) }.splice)
//              
//              
//              val s2=StringContext(c.Expr[String] { Literal(Constant("hi")) }.splice, c.Expr[String] { Literal(Constant("hi")) }.splice)
////              val s3 = StringContext.apply(List("a","b","c"):_*)
//              val l =c.Expr[List[String]] {
//                Apply(Select(Ident(symbolOf[List[String]]), TermName("apply")), List(Literal(Constant("a"))))
//              }.splice
//              
//              val s3=c.Expr[StringContext] {
//                Apply(Select(Ident(symbolOf[StringContext]), TermName("apply")), List(Literal(Constant("a")), Literal(Constant("s"))))
//              }.splice
              
          
                RegexRuntime.parse(
                    //string context
                (c.Expr[StringContext] {
                Apply(Select(Ident(typeOf[StringContext].typeSymbol.companionSymbol), TermName("apply")), rawParts)
              }.splice),
                    //seq of trees
              (c.Expr[Seq[Any]] {
                Apply(Select(Ident(typeOf[Seq[Any]].typeSymbol.companionSymbol), TermName("apply")), 
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