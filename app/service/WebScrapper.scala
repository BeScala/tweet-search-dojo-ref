package service

import model.{Tweet, TweetNews}
import org.jsoup.Jsoup
import play.api.Logger


object WebScrapper {

  def scrapp(tweet: Tweet): List[TweetNews] = {
    val list =
      for { url <- tweet.urls }
      yield {
        try {
          val doc = Jsoup.connect(url).get

          val content =
            if (doc.getElementById("content") == null)
              doc.getElementById("content").text
            else
              doc.body.text

          // we just need a dumb unique id
          val id = url.hashCode + "-" + tweet.id

          TweetNews(id,
                    doc.title, content,
                    tweet.user, tweet.imgUrl,
                    tweet.date,
                    url, tweet.tags)
        } catch {
          case _ => Logger.warn("Couldn't scrap url " + url)
        }
      }

      list.collect { case t: TweetNews => t }
  }
}
