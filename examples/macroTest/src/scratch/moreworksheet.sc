package scratch

import java.util.regex.Pattern

object moreworksheet {
  println("Welcome to the Scala worksheet")       //> Welcome to the Scala worksheet

  //    import riteofwhey.ocd.regex.RegexRuntime._
  import riteofwhey.ocd.regex.RegexValidator._



  //and you're on your own for string escaping
  val str = "some random( chars"                  //> str  : String = some random( chars
  
  
  //instead use
  r"regex_${Pattern.quote(str)}"                  //> res0: java.util.regex.Pattern = regex_\Qsome random( chars\E

  //finally it will warn you if the string is empty
  r""                                             //> res1: java.util.regex.Pattern = 
}