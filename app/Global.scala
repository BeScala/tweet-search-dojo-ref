import akka.actor.{Props, Actor}
import model.SearchTerm
import play.api._
import libs.concurrent.Akka
import play.api.Play.current
import akka.util.duration._
import service.akka.{Start, Master}


object Global extends GlobalSettings {

  override def onStart(app: Application) {
    SearchTerm("playframework").save()
    SearchTerm("scala-lang").save()
    SearchTerm("typesafe").save()


    val twitterActorRef = Akka.system.actorOf(Props[Master], name = "twitterActor")

    Akka.system.scheduler.schedule(0 seconds, 1 minutes, twitterActorRef, Start)
  }
}
