package tgpr.schoolApp.controller;

import tgpr.schoolApp.model.*;
import tgpr.schoolApp.view.TeacherConsultStudentTestView;
import tgpr.schoolApp.view.View;

public class TeacherConsultStudentTestController extends Controller {
    TeacherConsultStudentTestView view = new TeacherConsultStudentTestView();
    Quiz quiz;
    Test test;

    public TeacherConsultStudentTestController(Quiz quiz, Test test) {
        this.quiz = quiz;
        this.test = test;
    }

    @Override
    public void run() {
        try {
            View.Action res;
            do {
                var questionList = Question.getByQuiz(quiz.getId());
                var answers = Answer.getByQuizByStudent(quiz.getId(), test.getStudentPseudo());
                var course = Course.getById(quiz.getCourseId());
                int grade = questionList.size();

                view.displayHeader();
                view.displayStudent(test.getStudentPseudo());
                view.displayCourseInfoTeacher(course);
                view.displayQuizInfo(quiz);

                for (int i = 0; i < questionList.size(); i++) {
                    boolean isCorrect;
                    view.displayQuestion(questionList.get(i), i + 1);
                    var options = Option.getByIdQuestion(questionList.get(i).getId());
                    isCorrect = view.displayStudentAnswer(answers, options, true);
                    if (!isCorrect) {
                        grade--;
                    }
                    view.displayCorrectAnswer(options);
                }
                view.displayGrade(grade, questionList.size());

                res = view.askForAction();
            } while (res.getAction() != 'L');
        } catch (View.ActionInterruptedException ignored) {
        }
    }
}
