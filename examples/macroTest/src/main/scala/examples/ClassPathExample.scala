package examples

import org.apache.commons.io.IOUtils

object ClassPathExample {

  def main(args: Array[String]): Unit = {
    import validation.compiletime.ClassPath._

    val file1 = resource"/path/to/some/file.txt"

    println(IOUtils.toString(file1)) // Hi!

    resource"/path/to/some/fil.txt" // compile warning

    resource"/path/too/some/file.txt"  // compile warning

    // it looks like it validates .. correctly, but I'm sure there are related bugs
    resource"/path/to/some/doesnotexist/../file.txt"
    
    // interpolation will do the best it can with compile time information
    
    resource"/path/too/${"dynamic path"}"  // compile warning
    
    resource"/path/to/${"dynamic path"}"
    
    resource"${"dynamic path"}"
    
    resource"/${"dynamic path"}"
    
  }
}