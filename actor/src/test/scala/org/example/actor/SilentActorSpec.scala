package org.example.actor

import akka.actor._
import akka.testkit.{TestActorRef, TestKit}
import org.scalatest.matchers.must.Matchers
import org.scalatest.wordspec.AnyWordSpecLike

import scala.collection.mutable

class SilentActorSpec extends TestKit(ActorSystem("testSystem"))
  with AnyWordSpecLike
  with Matchers
  with StopSystemAfterAll {

  class SilentActor extends Actor with ActorLogging {

    import SilentActor._

    private val _state = mutable.ListBuffer[Int]()

    def state: Seq[Int] = _state.toSeq

    override def receive: Receive = {
      case SilentMessage(message) =>
        log.info(s"receive SilentMessage($message)")
        _state += message
    }
  }

  object SilentActor {
    def props(): Props = Props(new SilentActor)

    case class SilentMessage(i: Int)
  }


  "A Silent Actor" must {
    "change state when it receives a message, single threaded" in {
      import SilentActor._

      val actor = TestActorRef[SilentActor](SilentActor.props())
      actor ! SilentMessage(1)
      actor.underlyingActor.state must (contain(1))
    }
  }
}