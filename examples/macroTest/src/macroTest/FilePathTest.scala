package macroTest

object FilePathTest {

  def main(args: Array[String]): Unit = {
    import riteofwhey.ocd.regex.FilePathValidator._

    val file1 = FilePath"C:/tmp/somefile.txt"

    val file2 = FilePath"C:/tmp/somefiled.txt"

    val fil3 = FilePath"z:/tmp/somefileprotected.txt"
  }
}