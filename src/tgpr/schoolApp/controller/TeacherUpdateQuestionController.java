package tgpr.schoolApp.controller;

import tgpr.schoolApp.model.Course;
import tgpr.schoolApp.model.Question;
import tgpr.schoolApp.model.Quiz;
import tgpr.schoolApp.view.TeacherUpdateQuestionView;
import tgpr.schoolApp.view.View;

public class TeacherUpdateQuestionController extends Controller {
    Question question;
    Course course;
    Quiz quiz;
    TeacherUpdateQuestionView view = new TeacherUpdateQuestionView();

    public TeacherUpdateQuestionController(Question question, Course course, Quiz quiz) {
        this.question = question;
        this.course = course;
        this.quiz = quiz;
    }

    public String askTitle(String actual) {
        String title;
        String error;
        do {
            error = null;
            title = view.askTitle(actual);
            if (!title.equals(actual))
                error = Question.isValidTitle(title);
            if (error != null) view.error(error);
        } while (error != null);
        return title;
    }

    public String askType(String actual) {
        String type;
        String error;
        do {
            error = null;
            type = view.askType(actual);
            if (!type.equals(actual))
                error = Question.isValidType(type);
            if (error != null) view.error(error);
        } while (error != null);
        return type;
    }

    @Override
    public void run() {
        View.Action res;

        try {
            view.displayHeader();
            view.displayCourseInfoTeacher(course);
            view.displayQuizInfo(quiz);
            String title = askTitle(question.getTitle());
            String type = askType(question.getType());

            question.setTitle(title);
            question.setType(type.toUpperCase());

            res = view.askForAction();
            if (res.getAction() == 'S') {
                question.save();
            }
        } catch (View.ActionInterruptedException e) {
            view.pausedWarning("Update question aborted");
        }
    }
}
