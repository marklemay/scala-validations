package riteofwhey.ocd.bigInt

//TODO: javadoc, capitalize

//this is the unvalidated class, it has the same public interface
//TODO: Rename
object BigIntUnsafe {

  //TODO: this is like the one time ever HLists would be better
  
  
//TODO:javadoc
  implicit class BigIntHelper(val sc: StringContext) extends AnyVal {
    def bi(args: Any*): BigInt = BigIntUnsafe.parse(sc, args)
  }  
  
  
  //TODO: javadoc
  //this parse handles the runtime context and interpolators
  //it uses the defualt string interpolator
    def parse(sc: StringContext,args: Seq[Any]):BigInt = 
      parse(sc.standardInterpolator({x=>x}, args))
  
  
  //TODO: javadoc
  //This is the most literal interface, used in other places for consistency
  def parse(str:String):BigInt= BigInt(str)
  
}