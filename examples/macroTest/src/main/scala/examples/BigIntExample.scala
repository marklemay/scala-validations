package examples

object BigIntExample {

  def main(args: Array[String]): Unit = {
    
    import validation.compiletime.BigInt._
    
    val bd1= int"3333333333333333333333333333333333333333333333333333"
    println(bd1)
  
    int"1,000,000,000.999999999999999" //compile error
    
    int"1000000000.999999999999999" //compile error
    
    int"--1" //compile error
    int"-1"
    
    int"9E+7" //compile error
    
    int"NAN" //compile error
    
    // string interpolation messes up validation.  but why would you use it here?
    
    int"--1${1}" //Exception in thread "main" java.lang.NumberFormatException...

  }
}