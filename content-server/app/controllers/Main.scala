package controllers

import javax.inject.Inject

import play.api.mvc._

class Main @Inject() extends Controller {

  def index = Action {
    Ok(views.html.index("Your new application is ready."))
  }

  def health = Action { Ok("okay") }

}
