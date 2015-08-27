scala-validations
==========
![usage screenshot](https://raw.githubusercontent.com/marklemay/scala-validations/master/examples/macroTest/demo.png)

Add some additional compile time checks with macros.

This project no longer reflects the blog post [Scala Macros that won’t kill you](http://blog.safariflow.com/2013/12/20/scala-macros-that-wont-kill-you/), go to the [old version](https://github.com/marklemay/scala-validations/tree/9ea4e18d6cc2317422666cd19aedfe1fb5ad3b4c) to see that code.

I welcome all patches, pull requests, Issues, and Comments!

If you use this libray, I'd love to hear about it!

Using scala-validations
==========
If you are using maven, the easiest way to use this project is to add
```xml
<dependency>
	<groupId>scala-validations</groupId>
	<artifactId>scala-validations</artifactId>
	<version>0.1.0-SNAPSHOT</version>
</dependency>
```
and
```xml
<repositories>
	<repository>
		<id>tptpParser-mvn-repo</id>
		<url>https://raw.github.com/marklemay/scala-validations/mvn-repo/</url>
		<snapshots>
			<enabled>true</enabled>
			<updatePolicy>always</updatePolicy>
		</snapshots>
	</repository>
</repositories>
```
to your pom.xml.  Right now I use the [poor man's repo method](http://stackoverflow.com/questions/14013644/hosting-a-maven-repository-on-github?answertab=votes#tab-top).  If there's enough intrest, I'll push to Maven Central.

You can also make your project directly dependent on the [binary jar](https://raw.github.com/marklemay/scala-validations/blob/mvn-repo/scala-validations/scala-validations/0.1.0-SNAPSHOT/scala-validations-0.1.0-20150827.000420-1.jar) or the [source jar](https://raw.github.com/marklemay/scala-validations/blob/mvn-repo/scala-validations/scala-validations/0.1.0-SNAPSHOT/scala-validations-0.1.0-20150827.000420-1-sources.jar).

See the [example project](https://github.com/marklemay/scala-validations/tree/master/examples/macroTest/src/main/scala/examples) for usage.

You can always postpone the compile time checks to runtime by changing the import from 
```scala
import validation.compiletime.ClassPath._
```
to
```scala
import validation.runtime.ClassPath._
```
This will remove the custom compile time warnings and errors.

You may need to turn off "implicit underlining" in eclipse scala ide to see the error underlines in all their glory.


Committing
==========
I would love contributions!  

There are 2 maven project in this repo
 * [scala-validations](https://github.com/marklemay/scala-validations/tree/master/project) the main project that contains the macros.
 * [scala-validations-examples](https://github.com/marklemay/scala-validations/tree/master/examples/macroTest) that contains usage examples.

The project should work fine with the scala 2.11 IDE without any further configuration.

TODOs
==========
 - [ ] blog about methodology
 - [ ] sbt testing
 - [ ] sbt documentation
 - [ ] tests
 - [ ] push to maven central if there's enough intresr
