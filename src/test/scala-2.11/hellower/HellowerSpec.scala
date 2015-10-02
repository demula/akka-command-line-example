package hellower

import akka.actor.{ActorSystem, Props}
import akka.testkit.{ImplicitSender, TestActorRef, TestKit}
import org.scalatest.{BeforeAndAfterAll, FlatSpecLike, Matchers}

class HellowerSpec(_system: ActorSystem)
  extends TestKit(_system)
  with ImplicitSender
  with Matchers
  with FlatSpecLike
  with BeforeAndAfterAll {

  def this() = this(ActorSystem("HellowerSpec"))

  override def afterAll: Unit = {
    shutdown()
  }

  "An HellowerActor" should "be able to set a last person name" in {
    val hellower = TestActorRef(Props[HellowerActor])
    hellower ! SayHelloTo("testkit")
    hellower.underlyingActor.asInstanceOf[HellowerActor].lastName should be("testkit")
  }

  it should "be able to get a new greeting" in {
    val hellower = system.actorOf(Props[HellowerActor], "hellower")
    hellower ! SayHelloTo("testkit")
    expectMsgType[HelloMessage].content should be("Hello, testkit!")
  }
}