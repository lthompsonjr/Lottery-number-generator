package com.louis.lottogenerator

import akka.http.scaladsl.model._
import io.swagger.jaxrs2.Reader
import io.swagger.oas.models.OpenAPI
import io.swagger.util.Json

import scala.util.control.NonFatal
import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.StatusCodes._
import akka.http.scaladsl.server.Directives._
import akka.stream.ActorMaterializer
import org.slf4j.LoggerFactory

import scala.io.StdIn

/**
  * Created by lth09 on 02/12/2017.
  */
object LottoDocs extends App {

  val logger = LoggerFactory.getLogger("lottoDocs")
  implicit val system = ActorSystem("swagger-docs")
  implicit val materializer = ActorMaterializer()
  // needed for the future flatMap/onComplete in the end
  implicit val executionContext = system.dispatcher


  def reader = new Reader(new OpenAPI())

  def generateSwaggerJson(clazz: Class[_], host: String): String = {
  try {
  val swagger: OpenAPI = reader.read(clazz)
  //.servers(List(new Server().url(host)).asJava)
  Json.pretty().writeValueAsString(swagger)
} catch {
  case NonFatal(t) =>
  throw t
}
}

  val swaggerRoute =
  pathPrefix("swagger") {
  pathEndOrSingleSlash {
  getFromResource("swagger-ui/index.html")
} ~ {
  getFromResourceDirectory("swagger-ui")
} ~ pathPrefix("webjars") {
  getFromResourceDirectory("META-INF/resources/webjars")
}
}

  //serves json to be used in index.html
  val docsRoute =
  path("api-docs"/"swagger.json") {
  get {
  //complete(HttpEntity(ContentTypes.`text/plain(UTF-8)`, "hello"))
  complete(HttpEntity(
  MediaTypes.`application/json`, generateSwaggerJson(classOf[RouterDocs], "http://localhost:8080")
  ))
}
}
  val generateRoute = get {
    path("generate" / "lotto") {
      logger.info("generate lotto request made")
      complete(OK)
    }
  }

  val statusRoute = get {
  path("private" / "status") {
  complete(OK)
}


}


  //val test = classOf[RouterDocs]
  val routes = swaggerRoute ~ statusRoute ~ docsRoute ~ generateRoute

  val bindingFuture = Http().bindAndHandle(routes, "localhost", 8080)
  logger.info(s"Server online at http://localhost:8080/\nPress RETURN to stop...")
  StdIn.readLine() // let it run until user presses return
  bindingFuture

  .flatMap(_.unbind()) // trigger unbinding from the port
  .onComplete(_ => system.terminate()) // and shutdown when done

}
