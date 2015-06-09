regex code review
package structure
documentation
general macro stuff
variable names
what's the best way to unit test macros?


may need to turn off implicit underlining to see the error underlines in all there glory.
TODO: seems like a light bug in scalaide

stuff about things

//TODO: connection string


TODO: from https://docs.google.com/spreadsheets/d/1IldmSJldpwaPbUSunU16YVW1WcU3kUaTROyXZ7I-cjw/edit#gid=734066628
X java.util.regex.Pattern.compile	3248	regex
java.io.File	2944	file
X java.lang.System.getProperty	2249	prop
java.nio.file.Paths.get	1733	file
? java.lang.System.setProperty	1045	prop
? java.util.Properties.setProperty	876	prop
? java.util.Properties.put	778	prop
java.nio.file.Path.resolve	674	file
java.lang.String.getBytes	672	charset
java.nio.charset.Charset.forName	653	charset
X java.math.BigDecimal	588	num
java.net.URI	539	URI
java.lang.Class.getResourceAsStream	534	classpath
? java.util.Properties.getProperty	530	prop
java.util.Scanner	490	file
X java.net.URL	477	url
X java.math.BigInteger	475	num
- java.util.regex.Pattern.matcher	449	regex
java.io.File.createTempFile	439	file



TODO: javadoc the public methods
TODO: Sub line underlining
TODO: Handle partial correctness when applicable
TODO: document partial correctness
TODO: test both styles " and """

TODO: best to let the error line run to the end?
TODO: get this code reviewed


TODO: remove email example
TODO: mavenize
TODO: deploy the poor mans repo
TODO: write up readme
TODO: post a few places
TODO: blog about methodology

todo:
    println(raw"hi\th${1}")
    
    println("""hi\th${1}""")
    
    println(raw"""hi\th${1}""")
    
    does not work like you'd think
    
    perhaps make the multiple versions of classes be similar to the immutable/mutable collections