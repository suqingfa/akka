package org.example.actor

import akka.actor._

class ExampleActor extends Actor with ActorLogging {

  import ExampleActor._

  override def receive: Receive = {
    case ExampleStart =>
      log.info(s"$Name is start")
  }

  override def preStart(): Unit = log.info(s"$Name pre start")

  override def postStop(): Unit = log.info(s"$Name post stop")
}

object ExampleActor {
  def Name = "ExampleActor"

  def actorOf()(implicit actorSystem: ActorSystem): ActorRef = {
    val props = Props(new ExampleActor())
    actorSystem.actorOf(props, Name)
  }

  sealed abstract class ExampleCommand

  object ExampleStart extends ExampleCommand
}