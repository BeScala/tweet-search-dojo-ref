package controllers

import play.api.mvc._

import service.TweetProvider
import play.api.libs.concurrent.Promise
import model.{Tweet, SearchTerm}
import play.api.data.Form
import play.api.data.Forms._
import play.api.Logger


object TweetController extends Controller {

  /**
   * List all availables search terms
   */
  def list = Action {
    Ok(views.html.list(SearchTerm.all, emptyForm))
  }

  /**
   * Saves a new SearchTerm. Handles form submission.
   */
  def save = Action { implicit request =>
    searchTermForm.bindFromRequest.fold(
      errors => { BadRequest(views.html.list(SearchTerm.all, errors)) },
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

  def delete(id: Long) = Action {
    SearchTerm.delete(id)
    Redirect(routes.TweetController.list())
  }


  /**
   * Prepares an empty form for new SearchTerm
   */
  def newForm = toFormPage(emptyForm)

  /**
   * Fill form with existing SearchTerm
   */
  def editForm(id: Long) = toFormPage(searchTermForm.fill(SearchTerm.findById(id)))
  private def emptyForm = searchTermForm.fill(SearchTerm(""))
  private def toFormPage(form: Form[SearchTerm]) = Action(Ok(views.html.list(SearchTerm.all, form)))

  def tweets(searchTerm: String, max: Int) = Action {
    Async {
      val promiseOfTweets: Promise[List[Tweet]] = TweetProvider.fetchTweets(searchTerm, max)
      promiseOfTweets.map {
        tweets =>
          tweets.foreach(t => Logger.info(t.toString))
          Ok(views.html.tweets(tweets))
      }
    }
  }

}