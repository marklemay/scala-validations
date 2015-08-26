scala-validations
==========
![usage screenshot](https://raw.githubusercontent.com/marklemay/scala-validations/master/examples/macroTest/demo.png)
You may need to turn off "implicit underlining" in eclipse to see the error underlines in all there glory.

This project no longer reflects the blog post [Scala Macros that won’t kill you](http://blog.safariflow.com/2013/12/20/scala-macros-that-wont-kill-you/), go to the [2013 version](https://github.com/marklemay/scala-validations/tree/9ea4e18d6cc2317422666cd19aedfe1fb5ad3b4c) to see that code.

This is built with scala 2.12, and I welcome all patches and pull requests!

If you use this libray, I'd love to hear about it!

Using the Validations
==========
If you are using maven, the easiest way to use this project is to add
```xml
<dependency>
	<groupId>tptp</groupId>
	<artifactId>parser</artifactId>
	<version>0.0.6-SNAPSHOT</version>
</dependency>
```
and
```xml
<repositories>
	<repository>
		<id>tptpParser-mvn-repo</id>
		<url>https://raw.github.com/marklemay/tptpParser/mvn-repo/</url>
		<snapshots>
			<enabled>true</enabled>
			<updatePolicy>always</updatePolicy>
		</snapshots>
	</repository>
</repositories>
```
to your pom.xml (we use the [poor man's repo method](http://stackoverflow.com/questions/14013644/hosting-a-maven-repository-on-github?answertab=votes#tab-top))


You can also make your project directly dependent on the [binary jar](https://raw.github.com/marklemay/tptpParser/mvn-repo/tptp/parser/0.0.6-SNAPSHOT/parser-0.0.6-20140121.033204-1.jar) or the [source jar](https://raw.github.com/marklemay/tptpParser/mvn-repo/tptp/parser/0.0.6-SNAPSHOT/parser-0.0.6-20140121.033204-1.jar).  This is not recomended becuase you will need to download transitive dependencies.

See the test file [CheckThemALL.java](https://github.com/marklemay/tptpParser/blob/master/com.theoremsandstuff.tptp.parser.tests/src/com/theoremsandstuff/tptp/parser/tests/CheckThemALL.java) for usage 


This is a simple macro library that adds some additional compile time checks

Would love contributions!


TODO: deploy the poor mans repo
TODO: test loading from the repo works
TODO: write up readme
TODO: post a few places
TODO: blog about methodology
TODO: sbt