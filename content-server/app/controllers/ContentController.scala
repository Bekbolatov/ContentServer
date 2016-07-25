package controllers

import javax.inject.{Inject, Singleton}

import scala.concurrent.ExecutionContext
import play.api.mvc.{Controller, Action}
import services.ContentGeneration
import com.sparkydots.service.latex.LatexService
import com.sparkydots.service.content._
import PracticeSetSpecification.implicits._

@Singleton
class ContentController @Inject()(
                                   contentGen: ContentGeneration,
                                   latexService: LatexService
                                 )(implicit exec: ExecutionContext) extends Controller {

  def log(msg: => String): Unit = println(msg)

  def testexample() = Action.async {

    val body = contentGen.getSetInTex()

    latexService.convertLatexFile(body, log = Some(log)).
      map { case (success, result) =>
        result.withHeaders("Content-Disposition" -> "attachment; filename=\"attachment.pdf\"")
      }
  }


  def generate() = Action.async { request =>

    val sentSpec = for {
      data <- request.body.asJson
      spec <- data.validate[PracticeSetSpecification].asOpt
    } yield spec

    println(sentSpec)

    val body = contentGen.getSetInTex()

    latexService.convertLatexFile(body, timeoutMillis = 5000).
      map { case (success, result) => result.withHeaders("Content-Disposition" -> "attachment; filename=\"attachment.pdf\"") }
  }

}
