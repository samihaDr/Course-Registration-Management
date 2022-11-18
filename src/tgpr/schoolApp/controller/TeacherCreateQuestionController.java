package tgpr.schoolApp.controller;

import tgpr.schoolApp.model.Course;
import tgpr.schoolApp.model.Question;
import tgpr.schoolApp.model.Quiz;
import tgpr.schoolApp.view.TeacherCreateQuestionView;
import tgpr.schoolApp.view.View;

import java.util.Locale;

public class TeacherCreateQuestionController extends Controller{
    private final Quiz quiz;
    private final Course course;
    private final TeacherCreateQuestionView view = new TeacherCreateQuestionView();

    public TeacherCreateQuestionController(Quiz quiz, Course course) {
        this.quiz = quiz;
        this.course = course;
    }
    public String askTitleQuestion(String actual){
        String title;
        String error;
        do {
            title = view.askQuestionTitle(actual);
            error = Question.isValidTitle(title);
            if(error != null) view.error(error);
        }while(error != null);
        return title;
    }

    public String askTypeQuestion(String actual){
        String type;
        String error;
        do {
            type = view.askQuestionType(actual);
            error = Question.isValidType(type);
            if(error != null) view.error(error);
        }while (error != null);
        return type;
    }
    @Override
    public void run() {
        View.Action res;
        var question = new Question();
        try {
            view.displayHeader();
            view.displayCourseInfoTeacher(course);
            view.displayQuizInfo(quiz);
            String questionTitle = askTitleQuestion(question.getTitle());
            String questionType = askTypeQuestion(question.getType());

            question.setTitle(questionTitle);
            question.setType(questionType.toUpperCase());
            question.setQuizId(quiz.getId());

            res = view.askForAction();
            if (res.getAction() == 'S') {
                question.save();
            }
        } catch (View.ActionInterruptedException e) {
            view.pausedWarning("create question aborted");
        }
    }
}
