package tgpr.schoolApp.controller;

import tgpr.schoolApp.model.*;
import tgpr.schoolApp.view.TeacherSelectQuizView;
import tgpr.schoolApp.view.View;

public class TeacherSelectQuizController extends Controller {

    private final TeacherSelectQuizView view = new TeacherSelectQuizView();
    private final Quiz quiz;

    public TeacherSelectQuizController(Quiz quiz) {
        this.quiz = quiz;
    }

    @Override
    public void run() {
        try {
            View.Action res;

            do {
                var questions = Question.getByQuiz(quiz.getId());
                var course = Course.getById(quiz.getCourseId());
                var answers = Answer.getByQuiz(quiz.getId());
                var testParticipantList = Test.getByQuiz(quiz.getId());
                view.displayHeader();
                view.displayHeaderSelectQuiz(course);
                view.displayQuizInfo(quiz);
                view.displayQuestions(questions);

                res = view.askForAction(questions.size(), testParticipantList.size());

                switch (res.getAction()) {
                    case 'S': // select question
                        new TeacherSelectQuestionController(questions.get(res.getNumber() - 1),course,quiz).run();
                        break;
                    case 'C': // create question
                        new TeacherCreateQuestionController(quiz,course).run();
                        break;
                    case 'D': // delete question
                        var question = questions.get(res.getNumber() - 1);
                        if (view.askConfirmationDelete(question.getTitle()))
                            question.delete();
                        break;
                    case 'U': // update question
                        question = questions.get(res.getNumber() - 1);
                        var options = Option.getByIdQuestion(question.getId());
                        if (!question.hasAnswer(answers, options))
                            new TeacherUpdateQuestionController(question,course,quiz).run();
                        else
                            view.displayUpdateImpossible(question.getTitle());
                        break;
                    case 'V': // view student tests
                        new TeacherTestParticipantListController(quiz).run();
                        break;
                }
            } while (res.getAction() != 'L');
        } catch (View.ActionInterruptedException e) {
        }
    }
}
