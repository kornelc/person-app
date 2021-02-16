import org.json4s.native.JsonMethods._
import org.json4s._
import org.json4s.native.Serialization.{read, write}
import java.nio.file._

import com.synkre.personapp.Person
import com.synkre.personapp.Person._

import scala.util.control.NonFatal

implicit val formats = DefaultFormats
val people = Seq(denzelWashington, markHamill)
val jsonText = write(people)
val path = Paths.get("/tmp/people.json")
Files.write(path, jsonText.getBytes)

val readData = Files.readAllBytes(path)
val peopleReadFromDisk = parse(new String(readData)).extract[Seq[Person]]
peopleReadFromDisk.foreach(println)
