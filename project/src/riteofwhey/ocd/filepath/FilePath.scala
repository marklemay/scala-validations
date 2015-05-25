package riteofwhey.ocd.filepath

import java.net.URL
import java.io.File


//TODO: javadoc, capitalize

//this is the unvalidated class, it has the same public interface
//TODO: Rename
object FilePath {

  //TODO: this is like the one time ever HLists would be better
  
  
//TODO:javadoc
  implicit class FilePathHelper(val sc: StringContext) extends AnyVal {
    def file(args: Any*): File = FilePath.parse(sc, args)
  }  
  
  
  //TODO: javadoc
  //this parse handles the runtime context and interpolators
  //it uses the defualt string interpolator
    def parse(sc: StringContext,args: Seq[Any]):File = 
      parse(sc.standardInterpolator({x=>x}, args))
  
  
  //TODO: javadoc
  //This is the most literal interface, used in other places for consistency
  def parse(str:String):File= new File(str)
  
}