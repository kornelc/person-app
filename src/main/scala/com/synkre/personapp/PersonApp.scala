package com.synkre.personapp

import com.synkre.personapp.Person.{denzelWashington, markHamill}

object PersonApp extends App {
  val appConf = PersonAppConf.loadConf("config/prod/application.conf")
  val store = new PersonStore(appConf.personStoreConf)
  val people = Seq(denzelWashington, markHamill)
  store.storePerson(people)
  val people2 = store.readPerson
  people2.foreach(println)
}