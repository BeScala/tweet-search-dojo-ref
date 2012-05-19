package model

case class Tweet(id: Long,
                 content:String,
                 user:String,
                 imgUrl: String,
                 date: String,
                 urls : List[String],
                 tags : List[String])
