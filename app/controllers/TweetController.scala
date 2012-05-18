package controllers

import play.api.mvc._

import service.TweetProvider
import play.api.libs.concurrent.Promise
import model.{Tweet, SearchTerm}
import play.api.data.Form
import play.api.data.Forms._


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
  def save = Action { implicit request =>
    searchTermForm.bindFromRequest.fold(
      errors => { BadRequest(views.html.edit(errors)) },
      term => {
        term.save()
        Redirect(routes.TweetController.list())
      }
    )
  }

  /**
   * Displays search results for passed SearchTerm
   */
  def view(id: Long) = TODO

  private val searchTermForm = Form {
    mapping(
      "id" -> longNumber,
      "text" -> nonEmptyText)(SearchTerm.apply)(SearchTerm.unapply)
  }

  /**
   * Prepares an empty form for new SearchTerm
   */
  def newForm = toFormPage(searchTermForm.fill(SearchTerm("")))

  /**
   * Fill form with existing SearchTerm
   */
  def editForm(id: Long) = toFormPage(searchTermForm.fill(SearchTerm.findById(id)))

  private def toFormPage(form: Form[SearchTerm]) = Action(Ok(views.html.edit(form)))

  def tweets(searchTerm: String, max: Int) = Action {
    Async {
      val promiseOfTweets: Promise[List[Tweet]] = TweetProvider.fetchTweets(searchTerm, max)
      promiseOfTweets.map {
        tweets => Ok(views.html.tweets(tweets))
      }
    }
  }

}