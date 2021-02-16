case class Person(firstName: String, lastName: String){
  def fullName: String = s"$firstName $lastName"
}

object Person {
  def denzelWashington = Person("Denzel", "Washington")
  def markHamill = Person("Mark", "Hamill")
}
import Person._
val person = Person("joe", "smith")

val capitalPerson =
  person.copy(
    firstName = person.firstName.toUpperCase,
    lastName = person.lastName.toUpperCase)

println(s"Lower Case Person $person")
println(s"Capital Person $capitalPerson")

def transformPerson(p: Person): Person =
  p.copy(
    firstName = p.firstName.toUpperCase,
    lastName = p.lastName.toUpperCase)

val people = Seq(denzelWashington, markHamill)
val transformedPeople = people.map(transformPerson)
transformedPeople.foreach(println)

people
  .map(person => person.copy(
    firstName = person.firstName.toUpperCase,
    lastName = person.lastName.toUpperCase)
  )
  .map(_.lastName)
  .foreach(lastname => println(s"Last name: $lastname"))



