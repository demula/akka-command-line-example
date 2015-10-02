package hellower

import akka.actor.{Actor, ActorLogging}

case class SayHelloTo(name: String)
case class HelloMessage(content: String)

class HellowerActor extends Actor with ActorLogging {
  var lastName = ""
  val messageContentTemplate = "Hello, %s!"

  def receive = {
    case command: SayHelloTo => {
      log.debug("Executing command SayHelloTo(name:{})", command.name)
      lastName = command.name
      val content = messageContentTemplate.format(command.name)
      println(content)
      sender ! HelloMessage(content)
    }
    case unexpectedMessage: Any => {
      log.debug("Received unexpected message: {}", unexpectedMessage)
      throw new Exception("Can't handle %s".format(unexpectedMessage))
    }
  }
}
