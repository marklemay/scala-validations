package macroTest

object UrlTest {
  def main(args: Array[String]): Unit = {

    import riteofwhey.ocd.url.UrlValidator._

    val url1 = url"http://www.google.com"

    val url2 = url"http:/www.google.com"
   // val url3 = url"www.google.com"
    
    //val url4 = url"badProtocall://www.google.com"
    val url5 = url"http://www.google.com// f"
  }
}