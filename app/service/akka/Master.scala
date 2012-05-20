package service.akka

import _root_.akka.actor.{Actor, Props}
import _root_.akka.routing.RoundRobinRouter

import model.SearchTerm
import play.api.Logger

class Master extends Actor {


  val workerRouter = context.actorOf(Props[TwitterSearchActor].withRouter(RoundRobinRouter(10)), name = "twitterSearch")

  protected def receive: Receive = {
    case Start => SearchTerm.all.foreach {
      searchTerm =>
        Logger.info("[" + this + "] - Sending: " + searchTerm)
        workerRouter ! SearchTweets(searchTerm)
    }
  }
}
