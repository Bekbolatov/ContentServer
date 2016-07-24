package model

import play.twirl.api.TxtFormat

case class AnswerChoice(id: Int, label: String, value: String)
case class CorrectAnswer(choice: Option[Int], exactValue: Option[String])
case class Question(id: String, statement: String, answers: Seq[AnswerChoice], correctAnswer: CorrectAnswer)

case class Section(title: String, description: String, questions: Seq[Question])
case class PracticeSet(title: String, subtitle: String, description: String, sections: Seq[Section]) {

  def pretty: PracticeSet = {
    val alphas = "ABCDEFGHIJ"
    var qid = 0
    this.copy(
      sections = sections.map { section =>
        section.copy(
          questions = section.questions.map { question =>
            qid += 1
            var aid = -1
            question.copy(
              id = s"$qid.",
              answers = question.answers.map { answerChoice =>
                aid += 1
                answerChoice.copy(
                  label = alphas(aid).toString
                )
              }
            )
          }
        )
      }
    )
  }

  def answers = {
    sections.map { section =>
      (section,
        section.questions.map { q =>
          val correct = if(q.correctAnswer.choice.nonEmpty) {
            val choice = q.answers(q.correctAnswer.choice.get)
            s"choice.label"
          } else {
            q.correctAnswer.exactValue.get
          }
          q.id.toString + " " +  correct
        })
    }
  }
}


object Example {
  import views.txt.QuestionTemplates
  implicit def appendableToText(appendable: TxtFormat.Appendable): String = appendable.body

  def createPracticeSet: PracticeSet = {
    PracticeSet(
      "Practice Set",
      "Renat Bekbolatov",
      "This is an example practice set an example practice set an example practice set an example practice set an example practice set an example practice set an example practice set.",
      Seq(

        // SECTIONS BEGIN
        Section(
          "Section A",
          "Math questions",
          Seq(
            Question(
              "11. ",
              QuestionTemplates.q1(),
              Seq(AnswerChoice(0, "A", "14"), AnswerChoice(1, "B", "49"), AnswerChoice(1, "C", "52"), AnswerChoice(1, "D", "14"), AnswerChoice(1, "E", "7")),
              CorrectAnswer(Some(1), None)
            ),
            Question(
              "2. ",
              QuestionTemplates.q1(),
              Seq(AnswerChoice(0, "A", "14"), AnswerChoice(1, "3", "49"), AnswerChoice(1, "C", "52"), AnswerChoice(1, "D", "14"), AnswerChoice(1, "E", "7")),
              CorrectAnswer(Some(1), None)
            ),
            Question(
              "2. ",
              QuestionTemplates.q1(),
              Seq(AnswerChoice(0, "A", "14"), AnswerChoice(1, "B", "49"), AnswerChoice(1, "C", "52"), AnswerChoice(1, "D", "14"), AnswerChoice(1, "E", "7")),
              CorrectAnswer(Some(1), None)
            ),
            Question(
              "2. ",
              QuestionTemplates.q1(),
              Seq(AnswerChoice(0, "A", "14"), AnswerChoice(1, "B", "49"), AnswerChoice(1, "C", "52"), AnswerChoice(1, "D", "14"), AnswerChoice(1, "E", "7")),
              CorrectAnswer(Some(1), None)
            ),
            Question(
              "2. ",
              QuestionTemplates.q1(),
              Seq(AnswerChoice(0, "A", "14"), AnswerChoice(1, "B", "49"), AnswerChoice(1, "C", "52"), AnswerChoice(1, "D", "14"), AnswerChoice(1, "E", "7")),
              CorrectAnswer(Some(1), None)
            ),
            Question(
              "3. ",
              QuestionTemplates.q1(),
              Seq(AnswerChoice(0, "A", "14"), AnswerChoice(1, "B", "49"), AnswerChoice(1, "C", "52"), AnswerChoice(1, "D", "14"), AnswerChoice(1, "E", "7")),
              CorrectAnswer(Some(1), None)
            )
          )
        ),

        Section(
          "Section B",
          "Verbal questions",
          Seq(
            Question(
              "4. ",
              QuestionTemplates.q1(),
              Seq(AnswerChoice(0, "A", "14"), AnswerChoice(1, "B", "49"), AnswerChoice(1, "C", "52"), AnswerChoice(1, "D", "14"), AnswerChoice(1, "E", "7")),
              CorrectAnswer(Some(1), None)
            ),
            Question(
              "5. ",
              QuestionTemplates.q1(),
              Seq(AnswerChoice(0, "A", "14"), AnswerChoice(1, "B", "49"), AnswerChoice(1, "C", "52"), AnswerChoice(1, "D", "14"), AnswerChoice(1, "E", "7")),
              CorrectAnswer(Some(1), None)
            ),
            Question(
              "5. ",
              QuestionTemplates.q1(),
              Seq(AnswerChoice(0, "A", "14"), AnswerChoice(1, "B", "49"), AnswerChoice(1, "C", "52"), AnswerChoice(1, "D", "14"), AnswerChoice(1, "E", "7")),
              CorrectAnswer(Some(1), None)
            ),
            Question(
              "6. ",
              QuestionTemplates.q1(),
              Seq(AnswerChoice(0, "A", "14"), AnswerChoice(1, "B", "49"), AnswerChoice(1, "C", "52"), AnswerChoice(1, "D", "14"), AnswerChoice(1, "E", "7")),
              CorrectAnswer(Some(1), None)
            )
          )
        ),

        Section(
          "Section C",
          "Analytical questions",
          Seq(
            Question(
              "7. ",
              QuestionTemplates.q1(),
              Seq(AnswerChoice(0, "A", "14"), AnswerChoice(1, "B", "49"), AnswerChoice(1, "C", "52"), AnswerChoice(1, "D", "14"), AnswerChoice(1, "E", "7")),
              CorrectAnswer(Some(1), None)
            ),
            Question(
              "1. ",
              QuestionTemplates.q1(),
              Seq(AnswerChoice(0, "A", "14"), AnswerChoice(1, "B", "49"), AnswerChoice(1, "C", "52"), AnswerChoice(1, "D", "14"), AnswerChoice(1, "E", "7")),
              CorrectAnswer(Some(1), None)
            ),
            Question(
              "1. ",
              QuestionTemplates.q1(),
              Seq(AnswerChoice(0, "7", "14"), AnswerChoice(1, "4", "49"), AnswerChoice(1, "F", "52"), AnswerChoice(1, "D", "14"), AnswerChoice(1, "E", "7")),
              CorrectAnswer(Some(1), None)
            )
          )
        ),

        Section(
          "Section D",
          "More questions",
          Seq(
            Question(
              "7. ",
              QuestionTemplates.q1(),
              Seq(AnswerChoice(0, "A", "14"), AnswerChoice(1, "B", "49"), AnswerChoice(1, "C", "52"), AnswerChoice(1, "D", "14"), AnswerChoice(1, "E", "7")),
              CorrectAnswer(Some(1), None)
            ),
            Question(
              "1. ",
              QuestionTemplates.q1(),
              Seq(AnswerChoice(0, "A", "14"), AnswerChoice(1, "B", "49"), AnswerChoice(1, "C", "52"), AnswerChoice(1, "D", "14"), AnswerChoice(1, "E", "7")),
              CorrectAnswer(Some(1), None)
            ),
            Question(
              "1. ",
              QuestionTemplates.q1(),
              Seq(AnswerChoice(0, "7", "14"), AnswerChoice(1, "4", "49"), AnswerChoice(1, "F", "52"), AnswerChoice(1, "D", "14"), AnswerChoice(1, "E", "7")),
              CorrectAnswer(Some(1), None)
            )
          )
          )
        // SECTIONS END
      )
    )
  }
}