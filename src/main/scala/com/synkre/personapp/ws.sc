
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
val people = Seq(denzelWashington, markHamill)
people.foreach(println)

val transformedPaople = people.map(person => person.copy(
  firstName = person.firstName.toUpperCase,
  lastName = person.lastName.toUpperCase))
transformedPaople.foreach(println)

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

val readData = Files.readAllBytes(path)
val peopleReadFromDisk = parse(new String(readData)).extract[Seq[Person]]
peopleReadFromDisk.foreach(println)

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