package riteofwhey.ocd.email

import riteofwhey.base.Errors
import riteofwhey.base.Result

object EmailValidator {

  import Email._

  import scala.reflect.macros.Context
  import language.experimental.macros

  import reflect.macros.Context
  import java.util.Date
  import scala.reflect.internal._

  implicit class EmailHelper(val sc: StringContext) extends AnyVal {
    def email(args: Any*): Email = macro EmailHelperimpl
  }

  def EmailHelperimpl(c: Context)(args: c.Expr[Any]*): c.Expr[Email] = {
    import c.universe._

    c.prefix.tree match {
      // access data of string interpolation
      case Apply(_, List(Apply(sc, rawParts))) =>

        // `parts` contain the strings a string interpolation is built of pairs of raw strings and thier position
        val parts = rawParts map { case t @ Literal(Constant(const: String)) => (const, t.pos) }

        parts match {
          case List((raw, pos)) => {
            //compiletime validation here
            Email.EmailParser.parse(raw) match {
              case Errors(errors) => {
                for (error <- errors) {
                  c.error(pos, error.msg)
                }
                reify { Email("", "") } //not valid, TODO: is there a better way to do this?
              }
              case Result(Email(local, domain), warnigs) => {
                for (warnig <- warnigs) {
                  c.warning(pos, warnig.msg)
                }
                reify {
                  Email(
                    (c.Expr[String] { Literal(Constant(local)) }.splice),
                    ((c.Expr[String] { Literal(Constant(domain)) }.splice)))
                }
              }
            }
            //TODO: then parse for real

          }

          case List() => {
            //don't forget the null case
            c.abort(c.enclosingPosition, "invalid")
          }
          
          case _ => {
            //TODO: fall back to runtime interpolation

            c.error(c.prefix.tree.pos, c.prefix.tree.toString()
                +"\n"+sc.toString()+sc.isTerm
                +"\n"+args)
               // +"\n"+ scin)//parts.map(_._1))
            
                
            reify { Email(("steve"), ("gmail.com")) }
          }
        }

      case _ =>
        c.abort(c.enclosingPosition, "invalid")  //TODO: change this to a sensible defult
    }

  }

}