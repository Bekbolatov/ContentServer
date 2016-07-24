package controllers

import javax.inject.{Inject, Singleton}

import akka.actor.ActorSystem
import com.sparkydots.utils.ServiceDiscovery
import play.api.libs.ws.WSClient

import scala.concurrent.ExecutionContext
import play.api.mvc.{Controller, Action}
import play.twirl.api.TxtFormat
import services.{LatexService, ContentGeneration}


@Singleton
class ContentController @Inject()(
                                   contentGen: ContentGeneration,
                                   latexService: LatexService
                                 ) (implicit exec: ExecutionContext) extends Controller {

  def works() = Action.async {

    val body = contentGen.getSetInTex()
    println(body)

    latexService.convertLatexFile(body).
      map { case (success, result) => result }
  }

}
