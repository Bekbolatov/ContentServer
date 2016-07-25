package com.sparkydots.service.content

import play.api.libs.json.Json


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
