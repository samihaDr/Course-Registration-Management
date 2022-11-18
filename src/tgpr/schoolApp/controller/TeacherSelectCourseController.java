package tgpr.schoolApp.controller;

import tgpr.schoolApp.model.Course;
import tgpr.schoolApp.model.Quiz;
import tgpr.schoolApp.view.TeacherSelectCourseView;
import tgpr.schoolApp.view.View;

public class TeacherSelectCourseController extends Controller {
    private final Course course;
    private final TeacherSelectCourseView view;

    public TeacherSelectCourseController(Course course) {
        this.course = course;
        this.view = new TeacherSelectCourseView();
    }

    @Override
    public void run() {
        var quizList = Quiz.getByCourseId(course.getId());
        try {
            View.Action res;
            do {
                view.displayHeader();
                view.displayQuizInfo(course);
                view.displayQuiz(quizList);
                res = view.askForAction(quizList.size());

                switch (res.getAction()) {
                    case 'C':     // create new quiz
                        new TeacherCreateQuizController(course).run();
                        break;

                    case 'U': //Update quiz
                        var quiz = quizList.get(res.getNumber() - 1);
                        new TeacherUpdateQuizController(quiz,course).run();
                        break;

                    case 'S': //Select quiz
                        quiz = quizList.get(res.getNumber() - 1);
                        new TeacherSelectQuizController(quiz).run();
                        break;

                    case 'D': //Delete quiz
                        quiz = quizList.get(res.getNumber() - 1);
                        if(view.askConfirmationDelete(quiz.getTitle()))
                            quiz.delete();
                        break;
                }
                quizList = Quiz.getByCourseId(course.getId());
            } while (res.getAction() != 'L');

        } catch (View.ActionInterruptedException e) {
        }
    }
}