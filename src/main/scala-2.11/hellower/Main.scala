package hellower

import akka.actor._
import com.typesafe.config.ConfigFactory

import scala.util.control.NonFatal

object Main {

  /**
   * @param args one argument: the persona name to say hello
   */
  def main(args: Array[String]): Unit = {
    if (args.length > 1) {
      println("Please provide just one name to greet or none")
    } else {
      val system = ActorSystem("Main")
      try {
        // Init system
        val hellower = system.actorOf(Props[HellowerActor], "hellower")
        val terminator = system.actorOf(Props(classOf[Terminator], hellower), "app-terminator")
        val config = ConfigFactory.load()
        // Perform greeting logic
        val defaultName = config.getString("hellower.default-name")
        val paramName = getNameFromArgs(args)
        val name = paramName.getOrElse(defaultName)
        hellower ! SayHelloTo(name)

        //Shutdown system
        system.stop(hellower)
      } catch {
        case NonFatal(e) ⇒ system.terminate(); throw e
      }
    }
  }

  def getNameFromArgs(args: Array[String]): Option[String] = {
    if (args.length == 1) Some(args(0))
    else None
  }

  class Terminator(app: ActorRef) extends Actor with ActorLogging {
    context watch app

    def receive = {
      case Terminated(_) ⇒
        log.info("application supervisor has terminated, shutting down")
        context.system.terminate()
    }
  }

}
