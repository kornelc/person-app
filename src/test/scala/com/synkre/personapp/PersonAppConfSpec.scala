package com.synkre.personapp

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class PersonAppConfSpec extends AnyFlatSpec with Matchers{
  "PersonAppConf" should "load and parse out application.conf file" in {
    val appConf = PersonAppConf.loadConf("config/test/application.conf")
    appConf.personStoreConf shouldEqual PersonStoreConf("/tmp/people.json")
  }
}
