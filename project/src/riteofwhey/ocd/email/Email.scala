package riteofwhey.ocd.email

import riteofwhey.base.Parser
import riteofwhey.base.ParseResult
import riteofwhey.base.Result
import scala.collection.immutable.Nil
import riteofwhey.base.Errors
import riteofwhey.base.Promlem
import riteofwhey.base.OffsetRange

//TODO: review from spec (there is nomeaningful spec see http://en.wikipedia.org/wiki/Email_address)

//todo: warn or error for whitespace
//todo: prove termination
//todo: warn when email looks wrong, _@gemailcom, or over agiven length

//todo specs generator

//assert local & domain do not contain @ and are not empty
case class Email(val local: String, val domain: String) extends NotNull {
  override def toString() = local + "@" + domain
}

object Email {
  //TODO: all above implicit to sting    

  object EmailParser extends Parser[Email]{
    //todo: parses quick, throws no exceptions
     override def parse(s:String):ParseResult[Email]=  s.zipWithIndex.filter(_._1 =='@').toList match {
//     case List( ('@',0)) => Result(Email("sd","fdf"));
//     case List( ('@',s.length())) => Result(Email("sd","fdf"));
     case List( ('@',loc)) => {
       val warnings=if(s.contains(" "))(Set[Promlem](Promlem("Bro, emails should not have whitespace!",Set(OffsetRange(0,0)))))else(Set[Promlem]())
       
       Result(Email(s.substring(0, loc),s.substring(loc+1, s.length())), warnings);
     }
       case List() => simpleError(s, """email must conatin a "@" character """);
       case _ =>  simpleError(s, """email must conatin a single "@" character """);
     }
  }
  
  def main(args: Array[String]) {
    println(""+Email("sd","fdf").domain )
    
    println(EmailParser.parse("hi@as"))
  }
}

