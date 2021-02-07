package com.synkre.personapp

import java.nio.file.{Files, Paths}

import org.json4s.DefaultFormats
import org.json4s.native.JsonMethods.parse
import org.json4s.native.Serialization.write

case class Person(firstName: String, lastName: String){
  def fullName: String = s"$firstName $lastName"
}

object Person {
  def denzelWashington = Person("Denzel", "Washington")
  def markHamill = Person("Mark", "Hamill")
}

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
