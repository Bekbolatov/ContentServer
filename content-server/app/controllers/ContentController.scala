package controllers

import javax.inject.{Inject, Singleton}

import scala.concurrent.ExecutionContext
import play.api.mvc.{Controller, Action}
import services.{ContentGeneration, LatexService}
import com.sparkydots.contentservice._
import PracticeSetSpecification.implicits._

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


  def generate() = Action.async { request =>

    println(request)
    println(request.headers)
    println("BODY:")
    println(request.body)

    println("BODY.asJson:")
    println(request.body.asJson)

    println("BODY.asJson.vali:")
    println(request.body.asJson.get.validate[PracticeSetSpecification])

    val sentSpec = for {
      data <- request.body.asJson
      spec <- data.validate[PracticeSetSpecification].asOpt
    } yield spec

    println(sentSpec)

    val body = contentGen.getSetInTex()
//    println(body)

    latexService.convertLatexFile(body).
      map { case (success, result) => result }
  }

}
