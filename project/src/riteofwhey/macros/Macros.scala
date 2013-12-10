package riteofwhey.macros

import language.experimental.macros
import reflect.macros.Context
import java.util.Date
import scala.reflect.internal._
import com.sun.org.apache.xalan.internal.xsltc.compiler.util.ObjectType

object Test {
  def hello(): Unit = macro hello_impl

  def hello_impl(c: Context)(): c.Expr[Unit] = {
    import c.universe._
    val time = (new Date()).toString()
    //c.warning(c.enclosingPosition, "sup")
    //c.error(c.enclosingPosition, "it's too late for this "+time)
    //while(true){}

    reify { println("Hello World!" + c.Expr[Any] { Literal(Constant(time)) }.splice) }
  }

  def main(args: Array[String]) {
    println("hay oh")
    import reflect.runtime.universe._
    import reflect.runtime.universe._

    println(showRaw(reify { "hi" }.tree))
  }

}