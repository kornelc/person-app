package com.synkre.personapp

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class PersonSpec extends AnyFlatSpec with Matchers{
  "Person" should "compute last full name" in {
    val person = Person("Joe", "Smith")
    person.fullName shouldEqual "Joe Smith"
  }
}
