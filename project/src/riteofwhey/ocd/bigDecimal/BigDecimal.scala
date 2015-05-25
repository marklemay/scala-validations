package riteofwhey.ocd.bigDecimal


//TODO: javadoc, capitalize

//this is the unvalidated class, it has the same public interface
//TODO: Rename
object BigDecimalRuntime {

  //TODO: this is like the one time ever HLists would be better
  
  
//TODO:javadoc
  implicit class BigDecimalHelper(val sc: StringContext) extends AnyVal {
    def bd(args: Any*): BigDecimal = BigDecimalRuntime.parse(sc, args)
  }  
  
  
  //TODO: javadoc
  //this parse handles the runtime context and interpolators
  //it uses the defualt string interpolator
    def parse(sc: StringContext,args: Seq[Any]):BigDecimal = 
      parse(sc.standardInterpolator({x=>x}, args))
  
  
  //TODO: javadoc
  //This is the most literal interface, used in other places for consistency
  def parse(str:String):BigDecimal= BigDecimal(str)
  
}