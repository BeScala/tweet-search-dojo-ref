package service

import play.api.libs.concurrent.Promise
import play.api.libs.ws.{Response, WS}
import util.TweetParser
import model.{Tweet, SearchTerm}
import play.api.Logger

object TweetProvider {

  val baseUrl = "http://search.twitter.com/search.json?include_entities=true&result_type=recent&q="

  def fetchTweets(searchTerm: String, max: Int = 100) : Promise[List[Tweet]] = {
    val searchUrl = baseUrl + searchTerm + "%20filter:links&rpp=" + max
    Logger.info("Fetching tweets for: " + searchUrl)
    WS.url(searchUrl).get().map { r => TweetParser.parse(r.json) }
  }
}
