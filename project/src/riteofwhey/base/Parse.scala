package riteofwhey.base

//assert all not null

trait Parser[T] extends NotNull{
  
  //assert: that some imput produces a result without warnings
  //assert: mapback: if email.toString = str -> parse(str)=email
   def parse(s:String):ParseResult[T]

   //healper methd
def simpleError(s:String, msg:String)=Errors[T](Set(Promlem(msg, Set(OffsetRange(0,s.length())))))
 
}


