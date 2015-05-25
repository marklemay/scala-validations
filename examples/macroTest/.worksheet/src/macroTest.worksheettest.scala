package macroTest

import java.util.regex.Pattern

object worksheettest {



        import riteofwhey.ocd.filepath.FilePathValidator._;import org.scalaide.worksheet.runtime.library.WorksheetSupport._; def main(args: Array[String])=$execute{;$skip(212); val res$0 = 
        

file"C:\Users\Mark\Documents\GitHub\${1}\file that doesn't exist"




    import riteofwhey.ocd.url.UrlValidator._
    
    
// url"htp://hi"




    import riteofwhey.ocd.systemProperty.SystemPropertyValidator._;System.out.println("""res0: java.io.File = """ + $show(res$0));$skip(170); 
    
    val os =sp"x"
    




    import riteofwhey.ocd.regex.RegexValidator._;System.out.println("""os  : String = """ + $show(os ));$skip(206); val res$1 = 

    //use like you would
    //Pattern reg = Pattern.compile("I lost my \\w+");
    // in java

    // or
    Pattern.compile("""I lost my \w+""");System.out.println("""res1: java.util.regex.Pattern = """ + $show(res$1));$skip(38); val res$2 = 
    // in scala

    r"I lost my \w+";System.out.println("""res2: java.util.regex.Pattern = """ + $show(res$2));$skip(118); val res$3 = 
    //TODO: match example

    //tripple quates can be uesd, but they are not hugely helpfull
    r""".*
    
    """;System.out.println("""res3: java.util.regex.Pattern = """ + $show(res$3));$skip(130); val res$4 = 

    //we can catch syntax errors


    //note that scala needs to escape $, you can't win them all
  //r"regex$"

    r"regex$$";System.out.println("""res4: java.util.regex.Pattern = """ + $show(res$4));$skip(49); val res$5 = 

    //interpolation works
    r"regex_${2 + 2}";System.out.println("""res5: java.util.regex.Pattern = """ + $show(res$5));$skip(88); val res$6 = 

    //but interpolation will prevent compile time validation
    r"*\.*regex_${2 + 2}";System.out.println("""res6: java.util.regex.Pattern = """ + $show(res$6));$skip(85); 

    //and you're on your own for string escaping
    val str = "some random( chars";System.out.println("""str  : String = """ + $show(str ));$skip(97); val res$7 = 
    r"""regex_${str}""";System.out.println("""res7: java.util.regex.Pattern = """ + $show(res$7));$skip(54); val res$8 =  //Exception in thread "main" java.util.regex.PatternSyntaxException: ...

//instead use
    r"""regex_${Pattern.quote(str)}""";System.out.println("""res8: java.util.regex.Pattern = """ + $show(res$8));$skip(66); 

    //finally it will warn you if it's empty
    val reg10 = r"";System.out.println("""reg10  : java.util.regex.Pattern = """ + $show(reg10 ))}
}
