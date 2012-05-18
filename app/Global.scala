import model.SearchTerm
import play.api._

object Global extends GlobalSettings {

  override def onStart(app: Application) {
    SearchTerm("playframework").save()
    SearchTerm("scala-lang").save()
    SearchTerm("typesafe").save()
  }
}
