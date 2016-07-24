package com.sparkydots.contentservice

import javax.inject.{Inject, Singleton}

import com.sparkydots.utils.ServiceDiscovery
import play.api.http.HttpEntity
import play.api.libs.json.Json
import play.api.libs.ws.WSClient
import play.api.mvc.Results._
import play.api.mvc.{Result, ResponseHeader}

import scala.concurrent.{Future, ExecutionContext}

object PracticeSetSpecification {
  object implicits {
    implicit val practiceQuestionFormat = Json.format[PracticeQuestion]
    implicit val practiceQuestionCountFormat = Json.format[PracticeQuestionCount]
    implicit val practiceSetSpecificationFormat = Json.format[PracticeSetSpecification]
  }
}

case class PracticeQuestion(family: String, version: String, index: Int)
case class PracticeQuestionCount(question: PracticeQuestion, number: Int)

case class PracticeSetSpecification(
                                     title: String,
                                     subtitle: String,
                                     description: String,
                                     questions: Seq[PracticeQuestionCount],
                                     questionSet: Option[String]
                                   ) {

  import PracticeSetSpecification.implicits._
  def jsonString = Json.toJson(this).toString

}

@Singleton
class QuestionsService @Inject()(ws: WSClient, serviceDiscovery: ServiceDiscovery) {

  def generateDocument(spec: PracticeSetSpecification)(implicit exec: ExecutionContext): Future[Result] = {

    val serverProduct = serviceDiscovery.call[Future[Result]]("contentserver") { serviceInstance =>

      val host = serviceInstance.host
      val port = serviceInstance.port

      val url = s"http://$host:$port/generate"

      val futureResponse = ws.url(url).
        withHeaders("Content-Type" -> "application/json").
        post(spec.jsonString)

      val futureResult: Future[Result] = futureResponse.map { response =>
          if (response.header("Content-Type").contains("application/pdf")) {
            Result(
              header = ResponseHeader(200),
              body = HttpEntity.Strict(response.bodyAsBytes, Some("application/pdf"))
            ).withHeaders("Content-Disposition" -> "inline; filename=\"result.pdf\"")
          } else {
            Result(
              header = ResponseHeader(200),
              body = HttpEntity.Strict(response.bodyAsBytes, Some("plain/text"))
            )
          }
        }
      Some(futureResult)
    }

    serverProduct.getOrElse(Future.successful(Ok("Server error")))

  }

}


