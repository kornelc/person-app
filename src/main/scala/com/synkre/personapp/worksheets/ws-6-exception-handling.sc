import org.json4s.native.JsonMethods._
import org.json4s._
import org.json4s.native.Serialization.{read, write}
import java.nio.file._

import com.synkre.personapp.Person
import com.synkre.personapp.Person.{denzelWashington, markHamill}

import scala.util.{Failure, Success, Try}
import scala.util.control.NonFatal

val people = Seq(denzelWashington, markHamill)

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
  case Failure(e) => println(s"First failure: ${e.getMessage}")
}

val allWorkedOption =
  for{
    firstCollection <- readingWithExceptionAndOption("/tm/path1")
    secondCollection <- readingWithExceptionAndOption("/tm/path2")
    thirdCollection <- readingWithTry("/tm/path3").toOption //Can't mix monads!
  } yield firstCollection ++ secondCollection ++ thirdCollection
println(s"All worked Option: $allWorkedOption")
