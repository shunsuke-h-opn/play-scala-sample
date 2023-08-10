package controllers

import javax.inject._
import models.{Todo, TodoForm}
import play.api.Logging
import play.api.mvc._
import play.api.mvc.Results._
import services.TodoService

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

@Singleton
class ApplicationController @Inject()(cc: ControllerComponents, todoService: TodoService) extends AbstractController(cc) with Logging {

  def index() = Action.async { implicit request: Request[AnyContent] =>
    todoService.listAllTodos map { todos =>
      Ok(views.html.index(TodoForm.form, todos))
    }
  }

  def addTodo() = Action.async { implicit request: Request[AnyContent] =>
    TodoForm.form.bindFromRequest.fold(
      // if any error in submitted data
      errorForm => {
        logger.warn(s"Form submission with error: ${errorForm.errors}")
        Future.successful(Ok(views.html.index(errorForm, Seq.empty[Todo])))
      },
      data => {
        val newTodo = Todo(0, data.status, data.title)
        todoService.addTodo(newTodo).map( _ => Redirect("/"))
      })
  }

  def deleteTodo(id: Int) = Action.async { implicit request: Request[AnyContent] =>
    todoService.deleteTodo(id) map { res =>
      Redirect("/")
    }
  }

}