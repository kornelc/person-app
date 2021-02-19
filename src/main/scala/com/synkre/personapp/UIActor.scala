package com.synkre.personapp

import akka.actor.typed.Behavior
import akka.actor.typed.scaladsl.Behaviors
import com.synkre.personapp.PersonStoreActor.{Command, Load, Loaded, Store, Stored}

import scala.io.StdIn.readLine

object UIActor {
  def apply(): Behavior[Command] = Behaviors.setup { context =>
    Behaviors.receiveMessage[Command] {
      case Loaded(path, people, from) =>
        context.log.info("UI: Loaded {}", path)
        val newPath = loadedPeopleNowWhat(path, people)
        from ! Store(newPath, people, context.self)
        Behaviors.same
      case Stored(path, people, from) =>
        context.log.info("UI: Stored {}", path)
        val newPath = storedPeopleNowWhat(path, people)
        from ! Load(newPath, context.self)
        Behaviors.same
      case other =>
        context.log.info("We received an unknown message {}", other)
        Behaviors.same
    }
  }
  private def loadedPeopleNowWhat(path: String, people: Seq[Person]): String ={
    println(s"We heave loaded from $path these people $people. Where should we store them?")
    readLine
  }
  private def storedPeopleNowWhat(path: String, people: Seq[Person]): String ={
    println(s"We heave stored $people to $path. Where should where should we load from next?")
    readLine
  }
}
