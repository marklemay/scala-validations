package examples

object BigDecimalExample {

  def main(args: Array[String]): Unit = {

    import validation.compiletime.BigDecimal._

    val bd1 = dec"3333333333333333333333333333333333333333333333333333"
    println(bd1) //3333333333333333333333333333333333333333333333333333

    dec"33333333333333333333333" + dec"6666666666666666666" //33339999999999999999999

    dec"1,000,000,000.999999999999999" //compile error

    dec"1000000000.999999999999999"

    dec"--1" //compile error
    dec"-1"

    dec"9E+700"

    dec"NAN" //compile error

    // string interpolation messes up validation.  but why would you use it here?
    dec"--1${1}" //Exception in thread "main" java.lang.NumberFormatException...

  }
}