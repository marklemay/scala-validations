package scratch

import java.util.regex.Pattern

object moreworksheet {;import org.scalaide.worksheet.runtime.library.WorksheetSupport._; def main(args: Array[String])=$execute{;$skip(117); 
  println("Welcome to the Scala worksheet")

  //    import riteofwhey.ocd.regex.RegexRuntime._
  import riteofwhey.ocd.regex.RegexValidator._;$skip(185); 



  //and you're on your own for string escaping
  val str = "some random( chars";System.out.println("""str  : String = """ + $show(str ));$skip(56); val res$0 = 
  
  
  //instead use
  r"regex_${Pattern.quote(str)}";System.out.println("""res0: java.util.regex.Pattern = """ + $show(res$0));$skip(60); val res$1 = 

  //finally it will warn you if the string is empty
  r"";System.out.println("""res1: java.util.regex.Pattern = """ + $show(res$1))}
}
