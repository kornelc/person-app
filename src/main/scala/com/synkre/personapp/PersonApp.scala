package com.synkre.personapp

import com.synkre.personapp.Person._

import scala.util.{Failure, Success}

object PersonApp extends App {
  val appConf = PersonAppConf.loadConf("config/prod/application.conf")
  val store = new PersonStore(appConf.personStoreConf)
  val people = Seq(denzelWashington, markHamill)
  store.storePeople(people)
  val people2 = store.readPeople match {
    case Success(p) => println(s"Capitalized people ${p.map(_.capitalize)}")
    case Failure(e) => println(s"Failed to create capitalized people because of ${e.getMessage}")
  }
}