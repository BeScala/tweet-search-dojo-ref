package service.akka

import play.api.Logger
import service.TweetProvider
import akka.actor.{Props, Actor}
import akka.routing.RoundRobinRouter
import collection.mutable.ListBuffer
import model.{TweetNews, SearchTerm}

class TwitterSearchActor extends Actor {

  val numOfTweets = 10
  val workerRouter = context.actorOf(Props[WebScrapperWorker]
                                     .withRouter(RoundRobinRouter(numOfTweets)),
                                     name = "twitterSearch")

  var sendMessages = 0;
  var receivedMessages = 0;
  var list: ListBuffer[TweetNews] = new ListBuffer[TweetNews]

  protected def receive: Receive = {

    case msg: SearchTweets => {
      Logger.info("[" + this + "] - Received work assigment for: [" + msg.term.text + "]")

      TweetProvider.fetchTweets(msg.term.text, numOfTweets).onRedeem {
        tweetList =>
            Logger.info("Found " + tweetList.size + " tweets for term " + msg.term.text)
          for (tweet <- tweetList) {
            sendMessages += 1
            // send to webscrappers
            workerRouter ! WebScrap(tweet)
          }
      }
    }

    case msg: FinishedWebScrapping => {
      Logger.info("Received " + msg.news.size + " summarie(s)")
      receivedMessages += 1

      // aggregate news
      list ++= msg.news

      if (sendMessages == receivedMessages) {
        sender ! FinishedSearch(List(list:_*))
        // stop this actor
        context.stop(self)
      }
    }
  }
}
