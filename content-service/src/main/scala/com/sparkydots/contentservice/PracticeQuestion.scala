package com.sparkydots.contentservice

import play.api.libs.json.Json

object PracticeSetSpecification {
  object implicits {
    implicit val practiceQuestionFormat = Json.format[PracticeQuestion]
    implicit val practiceSetSpecificationFormat = Json.format[PracticeSetSpecification]
  }
}

case class PracticeQuestion(family: String, version: String, number: Int)

case class PracticeSetSpecification(
                                     title: String,
                                     subtitle: String,
                                     description: String,
                                     questions: Seq[PracticeQuestion]
                                   ) {


  import PracticeSetSpecification.implicits._
  def jsonString = Json.toJson(this).toString

}

