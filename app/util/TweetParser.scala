package util

import model.Tweet
import play.api.libs.json
import play.api.libs.json.{JsValue, Json}

object TweetParser {
  def parse(jsonPayload: String) : List[Tweet] = parse(Json.parse(jsonPayload))

  def parse(json: JsValue) : List[Tweet] = {
    val list = (json \ "results").as[List[JsValue]]
    list.map { e =>
      val id = (e \ "id").as[Int]
      val user = (e \ "from_user").as[String]
      val content = (e \ "text").as[String]
      val imgUrl = (e \ "profile_image_url").as[String]
      val date = (e \ "created_at").as[String]
      val entities = (e \ "entities")
      Tweet(id,content,user, imgUrl, date, collectMediaUrls(entities))
    }
  }

  private def collectMediaUrls(json: JsValue) : List[String] = {
    val urls = (json \ "urls").as[List[JsValue]]
    val listOfOptionalUrls = urls.map { url => (url \ "expanded_url").asOpt[String] }
    listOfOptionalUrls.collect { case Some(url) => url }
  }
}
