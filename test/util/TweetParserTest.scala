package util

import scala.io.Source
import org.scalatest.FunSuite
import org.junit.Test
import org.junit.Assert._

class TweetParserTest extends FunSuite{
  test("testing parsing of Twitter Search result"){
    val payload = Source.fromFile("test/util/tweet.json").mkString

    val tweets = TweetParser.parse(payload)
    assert(tweets.size === 1)
    val tweet = tweets(0)
    assert(tweet.content.startsWith("Playframework"))
  }
}
