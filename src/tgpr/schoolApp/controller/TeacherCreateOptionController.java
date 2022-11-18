package tgpr.schoolApp.controller;

import com.sun.source.tree.PackageTree;
import tgpr.schoolApp.model.*;
import tgpr.schoolApp.view.TeacherCreateOptionView;
import tgpr.schoolApp.view.View;

public class TeacherCreateOptionController extends Controller {

    Question question;
    Quiz quiz;
    Course course;
    private final TeacherCreateOptionView view = new TeacherCreateOptionView();

    public TeacherCreateOptionController(Question question, Quiz quiz, Course course) {
        this.question = question;
        this.quiz = quiz;
        this.course = course;
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
            if(question.getType().equals("QCM")){
                error = Option.isValidInputQcm(correct, questionId);
            }else{
                error = Option.isValidInputQrm(correct,questionId);
            }

            if (error != null) view.error(error);
        } while (error != null);
        return correct;
    }


    @Override
    public void run() {
        View.Action res;

        var option = new Option();
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
            view.pausedWarning("create option aborted");
        }

    }
}
