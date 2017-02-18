package examples

import java.util.regex.Pattern

object RegexExample {

  import validation.compiletime.Regex._

  // use like you would
  val reg = Pattern.compile("""I lost my \w+""")  //> reg  : java.util.regex.Pattern = I lost my \w+
  // in Scala

  val reg1 = r"I lost my \w+"                     //> reg1  : java.util.regex.Pattern = I lost my \w+
  //TODO: match example?

  

  //it can catch syntax errors at compile time
  r"*\.*xxxx" //compile error

  r"(dsdss" //compile error

  r"dsd)ss" //compile error
  
  
  //triple quates can be uesed, but they are not hugely helpfull
  r""".*
    
    """                                           //> res0: java.util.regex.Pattern = .*
                                                  //|     
                                                  //|     
  

  //note that scala needs to escape $ (you can't win them all)
  // r"regex$"  //compile error

  r"regex$$"                                      //> res1: java.util.regex.Pattern = regex$

  //interpolation works
  r"regex_${2 + 2}"                               //> res2: java.util.regex.Pattern = regex_4
  
  //but interpolation will prevent compile time validation
  r"*\.*regex_${2 + 2}"                           //> java.util.regex.PatternSyntaxException: Dangling meta character '*' near ind
                                                  //| ex 0
                                                  //| *\.*regex_4
                                                  //| ^
                                                  //|   at java.util.regex.Pattern.error(Unknown Source)
                                                  //|   at java.util.regex.Pattern.sequence(Unknown Source)
                                                  //|   at java.util.regex.Pattern.expr(Unknown Source)
                                                  //|   at java.util.regex.Pattern.compile(Unknown Source)
                                                  //|   at java.util.regex.Pattern.<init>(Unknown Source)
                                                  //|   at java.util.regex.Pattern.compile(Unknown Source)
                                                  //|   at riteofwhey.ocd.regex.RegexRuntime$.parse(RegexRuntime.scala:29)
                                                  //|   at riteofwhey.ocd.regex.RegexRuntime$.parse(RegexRuntime.scala:24)
                                                  //|   at scratch.moreworksheet$$anonfun$main$1.apply$mcV$sp(scratch.moreworksh
                                                  //| eet.scala:12)
                                                  //|   at org.scalaide.worksheet.runtime.library.WorksheetSupport$$anonfun$$exe
                                                  //| cute$1.apply$mcV$sp(WorksheetSupport.scala:76)
                                                  //|   at org.scalaide.worksheet.runtime.library.WorksheetSupport$.redirected(W
                                                  //| orksheetSupport.scala:65)
                                                  //|   at org.scalaide.worksheet.runtime.library.WorksheetSupport$.$
                                                  //| Output exceeds cutoff limit.
  
  
  
  //and you're on your own for string escaping
  val str = "some random( chars"                  //> str  : String = some random( chars
  r"regex_${str}"                                 //> java.util.regex.PatternSyntaxException: Unclosed group near index 24
                                                  //| regex_some random( chars
                                                  //|                         ^
                                                  //|   at java.util.regex.Pattern.error(Unknown Source)
                                                  //|   at java.util.regex.Pattern.accept(Unknown Source)
                                                  //|   at java.util.regex.Pattern.group0(Unknown Source)
                                                  //|   at java.util.regex.Pattern.sequence(Unknown Source)
                                                  //|   at java.util.regex.Pattern.expr(Unknown Source)
                                                  //|   at java.util.regex.Pattern.compile(Unknown Source)
                                                  //|   at java.util.regex.Pattern.<init>(Unknown Source)
                                                  //|   at java.util.regex.Pattern.compile(Unknown Source)
                                                  //|   at riteofwhey.ocd.regex.RegexRuntime$.parse(RegexRuntime.scala:29)
                                                  //|   at riteofwhey.ocd.regex.RegexRuntime$.parse(RegexRuntime.scala:24)
                                                  //|   at scratch.moreworksheet$$anonfun$main$1.apply$mcV$sp(scratch.moreworksh
                                                  //| eet.scala:15)
                                                  //|   at org.scalaide.worksheet.runtime.library.WorksheetSupport$$anonfun$$exe
                                                  //| cute$1.apply$mcV$sp(WorksheetSupport.scala:76)
                                                  //|   at org.scalaide.worksheet.runtime.libr
                                                  //| Output exceeds cutoff limit.
    
  //instead use
  r"regex_${Pattern.quote(str)}"                  //> res0: java.util.regex.Pattern = regex_\Qsome random( chars\E

  //finally it will warn you if the string is empty
  r"" // compile warning
  
}
