package util

import model.Tweet
import play.api.libs.json
import play.api.libs.json.{JsValue, Json}

object TweetParser {
  def parse(jsonPayload: String) : List[Tweet] = {
    val json = Json.parse(jsonPayload)
    val list = (json \ "results").as[List[JsValue]]
    list.map { e => Tweet((e \ "text").as[String]) }
  }
}
