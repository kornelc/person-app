
val hello = "Hello World!"

println(hello)

val x = 1

val y = 1 + x

println(s"My first variables are x=$x and y=$y")

class PersonTheHardWay(val firstName: String, val lastName: String){
  def fullName: String = s"$firstName $lastName"
}

object PersonTheHardWay{
  def denzelWashington = new PersonTheHardWay("Denzel", "Washington")
  def markHamill = new PersonTheHardWay("Mark", "Hamill")
}

case class Person(firstName: String, lastName: String){
  def fullName: String = s"$firstName $lastName"
}

object Person {
  def denzelWashington = Person("Denzel", "Washington")
  def markHamill = Person("Mark", "Hamill")
}

import Person._
var denzelUnsafe =  denzelWashington
denzelUnsafe = markHamill
println(denzelUnsafe)

val denzel =  denzelWashington
//the following generates compiler error
//denzel = markHamill
val jedi = markHamill
println(denzel)
println(jedi)

val people = Seq(denzelWashington, markHamill)
people.foreach(println)

//prints: Person(Denzel,Washington) Person(Mark,Hamill)
