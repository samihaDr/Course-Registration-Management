package tgpr.schoolApp.controller;

import tgpr.schoolApp.model.Course;
import tgpr.schoolApp.model.Option;
import tgpr.schoolApp.model.Question;
import tgpr.schoolApp.model.Quiz;
import tgpr.schoolApp.view.TeacherCreateOptionView;
import tgpr.schoolApp.view.TeacherUpdateOptionView;
import tgpr.schoolApp.view.View;

public class TeacherUpdateOptionController extends Controller {
    Question question;
    Quiz quiz;
    Course course;
    Option option;
    private  TeacherUpdateOptionView view = new TeacherUpdateOptionView();

    public TeacherUpdateOptionController(Question question, Quiz quiz, Course course, Option option) {
        this.question = question;
        this.quiz = quiz;
        this.course = course;
        this.option = option;
    }

    public String askTitleOption(String actual) {
        String title;
        String error;
        do {
            title = view.askOptionTitle(actual);
            error = Option.isValidTitle(title);
            if (error != null) view.error(error);
        } while (error != null);
        return title;
    }

    public String askCorrectOption(String actual, int questionId) {
        String correct;
        String error;
        do {
            correct = view.askOptionCorrect(actual);
            error = null;
            if (!correct.equals(actual)) {
                if (question.getType().equals("QCM")) {
                    error = Option.isValidInputQcm(correct, questionId);
                } else {
                    error = Option.isValidInputQrm(correct, questionId);
                }

            }

            if (error != null) view.error(error);
        } while (error != null);
        return correct;
    }

    @Override
    public void run() {
        View.Action res;


        try {
            view.displayHeader();
            view.displayCourseInfoTeacher(course);
            view.displayQuizInfo(quiz);
            view.displayQuestionInfo(question);
            String optionTitle = askTitleOption(option.getTitle());
            String optionCorrect = askCorrectOption(String.valueOf(option.isCorrect()), question.getId());

            option.setTitle(optionTitle);
            option.setCorrect(Boolean.parseBoolean(optionCorrect));
            option.setQuestionId(question.getId());

            res = view.askForAction();

            if (res.getAction() == 'S') {
                option.save();
            }

        } catch (View.ActionInterruptedException e) {
            view.pausedWarning("update option aborted");
        }

    }

}
