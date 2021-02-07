package com.synkre.personapp

import com.synkre.personapp.Person.{denzelWashington, markHamill}
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class PersonStoreSpec extends AnyFlatSpec with Matchers{
  "PersonStore" should "store and retrieve people from a JSON file" in {
    val store = new PersonStore(PersonStoreConf("/tmp/peopletest.json"))
    val people = Seq(denzelWashington, markHamill)
    store.storePerson(people)
    val people2 = store.readPerson
    people2 should contain theSameElementsInOrderAs people
  }
}
