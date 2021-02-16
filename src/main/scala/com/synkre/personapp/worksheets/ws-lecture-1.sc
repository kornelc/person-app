
case class Person(firstName: String, lastName: String){
  def fullName: String = s"$firstName $lastName"
}

object Person {
  def denzelWashington = Person("Denzel", "Washington")
  def markHamill = Person("Mark", "Hamill")
}

val x = Person.denzelWashington

import java.io.File

import Person._
import com.typesafe.config.{Config, ConfigFactory}

import scala.annotation.switch
import scala.util.{Failure, Success, Try}
import scala.util.control.NonFatal
val people = Seq(denzelWashington, markHamill)
people.foreach(println)

def transformPerson(p: Person): Person =
  p.copy(
    firstName = p.firstName.toUpperCase,
    lastName = p.lastName.toUpperCase)

val transformedPeople = people.map(transformPerson)
transformedPeople.foreach(println)

  people
    .map(person => person.copy(
      firstName = person.firstName.toUpperCase,
      lastName = person.lastName.toUpperCase)
    )
    .map(_.lastName)
    .foreach(lastname => println(s"Last name: $lastname"))

val person = Person("joe", "smith")

val capitalPerson =
  person.copy(
    firstName = person.firstName.toUpperCase,
    lastName = person.lastName.toUpperCase)

println(s"Lower Case Person $person")
println(s"Capital Person $capitalPerson")

import org.json4s.native.JsonMethods._
import org.json4s._
import org.json4s.native.Serialization.{read, write}
import java.nio.file._

implicit val formats = DefaultFormats
val jsonText = write(people)
val path = Paths.get("/tmp/people.json")
Files.write(path, jsonText.getBytes)

try{
  val readData = Files.readAllBytes(path)
  val peopleReadFromDisk = parse(new String(readData)).extract[Seq[Person]]
  peopleReadFromDisk.foreach(println)
}catch{
  case NonFatal(e) =>
    println(s"Error while trying to read data at path")
}

def readingWithExceptionAndOption(path: String): Option[Seq[Person]] = {
  try{
    val nonExistentData = Files.readAllBytes(Paths.get(path))
    Some(parse(new String(nonExistentData)).extract[Seq[Person]])
  }catch{
    case NonFatal(e) =>
      None
  }
}
println(s"Trying to read non-existent data while catching exceptions: ${readingWithExceptionAndOption("/tmp/nonExistentData")}")

def readingWithTry(path: String): Try[Seq[Person]] = Try{
    val data = Files.readAllBytes(Paths.get(path))
    parse(new String(data)).extract[Seq[Person]]
}
println(s"Trying to read non-existent data using a try object: ${readingWithTry("/tmp/nonExistentData")}")

val allWorkedTry =
  for{
    firstCollection <- readingWithTry("/tm/path1")
    secondCollection <- readingWithTry("/tm/path2")
    thirdCollection <- readingWithTry("/tm/path3")
  } yield firstCollection ++ secondCollection ++ thirdCollection
allWorkedTry match {
  case Success(x) => println(s"All worked Try: $x")
  case Failure(e) => println(s"First failure: ${e.getMessage()}")
}

val allWorkedOption =
  for{
    firstCollection <- readingWithExceptionAndOption("/tm/path1")
    secondCollection <- readingWithExceptionAndOption("/tm/path2")
    thirdCollection <- readingWithTry("/tm/path3").toOption //Can't mix monads!
  } yield firstCollection ++ secondCollection ++ thirdCollection
println(s"All worked Option: ${allWorkedOption}")

case class PersonStoreConf(writeLocation: String)
class PersonStore(conf: PersonStoreConf){
  implicit val formats = DefaultFormats
  val path = Paths.get(conf.writeLocation)
  def storePerson(people: Seq[Person]): Unit ={
    val jsonText = write(people)
    Files.write(path, jsonText.getBytes)
  }
  def readPerson: Seq[Person] = {
    val readData = Files.readAllBytes(path)
    parse(new String(readData)).extract[Seq[Person]]
  }
}

case class PersonAppConf(peopleStoreConf: PersonStoreConf)
object PersonAppConf{
  def loadConf(path: String): PersonAppConf = {
    val mainConfig = ConfigFactory.parseFile(new File(path))
    PersonAppConf(
      loadPersonStoreConf(mainConfig.getConfig("peopleStore"))
    )
  }
  private def loadPersonStoreConf(parentConf: Config): PersonStoreConf ={
    PersonStoreConf(parentConf.getString("writeLocation"))
  }
}

object PersonApp extends App{
  val appConf = PersonAppConf.loadConf("application.conf")
  val store = new PersonStore(appConf.peopleStoreConf)
  store.storePerson(people)
  val people2 = store.readPerson
  people2.foreach(println)
}

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

