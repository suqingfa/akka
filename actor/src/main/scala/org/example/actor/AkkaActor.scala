package org.example.actor

import akka.actor.{ActorRef, ActorSystem}
import org.example.actor.ExampleActor.ExampleStart

object AkkaActor extends App {
  implicit val system: ActorSystem = ActorSystem()
  system.log.info("start actor system")

  val actorRef: ActorRef = ExampleActor.actorOf()
  system.log.info(s"actorRef $actorRef")

  actorRef ! ExampleStart

  system.terminate()
}
