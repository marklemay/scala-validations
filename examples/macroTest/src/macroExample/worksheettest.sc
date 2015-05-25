package macroTest

import java.util.regex.Pattern

object worksheettest {



        import riteofwhey.ocd.filepath.FilePathValidator._
        

file"C:\Users\Mark\Documents\GitHub\${1}\file that doesn't exist"
                                                  //> res0: java.io.File = C:\Users\Mark\Documents\GitHub\1\file that doesn't exis
                                                  //| t




    import riteofwhey.ocd.url.UrlValidator._
    
    
// url"htp://hi"




    import riteofwhey.ocd.systemProperty.SystemPropertyValidator._
    
    val os =sp"x"                                 //> os  : String = null
    




    import riteofwhey.ocd.regex.RegexValidator._

    //use like you would
    //Pattern reg = Pattern.compile("I lost my \\w+");
    // in java

    // or
    Pattern.compile("""I lost my \w+""")          //> res1: java.util.regex.Pattern = I lost my \w+
    // in scala

    r"I lost my \w+"                              //> res2: java.util.regex.Pattern = I lost my \w+
    //TODO: match example

    //tripple quates can be uesd, but they are not hugely helpfull
    r""".*
    
    """                                           //> res3: java.util.regex.Pattern = .*
                                                  //|     
                                                  //|     

    //we can catch syntax errors


    //note that scala needs to escape $, you can't win them all
  //r"regex$"

    r"regex$$"                                    //> res4: java.util.regex.Pattern = regex$

    //interpolation works
    r"regex_${2 + 2}"                             //> res5: java.util.regex.Pattern = regex_4

    //but interpolation will prevent compile time validation
    r"*\.*regex_${2 + 2}"                         //> java.util.regex.PatternSyntaxException: Dangling meta character '*' near in
                                                  //| dex 0
                                                  //| *\.*regex_4
                                                  //| ^
                                                  //| 	at java.util.regex.Pattern.error(Unknown Source)
                                                  //| 	at java.util.regex.Pattern.sequence(Unknown Source)
                                                  //| 	at java.util.regex.Pattern.expr(Unknown Source)
                                                  //| 	at java.util.regex.Pattern.compile(Unknown Source)
                                                  //| 	at java.util.regex.Pattern.<init>(Unknown Source)
                                                  //| 	at java.util.regex.Pattern.compile(Unknown Source)
                                                  //| 	at riteofwhey.ocd.regex.RegexRuntime$.parse(RegexRuntime.scala:29)
                                                  //| 	at riteofwhey.ocd.regex.RegexRuntime$.parse(RegexRuntime.scala:24)
                                                  //| 	at macroTest.worksheettest$$anonfun$main$1.apply$mcV$sp(macroTest.worksh
                                                  //| eettest.scala:63)
                                                  //| 	at org.scalaide.worksheet.runtime.library.WorksheetSupport$$anonfun$$exe
                                                  //| cute$1.apply$mcV$sp(WorksheetSupport.scala:76)
                                                  //| 	at org.scalaide.worksheet.runtime.library.WorksheetSupport$.redirected(W
                                                  //| orksheetSupport.scala:65)
                                                  //| 	at org.scalaide.worksheet.runtime.libr
                                                  //| Output exceeds cutoff limit.

    //and you're on your own for string escaping
    val str = "some random( chars"
    r"""regex_${str}""" //Exception in thread "main" java.util.regex.PatternSyntaxException: ...

//instead use
    r"""regex_${Pattern.quote(str)}"""

    //finally it will warn you if it's empty
    val reg10 = r""
}