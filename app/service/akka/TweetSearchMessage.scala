package service.akka

import model.{Tweet, SearchTerm, TweetNews}


sealed trait TweetSearchMessage

/**
 * Sent to Master Actor to start collecting web page summaries
 */
case object Start extends TweetSearchMessage

/**
 * Sent from Master to Search Worker that must fetch results from TwitterSearch API
 */
case class SearchTweets(term: SearchTerm) extends  TweetSearchMessage

/**
 * Sent from Search Worker to WebScrapper Worker for web scrapping of links inside tweets
 */
case class WebScrap(tweet: Tweet) extends  TweetSearchMessage


/**
 * Sent from WebScrapper Worker to Search Worker with web scrapping result
 */
case class FinishedWebScrapping(news: List[TweetNews]) extends  TweetSearchMessage

/**
 * Sent from Search Worker to Master
 */
case class FinishedSearch(news: List[TweetNews]) extends  TweetSearchMessage
