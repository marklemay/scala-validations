package riteofwhey.ocd.bigInt


import scala.language.experimental.macros
import scala.reflect.macros.blackbox.Context
import scala.reflect.internal.util.RangePosition



//TODO: Javadoc
object BigIntValidator {

//TODO: Javadoc, basicly https://docs.oracle.com/javase/7/docs/api/java/math/BigDecimal.html#BigDecimal%28java.lang.String%29
  implicit class BigIntHelper(val sc: StringContext) extends AnyVal {
    def bi(args: Any*): BigInt = macro BigIntHelperimpl
  }

  def BigIntHelperimpl(c: Context)(args: c.Expr[Any]*): c.Expr[BigInt] = {
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
              //TODO: could copy the java source to get better error messages, but thats a bit much for this
              val p = BigInt(raw)
            } catch {
              case ex: NumberFormatException => {
                if(ex.getMessage!=null){
                c.error(pos, ex.getMessage)
                }else{
                c.error(pos, "there was a formatting error in your number")
                }
              }
              
              //catch other errors and hendle sensably
              case ex: Exception => {
                c.error(pos, "this was a very unexpected error, please file a bug on github: "+ex)
              }
            }
            
            //then parse at compile time
            reify { BigIntUnsafe.parse(c.Expr[String] { Literal(Constant(raw)) }.splice) }
            
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
                BigIntUnsafe.parse(
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