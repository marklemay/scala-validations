package riteofwhey.ocd.systemProperty


//TODO: javadoc, capitalize

//this is the unvalidated class, it has the same public interface
//TODO: Rename
object SystemProperty {

  //TODO: this is like the one time ever HLists would be better
  
  
//TODO:javadoc
  implicit class SystemPropertyHelper(val sc: StringContext) extends AnyVal {
    def sp(args: Any*): String = SystemProperty.parse(sc, args)
  }  
  
  
  //TODO: javadoc
  //this parse handles the runtime context and interpolators
  //it uses the defualt string interpolator
    def parse(sc: StringContext,args: Seq[Any]):String = 
      parse(sc.standardInterpolator({x=>x}, args))
  
  
  //TODO: javadoc
  //This is the most literal interface, used in other places for consistency
  def parse(str:String):String= java.lang.System.getProperty(str)
  
}