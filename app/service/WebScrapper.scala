package service

import model.{Tweet, TweetNews}
import org.jsoup.Jsoup


object WebScrapper {

  def scrapp(tweet: Tweet): List[TweetNews] = {
    for { url <- tweet.urls }
      yield  {
      // we just need a dumb unique id
      val id = url.hashCode + "-" + tweet.id
    TweetNews(id,
              "fake title",
              tweet.content,
              tweet.user, tweet.imgUrl, tweet.date, url, tweet.tags)
    }
  }
//    for { url <- tweet.urls }
//    yield {
//      val doc = Jsoup.connect(url).get
//      val content =
//        if (doc.getElementById("content") == null)
//          doc.getElementById("content").text
//        else
//          doc.body.text
//
//      // we just need a dumb unique id
//      val id = url.hashCode + "-" + tweet.id
//
//      TweetNews(id,
//                doc.title, content,
//                tweet.user, tweet.imgUrl,
//                tweet.date,
//                url, tweet.tags)
//    }
//  }
}
