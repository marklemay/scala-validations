package scratch

import java.util.Date
import riteofwhey.macros.Test.hello
import scala.reflect.runtime.{universe => ru}

object Hello {
  import java.util.Date

  def main(args: Array[String]): Unit = {
        
        import riteofwhey.ocd.filepath.FilePathValidator._
        
    

    
    
    
    

    import riteofwhey.macros.Test.hello
    println("hi " + (new Date()))

    hello()

    println(raw"hi\th${1}")
    
    println("""hi\th${1}""")
    
    println(raw"""hi\th${1}""")
    
    println("hi " + (new Date()))
    
    import scala.reflect.runtime.{universe => ru}
    
//    import ru._
    
//    var wab= new WrappedArrayBuilder[String]()
    
    println(StringContext("a","b","c"))
    println(StringContext.apply(List("a","b","c"):_*))
//    WrappedArray.make(Seq("a","b","c"))
  
    val tree=ru.reify { 
//      List("a")
      StringContext("a","s")
//      val s="hi"
//      StringContext("a","b","c",ru.Expr[String] { Literal(Constant("hi")) }.splice,"d")
    }
    
    val trees=ru.showRaw(tree)
    
    println(trees)
    
    println("ffffffffffffff")
  }

}
