package org.example.typedactor

import akka.actor.typed._
import akka.actor.typed.scaladsl._
import org.example.typedactor.TypedActor._

class TypedActor(context: ActorContext[Command]) extends AbstractBehavior(context) {
  override def onMessage(message: Command): Behavior[Command] = {
    message match {
      case Start =>
        context.log.info("{} start", context.self)
        this
      case Spawn(name) =>
        val child = context.spawn(TypedActor(), name)
        child ! Start
        this
    }
  }

  override def onSignal: PartialFunction[Signal, Behavior[Command]] = {
    case PostStop =>
      context.log.info("{} post stop", context.self)
      this
  }
}

object TypedActor {
  sealed trait Command

  object Start extends Command

  case class Spawn(name: String) extends Command

  def apply(): Behavior[Command] = Behaviors.setup(new TypedActor(_))
}