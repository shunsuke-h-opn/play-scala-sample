package controllers

import javax.inject.Inject
import play.api.Logging

import play.api.libs.json._
import play.api.mvc._

class HelloController @Inject()(cc: ControllerComponents) extends AbstractController(cc) with Logging {

  // override protected def controllerComponents: ControllerComponents = cc

  def hello(): Action[AnyContent] = Action(Ok("Hello World"))

  def helloJson(): Action[AnyContent] = Action {
    val json: JsValue =
      Json.obj("hello" -> "world", "language" -> "scala")

    Ok(json)
  }
}