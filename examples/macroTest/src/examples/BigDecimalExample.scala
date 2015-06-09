package examples

object BigDecimalExample {

  def main(args: Array[String]): Unit = {
    
    import riteofwhey.ocd.bigDecimal.BigDecimalValidator._
    
    val bd1= bd"3333333333333333333333333333333333333333333333333333"
    println(bd1)
  
//    bd"1,000,000,000.999999999999999"
//    
//    bd"1000000000.999999999999999"
//    
//    bd"--1"
//    bd"-1"
//    
//    bd"0E+7"
//    
//    bd"NAN"
    
    // string interpolation messes up validation.  but why would you use it here?
    
    bd"--1${1}" //Exception in thread "main" java.lang.NumberFormatException...

  }
}