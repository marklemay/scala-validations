package examples

object BigIntExample {

  def main(args: Array[String]): Unit = {
    
    import riteofwhey.ocd.bigInt.BigIntValidator._
    
    val bd1= bi"3333333333333333333333333333333333333333333333333333"
    println(bd1)
  
//    bi"1,000,000,000.999999999999999"
//    
//    bi"1000000000.999999999999999"
//    
//    bi"--1"
//    bi"-1"
//    
//    bi"0E+7"
//    
//    bi"NAN"
    
    // string interpolation messes up validation.  but why would you use it here?
    
    bi"--1${1}" //Exception in thread "main" java.lang.NumberFormatException...

  }
}