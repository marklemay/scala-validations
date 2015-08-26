package examples

import org.apache.commons.io.IOUtils

object UrlExample {
  def main(args: Array[String]): Unit = {

    import validation.compiletime.Url._

    val url1 = url"http://www.google.com"

    println(IOUtils.toString(url1)) // <!doctype html><html itemscope...

    // will not warn if the url is unreachable
    url"http:/www.fakewebpage.com"

    // but it will warn if you leave off the protocol
    url"www.google.com" // compile error

    //or if you have a bad protocol
    url"badprotocol://www.google.com" // compile error

  }
}