package com.synkre.personapp

case class Person(firstName: String, lastName: String){
  def fullName: String = s"$firstName $lastName"
  def capitalize: Person = copy(
    firstName = firstName.toUpperCase,
    lastName = lastName.toUpperCase
  )
}

object Person {
  def denzelWashington: Person = Person("Denzel", "Washington")
  def markHamill: Person = Person("Mark", "Hamill")
}
