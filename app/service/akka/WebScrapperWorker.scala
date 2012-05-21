package service.akka

import akka.actor.Actor
import service.WebScrapper
import play.api.Logger
import model.TweetNews


class WebScrapperWorker extends Actor {

  protected def receive: Receive = {
    case msg: WebScrap => {
      Logger.info("[" + this + "] - received assignement for tweet id:" + msg.tweet.id)
      val tweetNews = WebScrapper.scrapp(msg.tweet)
      tweetNews.foreach { _.save  }
      sender ! FinishedWebScrapping(tweetNews)
    }
  }
}
