package org.example.actor

import akka.actor.{Actor, ActorLogging, ActorSystem, Props}
import akka.testkit.{EventFilter, TestActorRef, TestKit}
import com.typesafe.config.ConfigFactory
import org.scalatest.matchers.must.Matchers
import org.scalatest.wordspec.AnyWordSpecLike

class SideEffectingActorSpec extends TestKit(ActorSystem("testSystem", ConfigFactory.parseString("akka.loggers = [akka.testkit.TestEventListener]")))
  with AnyWordSpecLike
  with Matchers
  with StopSystemAfterAll {

  class SideEffectingActor extends Actor with ActorLogging {

    import SideEffectingActor._

    override def receive: Receive = {
      case SideEffectingMessage(message) =>
        log.info(s"receive $message")
    }
  }

  object SideEffectingActor {
    def props(): Props = Props(new SideEffectingActor)

    case class SideEffectingMessage(message: String)
  }

  " The SideEffectingActor" must {
    "say receive hello when a 'hello' is send to is" in {
      import SideEffectingActor._

      val actorRef = TestActorRef[SideEffectingActor](SideEffectingActor.props())

      EventFilter.info(message = "receive hello", occurrences = 1).intercept {
        actorRef ! SideEffectingMessage("hello")
      }
    }
  }
}