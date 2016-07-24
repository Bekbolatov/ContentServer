package controllers

import play.api.mvc._

class Main extends Controller {

  def health = Action { Ok("okay") }

}
