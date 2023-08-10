package services

import com.google.inject.Inject
import models.{Todo, Todos}

import scala.concurrent.Future

class TodoService @Inject() (todos: Todos) {

  def addTodo(todo: Todo): Future[String] = {
    todos.add(todo)
  }

  def deleteTodo(id: Int): Future[Int] = {
    todos.delete(id)
  }

  def getTodo(id: Int): Future[Option[Todo]] = {
    todos.get(id)
  }

  def listAllTodos: Future[Seq[Todo]] = {
    todos.listAll
  }
}