package org.example.actor

import akka.actor._
import akka.pattern.ask
import akka.testkit._
import akka.util.Timeout
import org.example.actor.EchoActor._
import org.scalatest.matchers.must.Matchers
import org.scalatest.wordspec.AnyWordSpecLike

import scala.concurrent._
import scala.concurrent.duration.DurationInt
import scala.language.postfixOps

class PerformanceSpec extends TestKit(ActorSystem("testSystem"))
  with AnyWordSpecLike
  with Matchers
  with ImplicitSender
  with StopSystemAfterAll {
  val actorRef: ActorRef = system.actorOf(EchoActor.props(), "echo")
  implicit val timeout: Timeout = 60 second

  "A echo actor" must {
    "test inc" in {
      system.log.info("start inc")

      (1 to 10_000_000).foreach(_ => actorRef ! Inc)

      val result = Await.result(actorRef ? IncResult, timeout.duration)

      system.log.info(s"end inc, result: $result")
    }

    "test spawn" in {
      system.log.info("start spawn")

      (1 to 1_000_000).foreach(_ => actorRef ! Spawn)

      val result = Await.result(actorRef ? SpawnResult, timeout.duration)

      system.log.info(s"end spawn, result: $result")
    }
  }
}
