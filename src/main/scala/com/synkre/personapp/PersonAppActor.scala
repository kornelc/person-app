package com.synkre.personapp

import akka.actor.typed.{ActorSystem, Behavior}
import akka.actor.typed.scaladsl.Behaviors

object PersonAppActor extends App{
  final case class StoreInitialPeople(people: Seq[Person])
  def apply(): Behavior[StoreInitialPeople] = Behaviors.receive { (context, message) =>
    context.log.info("Store initial people {}", message.people)
    Behaviors.setup { context =>
      val store = context.spawn(PersonStoreActor(), "PeopleStoreActor")
      val ui = context.spawn(UIActor(), "UIActor")
      context.log.info("Actors created")
      store ! PersonStoreActor.Store(
        "/tmp/people.json",
        message.people,
        ui)
      Behaviors.same
    }
  }

  val system: ActorSystem[PersonAppActor.StoreInitialPeople] =
    ActorSystem(PersonAppActor(), "PersonActorSystem")

  system ! StoreInitialPeople(Person("Joe", "Smith") :: Person("John", "Tailor") :: Nil)
}

