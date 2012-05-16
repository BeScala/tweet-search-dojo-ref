package controllers

import play.api.mvc._
import collection.mutable.ListBuffer
import model.SearchTerm

object TweetController extends Controller {

  /**
   * List all availables search terms
   */
  def list = Action {
    Ok(views.html.list(SearchTerm.all))
  }

  /**
   * Saves a new SearchTerm. Handles form submission.
   */
  def save = TODO

  /**
   * Displays search results for passed SearchTerm
   */
  def view(id: Long) = TODO

  /**
   * Prepares an empty form for new SearchTerm
   */
  def newForm = TODO

  /**
   * Fill form with existing SearchTerm
   */
  def editForm(id: Long) = TODO

}