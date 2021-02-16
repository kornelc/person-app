package com.synkre.personapp

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class PersonSpec extends AnyFlatSpec with Matchers{
  "Person" should "compute last full name" in {
    val person = Person("Joe", "Smith")
    person.fullName shouldEqual "Joe Smith"
  }
  it should "capitalize its first and last name" in {
    val capitalPerson = Person("Joe", "Smith").capitalize
    capitalPerson.firstName shouldEqual "JOE"
    capitalPerson.lastName shouldEqual "SMITH"
    capitalPerson.fullName shouldEqual "JOE SMITH"
  }
}
