package com.sparkydots.service.content

import javax.inject.{Inject, Singleton}

import com.sparkydots.service.ServiceDiscovery
import play.api.http.HttpEntity
import play.api.libs.ws.{WSClient, StreamedResponse}
import play.api.mvc.Results._
import play.api.mvc.Result

import scala.concurrent.duration._
import scala.concurrent.{Future, ExecutionContext, Promise}

@Singleton
class ContentService @Inject()(ws: WSClient, serviceDiscovery: ServiceDiscovery) {

  def generatePracticeSet(spec: PracticeSetSpecification,
                          timeoutMillis: Long = 10000,
                          log: Option[(=> String) => Unit] = None)
                         (implicit exec: ExecutionContext): Future[Result] = {

    val serviceProduct = serviceDiscovery.call[Result]("contentserver", log = log) { serviceInstance =>

      val promise = Promise[Option[Result]]()
      val futureStreamedResponse: Future[StreamedResponse] =
        ws.
        url(serviceInstance.httpUrl("/generate")).
        withHeaders("Content-Type" -> "application/json").
        withRequestTimeout(timeoutMillis.millis).
        withMethod("POST").
        withBody(spec.jsonString).
        stream()

      val futureResult: Future[Option[Result]] = futureStreamedResponse.map {
        case StreamedResponse(response, body) =>
          if (response.status == 200) {
            val contentType = response.headers.get("Content-Type").flatMap(_.headOption).getOrElse("application/octet-stream")
            response.headers.get("Content-Length") match {
              case Some(Seq(length)) =>
                Some(Ok.sendEntity(HttpEntity.Streamed(body, Some(length.toLong), Some(contentType))))
              case _ =>
                Some(Ok.chunked(body).as(contentType))
            }
          } else {
            None
          }
      }

      futureResult.onSuccess { case result =>
        promise.success(result)
      }

      futureResult.onFailure { case t =>
        log.foreach(_("ContentService: Error getting from server"))
        log.foreach(_(t.getStackTrace.toString))
        promise.success(None)
      }

      promise.future
    }

    serviceProduct.map {
      case Some(r) => r
      case None => Ok("ContentService error")
    }

  }

}


