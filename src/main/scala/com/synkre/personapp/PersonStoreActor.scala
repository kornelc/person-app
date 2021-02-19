package com.synkre.personapp

import java.nio.file.{Files, Paths}

import akka.actor.typed.scaladsl.Behaviors
import akka.actor.typed.{ActorRef, Behavior}
import org.json4s.DefaultFormats
import org.json4s.native.JsonMethods.parse
import org.json4s.native.Serialization.write

import scala.util.Try

object PersonStoreActor {
  sealed trait Command
  final case class Load(path: String, replyTo: ActorRef[Command]) extends Command
  final case class Loaded(path: String, people: Seq[Person], from: ActorRef[Command]) extends Command
  final case class Store(path: String, people: Seq[Person], replyTo: ActorRef[Command]) extends Command
  final case class Stored(path: String, people: Seq[Person], from: ActorRef[Command]) extends Command

  def apply(): Behavior[Command] =
    Behaviors.setup { context =>
      Behaviors.receiveMessage[Command] {
        case Load(path, replyTo) =>
          context.log.info("Load {}", path)
          val loadedPeople = readPeople(path).foreach { loadedPeople =>
            replyTo ! Loaded(path, loadedPeople, context.self)
          }
          Behaviors.same
        case Store(path, people, replyTo) =>
          context.log.info("Load {}", path)
          storePeople(path, people).foreach { _ =>
            replyTo ! Stored(path, people, context.self)
          }
          Behaviors.same
        case other =>
          context.log.info("We received an unknown message {}", other)
          Behaviors.same
      }
    }
  implicit val formats = DefaultFormats
  private def storePeople(p: String, people: Seq[Person]): Try[Unit] = Try{
    val path = Paths.get(p)
    val jsonText = write(people)
    Files.write(path, jsonText.getBytes)
  }
  private def readPeople(p: String): Try[Seq[Person]] = Try{
    val path = Paths.get(p)
    val readData = Files.readAllBytes(path)
    parse(new String(readData)).extract[Seq[Person]]
  }

}
