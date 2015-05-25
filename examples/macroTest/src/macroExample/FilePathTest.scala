package macroExample

object FilePathTest {

  def main(args: Array[String]): Unit = {
        import riteofwhey.ocd.filepath.FilePathValidator._

    val file1 = file"C:/tmp/somefile.txt"

    val file2 = file"C:/tmp/somefiled.txt"

    val fil3 = file"z:/tmp/somefileprotected.txt"
  }
}