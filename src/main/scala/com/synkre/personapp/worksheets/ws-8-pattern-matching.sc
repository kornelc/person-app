import com.synkre.personapp.Person
import com.synkre.personapp.Person.denzelWashington

import scala.annotation.switch

val color = "red"
val result = (color: @switch) match {
  case "blue" => "It was blue"
  case "red" => "It was red"
  case _ => "We don't know what it was!"
}
println(result)

def printException(exception: Throwable): Unit ={
  val exceptionResult = exception match {
    case e: SecurityException => println("security issue")
    case e: NullPointerException => println("null reference issue")
    case e => s"Some other error ${e}"
  }
}
printException(new SecurityException("Security error"))

val personResult = denzelWashington match {
  case Person(first, _) if first == "Denzel" => "It was Denzel"
  case Person(first, _) if first == "Mark" => "It was Mark"
  case x => s"No idea who this is: $x"
}
println(personResult)

object FirstName{
  def apply(s: String) = s
  def unapply(s: String) = if(s.isEmpty || s.contains(',')) None else Some(s)
}

object FirstNames{
  def apply(s: String) = s
  def unapplySeq(s: String): Option[Seq[String]] = if(s.isEmpty) None else Some(s.split(',').map(_.trim))
}

def printFirstNames(s: String) = s match {
  case FirstName(s) => println(s"The first name was $s")
  case FirstNames(first, middle) => println(s"The first names were $first $middle")
  case _ => println("There was no name in the string")
}
printFirstNames("joe")
printFirstNames("jose, peter")
printFirstNames("")
