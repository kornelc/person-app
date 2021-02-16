package com.synkre.personapp

import java.nio.file.{Files, Paths}

import org.json4s.DefaultFormats
import org.json4s.native.JsonMethods.parse
import org.json4s.native.Serialization.write

class PersonStore{
  implicit val formats = DefaultFormats
  val path = Paths.get("/tmp/people.json")
  def storePeople(people: Seq[Person]): Unit ={
    val jsonText = write(people)
    Files.write(path, jsonText.getBytes)
  }
  def readPeople: Seq[Person] = {
    val readData = Files.readAllBytes(path)
    parse(new String(readData)).extract[Seq[Person]]
  }
}