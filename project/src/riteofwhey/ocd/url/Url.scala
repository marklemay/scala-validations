package riteofwhey.ocd.url

import java.net.URL


//TODO: javadoc, capitalize

//this is the unvalidated class, it has the same public interface
//TODO: Rename
object Url {

  //TODO: this is like the one time ever HLists would be better
  
  
//TODO:javadoc
  implicit class UrlHelper(val sc: StringContext) extends AnyVal {
    def url(args: Any*): URL = Url.parse(sc, args)
  }  
  
  
  //TODO: javadoc
  //this parse handles the runtime context and interpolators
  //it uses the defualt string interpolator
    def parse(sc: StringContext,args: Seq[Any]):URL = 
      parse(sc.standardInterpolator({x=>x}, args))
  
  
  //TODO: javadoc
  //This is the most literal interface, used in other places for consistency
  def parse(str:String):URL= new URL(str)
  
}