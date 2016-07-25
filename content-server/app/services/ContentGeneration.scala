package services

import javax.inject.Inject

import com.sparkydots.service.latex.LatexService
import play.twirl.api.TxtFormat

import scala.concurrent.ExecutionContext
import views.txt.DocumentTemplates._
import model._

class ContentGeneration @Inject()(latexService: LatexService) {

  implicit def appendableToText(appendable: TxtFormat.Appendable): String = appendable.body

  def sanitized(before: String): String = before.replace("\\\\", "\\")

  def getSetInTex()(implicit exec: ExecutionContext): String = sanitized {

    val practiceSet = Example.createPracticeSet.pretty
    practice_set_template(practiceSet)
  }

}
