package macroTest

object Hello {
  import java.util.Date

  def main(args: Array[String]): Unit = {

    import riteofwhey.macros.Test.hello
    println("hi " + (new Date()))

    hello()

    println("hi " + (new Date()))
  }

}
