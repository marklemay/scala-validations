package macroExample

import java.util.regex.Pattern

//rename
object RegexExample {

  def main(args: Array[String]): Unit = {
    //    import riteofwhey.ocd.regex.RegexRuntime._
    import riteofwhey.ocd.regex.RegexValidator._

    //use like you would
    //Pattern reg = Pattern.compile("I lost my \\w+");
    // in java

    // or
    val reg = Pattern.compile("""I lost my \w+""");
    // in scala

    val reg1 = r"I lost my \w+"
    //TODO: match example

    //tripple quates can be uesd, but they are not hugely helpfull
    val reg2 = r""".*"""

    //we can catch syntax errors
//    val bad1 = r"*\.*xxxx"
//
//    val bad2 = r"(dsdss"
//
//    val bad3 = r"dsd)ss"

    //note that scala needs to escape $, you can't win them all
    //    val reg5 = r"regex$"

    val reg6 = r"regex$$"
    println(reg6) //regex$

    //interpolation works
    val reg7 = r"regex_${2 + 2}"
    println(reg7) //regex_4

    //but interpolation will prevent compile time validation
    val reg8 = r"""*\.*regex_${2 + 2}""" //Exception in thread "main" java.util.regex.PatternSyntaxException: ...

    //and you're on your own for string escaping
    val str = "some random( chars"
    val reg9 = r"""regex_${str}""" //Exception in thread "main" java.util.regex.PatternSyntaxException: ...

    val reg9Better = r"""regex_${Pattern.quote(str)}"""

    //finally it will warn you if it's empty
    val reg10 = r""
  }

}
