package org.example.actor

import akka.actor.{Actor, ActorLogging, ActorSystem, Props}
import akka.testkit.{ImplicitSender, TestActorRef, TestKit}
import org.scalatest.matchers.must.Matchers
import org.scalatest.wordspec.AnyWordSpecLike

class SendingActorSpec extends TestKit(ActorSystem("testSystem"))
  with AnyWordSpecLike
  with Matchers
  with ImplicitSender
  with StopSystemAfterAll {

  class SendingActor extends Actor with ActorLogging {

    import SendingActor._

    override def receive: Receive = {
      case SendingMessage(message) =>
        log.info(s"receive $message")
        sender() ! SendingMessageResult(message)
    }
  }

  object SendingActor {
    def props(): Props = Props(new SendingActor)

    case class SendingMessage(message: String)

    case class SendingMessageResult(message: String)
  }

  "A Sending Actor" must {
    "send a message to another actor when it has finished processing" in {
      import SendingActor._

      val actorRef = TestActorRef(SendingActor.props())
      actorRef ! SendingMessage("hello")

      expectMsg(SendingMessageResult("hello"))
    }
  }
}
