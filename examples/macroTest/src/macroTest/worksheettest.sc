package macroTest

import java.util.regex.Pattern

object worksheettest {

    import riteofwhey.ocd.url.UrlValidator._
    
    
 url"htp://hi"




    import riteofwhey.ocd.systemProperty.SystemPropertyValidator._
    
    val os =sp"x"
    




    import riteofwhey.ocd.regex.RegexValidator._

    //use like you would
    //Pattern reg = Pattern.compile("I lost my \\w+");
    // in java

    // or
    Pattern.compile("""I lost my \w+""")
    // in scala

    r"I lost my \w+"
    //TODO: match example

    //tripple quates can be uesd, but they are not hugely helpfull
    r""".*
    
    """

    //we can catch syntax errors


    //note that scala needs to escape $, you can't win them all
  //r"regex$"

    r"regex$$"

    //interpolation works
    r"regex_${2 + 2}"

    //but interpolation will prevent compile time validation
    r"*\.*regex_${2 + 2}"

    //and you're on your own for string escaping
    val str = "some random( chars"
    r"""regex_${str}""" //Exception in thread "main" java.util.regex.PatternSyntaxException: ...

//instead use
    r"""regex_${Pattern.quote(str)}"""

    //finally it will warn you if it's empty
    val reg10 = r""
}