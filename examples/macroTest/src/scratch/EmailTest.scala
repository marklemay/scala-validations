package macroExample

object EmailTest {

  def main(args: Array[String]): Unit = {

    import riteofwhey.ocd.email.EmailValidator._

    println((email"someone@gmail.com").domain)

    val mail = email"someone@hotmail.com"

    println(mail.local)

    println(mail.domain)
    println(mail)
    
    
    val i="hi"
    
//    val mail2 = email"${i}someo${2+2}e@hotmail.com$i"
  }

}
