package com.synkre.personapp

case class Person(firstName: String, lastName: String){
  def fullName: String = s"$firstName $lastName"
}

object Person {
  def denzelWashington = Person("Denzel", "Washington")
  def markHamill = Person("Mark", "Hamill")
}
