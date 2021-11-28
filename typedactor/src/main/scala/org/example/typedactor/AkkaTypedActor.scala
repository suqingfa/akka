package org.example.typedactor

import akka.actor.typed._

object AkkaTypedActor extends App {
  val system = ActorSystem(TypedActor(), "ActorSystem")

  system ! TypedActor.Start
  system ! TypedActor.Spawn("child")

  system.terminate()
}