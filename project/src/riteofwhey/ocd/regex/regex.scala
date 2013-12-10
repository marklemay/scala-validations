package riteofwhey.ocd.regex

object regex {
import java.util.regex.Pattern

  def parse(raw:String) = Pattern.compile(raw)
}