package examples

import org.apache.commons.io.FileUtils

object FilePathExample {

  def main(args: Array[String]): Unit = {
    import validation.compiletime.FilePath._

    //note that this example will warn differently depend on your local configuration

    val file1 = file"""C:\path\to\some\file.txt"""

    print(FileUtils.readLines(file1)) // [Hi!]

    // Windows style paths work fine
    file"""C:\path\to\some\file.txt"""

    // Unix style paths work fine
    file"/path/to/some/file.txt"

    file"/path/too/some/file.txt" // compile warning

    
    //relative paths work, if you have them set up right in your editor
    
    file"relative/path/to/a/file.txt"
    
    file"relative/pathh/to/a/file.txt" // compile warning
    
    
    //paths to directories also work, because java.
    
    file"relative/path"
    
    file"relative/pathh" // compile warning
    
    
    // it looks like it validates .. correctly, but I'm sure there are related bugs
    
    file"/path/to/some/doesnotexist/../file.txt"
    
    
    // interpolation will do the best it can with compile time information
    
    file"/path/to/some/${"dynamic path"}"
    
    file"/path/too/some/${"dynamic path"}" // compile warning
    
    file"${"dynamic path"}"
    
    file"/${"dynamic path"}"
  }
}