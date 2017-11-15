//import org.apache.commons.mail.{DefaultAuthenticator, SimpleEmail}
//
//import scala.util.{Failure, Success, Try}
//import org.json4s._
//import org.json4s.jackson.JsonMethods._
//
///**
//  * Created by lth09 on 29/10/2017.
//  */
//object Lotto {
//
//  private def generate = {
//    NationalLotteryGenerator.generate(6, 59)
//  }
//
//  def LottoMessage: String = {
//
//    var lottoNumbers = ""
//    val result = generate
//    result match {
//      case Left(msg) => println(msg)
//      case Right(numbers) =>  lottoNumbers = numbers.mkString(", ")
//    }
//
//    s""" Lotto
//       | Numbers: $lottoNumbers
//        """.stripMargin
//  }
//}
//
//case class EuroMillions(standardNumbers:Either[String,List[Int]] , luckyStars: Either[String,List[Int]])
//
//object EuroMillions {
//
//  private val standardNumbers = NationalLotteryGenerator.generate(5,50)
//  private val luckyStars = NationalLotteryGenerator.generate(2,12)
//
//  def generate: EuroMillions = {
//    EuroMillions(standardNumbers,luckyStars)
//  }
//
//  def euroMillionsMessage: String = {
//
//    val result = generate
//    var standardNumbers = ""
//    result.standardNumbers match {
//      case Left(msg) => println(msg)
//      case Right(numbers) => standardNumbers = numbers.mkString(", ")
//    }
//
//    var luckyStars = ""
//    result.luckyStars match {
//      case Left(msg) => println(msg)
//      case Right(numbers) => luckyStars = numbers.mkString(", ")
//    }
//    s""" EuroMillions
//       | Standard Numbers: $standardNumbers
//       | LuckyStars: $luckyStars
//        """.stripMargin
//  }
//}
//
//
//
//
//class Email {
//  def sendEmail(content: String): Unit = {
//
//    val email = new SimpleEmail()
//    email.setHostName("smtp.gmail.com")
//    email.setSmtpPort(465)
//    email.setAuthenticator(new DefaultAuthenticator("lbthompson.jr@gmail.com", "grasshopper001"))
//    email.setSSLOnConnect(true)
//    email.setFrom("lbthompson.jr@gmail.com")
//    email.setSubject("Rose: National Lottery Number Generator")
//    email.setMsg(content)
//    email.addTo("lthompsonjr@hotmail.com")
//    email.addTo("cs.rose.jones@gmail.com")
//    email.addTo("caroline_s_r_jones@hotmail.co.uk")
//    email.send()
//  }
//}

import java.io.File
import java.nio.file.Path

import akka.actor.{Actor, ActorRef, ActorSystem, PoisonPill, Props}
import akka.http.scaladsl.model.{ContentTypes, HttpEntity, MediaTypes}
import akka.http.scaladsl.server.ContentNegotiator.Alternative.ContentType
import io.swagger.jaxrs2.Reader
import io.swagger.oas.models.OpenAPI
import io.swagger.oas.models.servers.Server

import scala.util.control.NonFatal
import io.swagger.util.Json

import scala.collection.JavaConverters._
//import language.postfixOps
import scala.concurrent.duration._



//case object Rant
//case object Rave
//case object Ping
//case object Pong



//class Raver extends Actor {
//  def receive = {
//    case Rant =>
//      println(s"${self.path} I just received rant message")
//      //sender ! Rave
//  }
//}
//
//class Ranter(message:String) extends Actor {
//  def receive = {
//    case Rave =>
//      println(s"${self.path} I just received a rave $message")
//      sender ! Rant
//  }
//}



//class Pinger extends Actor {
//  var countDown = 100
//
//  def receive = {
//    case Pong =>
//      println(s"${self.path} received pong, count down $countDown")
//
//      if (countDown > 0) {
//        countDown -= 1
//        sender() ! Ping
//      } else {
//        sender() ! PoisonPill
//        self ! PoisonPill
//      }
//  }
//}

//class Ponger(pinger: ActorRef) extends Actor {
//  def receive = {
//    case Ping =>
//      println(s"${self.path} received ping")
//      pinger ! Pong
//  }
//}



//object myApp extends App {
//
//  //val emailClient = new Email()
//  //emailClient.sendEmail(EuroMillions.euroMillionsMessage +"\n" + Lotto.LottoMessage)
////  val json =
////    """{
////      |"name":"Louis"
////      |}""".stripMargin
////
////  val test: Try[String] = for {
////    y <- Try(parse(json))
////    x <- compact(render(y))
////  } yield x
////
////  test match {
////    case Success(message) => println(message)
////    case Failure(ex) => println(ex)
////  }
//  val system = ActorSystem("rantrave")
//
//  val ranter = system.actorOf(Props(classOf[Ranter], "message"), "ranter")
//
//  val raver = system.actorOf(Props[Raver],"raver")
//
//  ranter.tell(Rave,raver)
//
//
//  //import system.dispatcher
//  //system.scheduler.scheduleOnce(500 millis) {
//  //}
//}




import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.model.StatusCodes.OK
import akka.stream.ActorMaterializer
import scala.io.StdIn

object Lotto extends App {

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

  val statusRoute = get {
    path("private" / "status") {
      complete(OK)
    }
  }


  //val test = classOf[RouterDocs]
  val routes = swaggerRoute ~ statusRoute ~ docsRoute

    val bindingFuture = Http().bindAndHandle(routes, "localhost", 8080)
    println(s"Server online at http://localhost:8080/\nPress RETURN to stop...")
    StdIn.readLine() // let it run until user presses return
    bindingFuture

      .flatMap(_.unbind()) // trigger unbinding from the port
      .onComplete(_ => system.terminate()) // and shutdown when done
}