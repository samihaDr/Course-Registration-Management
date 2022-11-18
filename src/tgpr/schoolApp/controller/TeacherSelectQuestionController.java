package tgpr.schoolApp.controller;

import tgpr.schoolApp.model.*;
import tgpr.schoolApp.view.TeacherSelectQuestionView;
import tgpr.schoolApp.view.View;

public class TeacherSelectQuestionController extends Controller {

    private final TeacherSelectQuestionView view = new TeacherSelectQuestionView();
    private final Question question;
    private Course course;
    private final Quiz quiz;


    public TeacherSelectQuestionController(Question question,Course course, Quiz quiz) {
        this.question = question;
        this.course = course;
        this.quiz = quiz;
    }

    @Override
    public void run() {
        try {
            View.Action res;
            do {
                var options = Option.getByIdQuestion(question.getId());
                view.displayHeaderSelectQuestion();
                view.displayCourseInfoTeacher(course);
                view.displayQuizInfo(quiz);
                view.displayQuestionInfo(question);
                view.displayOptions(options);

                var answers = Answer.getByQuiz(quiz.getId());

                res = view.askForAction(options.size(),question.hasAnswer(answers,options));

                switch (res.getAction()) {
                    case 'C': // create option
                        new TeacherCreateOptionController(question,quiz,course).run();
                        break;
                    case 'U': //Update option
                        var option = options.get(res.getNumber() - 1);
                            new TeacherUpdateOptionController(question,quiz,course,option).run();
                        break;
                    case 'D': // delete option
                         option = options.get(res.getNumber() - 1);
                        if (view.askConfirmationDelete(option.getTitle()))
                            option.delete();
                        break;
                }
            } while (res.getAction() != 'L');

        } catch (View.ActionInterruptedException e) {

        }
    }
}
