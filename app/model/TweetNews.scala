package model

case class TweetNews (id: String,
                      title: String,
                      content:String,
                      user:String,
                      imgUrl: String,
                      date: String,
                      url: String,
                      tags : List[String])
