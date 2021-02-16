package com.synkre.personapp

import com.synkre.personapp.Person._
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

import scala.util.Success

class PersonStoreSpec extends AnyFlatSpec with Matchers{
  "PersonStore" should "store and retrieve people from a JSON file" in {
    val store = new PersonStore(PersonStoreConf("/tmp/peopletest.json"))
    val people = Seq(denzelWashington, markHamill)
    store.storePeople(people)
    val Success(people2) = store.readPeople
    people should contain theSameElementsInOrderAs people2
  }
}

