package org.example.actor

import akka.actor._
import org.example.actor.EchoActor._

class EchoActor extends Actor {
  override def receive: Receive = onMessage(0)

  private def onMessage(i: Long): Receive = {
    case Inc =>
      context.become(onMessage(i + 1))
    case IncResult =>
      sender() ! i

    case Spawn =>
      context.actorOf(EchoActor.props())
    case SpawnResult =>
      sender() ! context.children.size
  }
}

object EchoActor {
  def props(): Props = Props(new EchoActor())

  object Inc

  object IncResult

  object Spawn

  object SpawnResult
}