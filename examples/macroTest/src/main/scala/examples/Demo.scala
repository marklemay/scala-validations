package examples

object Demo {

  def main(args: Array[String]): Unit = {

    import validation.compiletime.BigDecimal._
    import validation.compiletime.BigInt._
    import validation.compiletime.SystemProperty._
    import validation.compiletime.ClassPath._
    import validation.compiletime.FilePath._
    import validation.compiletime.Url._
    import validation.compiletime.Regex._

    val reg1 = r"I lost my (\w+) in the park"
    val reg2 = r"I lost my \w+) in the park"

    val resource1 = resource"/path/to/some/file.txt"
    val resource2 = resource"/path/tosome/file.txt"

    val file3 = file"/path/to/some/file.txt"
    val file4 = file"/path/too/some/file.txt"

    val bigDecimal = dec"200" + dec"1,000"

    val bigInt = int"-3" + int"2.2"

    val url1 = url"http://www.google.com"
    val url2 = url"htp://www.google.com"

    val operatingSystem1 = sp"os.name"
    val operatingSystem2 = sp"os_name"
  }
}