package model

import collection.mutable.ListBuffer

case class TweetNews(id: String,
                     title: String,
                     content: String,
                     user: String,
                     imgUrl: String,
                     date: String,
                     url: String,
                     tags: List[String])

object TweetNews {

  val news = new ListBuffer[TweetNews]

  def save(entity: TweetNews): TweetNews = {
    delete(entity.id)
    news += entity
    entity
  }


  def all: Seq[TweetNews] = List(news: _*)

  def findByIdOpt(id: String): Option[TweetNews] = news.find(e => e.id == id)

  def findById(id: String): TweetNews = news.find(e => e.id == id).get

  def delete(id: String) = {
    findByIdOpt(id).foreach(s => news -= s)
  }

  implicit def toActiveRecord(tweetNews: TweetNews) = new {
    def save() = TweetNews.save(tweetNews)
  }
}
