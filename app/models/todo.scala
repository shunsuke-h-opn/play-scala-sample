package models

import com.google.inject.Inject
import play.api.data.Form
import play.api.data.Forms.mapping
import play.api.data.Forms._
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import slick.jdbc.JdbcProfile

import scala.concurrent.{ExecutionContext, Future}

case class Todo(id: Int, status: String, title: String)

case class TodoFormData(status: String, title: String)

object TodoForm {

  val form = Form(
    mapping(
      "status" -> nonEmptyText,
      "title" -> nonEmptyText,
    )(TodoFormData.apply)(TodoFormData.unapply)
  )
}

import slick.jdbc.MySQLProfile.api._

class TodoTableDef(tag: Tag) extends Table[Todo](tag, "todo") {

  def id = column[Int]("id", O.PrimaryKey,O.AutoInc)
  def status = column[String]("status")
  def title = column[String]("title")

  override def * =
    (id, status, title) <>(Todo.tupled, Todo.unapply)
}

class Todos @Inject() (protected val dbConfigProvider: DatabaseConfigProvider)(implicit executionContext: ExecutionContext) extends HasDatabaseConfigProvider[JdbcProfile] {

  val todos = TableQuery[TodoTableDef]

  def add(todo: Todo): Future[String] = {
    dbConfig.db.run(todos += todo).map(res => "Todo successfully added").recover {
      case ex: Exception => ex.getCause.getMessage
    }
  }

  def delete(id: Int): Future[Int] = {
    dbConfig.db.run(todos.filter(_.id === id).delete)
  }

  def get(id: Int): Future[Option[Todo]] = {
    dbConfig.db.run(todos.filter(_.id === id).result.headOption)
  }

  def listAll: Future[Seq[Todo]] = {
   dbConfig.db.run(todos.result)
  }

}