package com.synkre.personapp

import java.nio.file.{Files, Paths}

import org.json4s.DefaultFormats
import org.json4s.native.JsonMethods.parse
import org.json4s.native.Serialization.write

import scala.util.Try

class PersonStore{
  implicit val formats = DefaultFormats
  val path = Paths.get("/tmp/people.json")
  def storePeople(people: Seq[Person]): Try[Unit] = Try{
    val jsonText = write(people)
    Files.write(path, jsonText.getBytes)
  }
  def readPeople: Try[Seq[Person]] = Try{
    val readData = Files.readAllBytes(path)
    parse(new String(readData)).extract[Seq[Person]]
  }
}