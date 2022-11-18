package tgpr.schoolApp.controller;

import tgpr.schoolApp.model.Course;
import tgpr.schoolApp.model.Quiz;
import tgpr.schoolApp.view.TeacherCreateQuizView;
import tgpr.schoolApp.view.View;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TeacherCreateQuizController extends Controller {
    private final Course course;
    private final TeacherCreateQuizView view = new TeacherCreateQuizView();

    public TeacherCreateQuizController(Course course) {
        this.course = course;
    }

    public String askQuizTitle(String actual) {
        String title;
        String error;
        do {
            title = view.askQuizTitle(actual);
            error = Quiz.isValidTitle(title, course.getId());
            if (error != null) view.error(error);
        } while (error != null);
        return title;
    }

    public LocalDate askStartDate(LocalDate start) {
        LocalDate startDate;
        String error;
        do {
            startDate = view.askStartDate(start);
            error = Quiz.isValidStartDate(startDate);
            if (error != null) view.error(error);
        } while (error != null);
        return startDate;
    }

    public LocalDate askEndDate(LocalDate start, LocalDate end) {
        LocalDate endDate;
        String error;
        do {
            endDate = view.askEndDate(end);
            error = Quiz.isValidEndDate(endDate);
            if (error != null) {
                view.error(error);
            }
            if (endDate.isBefore(start)) {
                error = "End date must be after start date.";
                view.error(error);
            }
        } while (error != null);
        return endDate;
    }

    @Override
    public void run() {
        View.Action res;
        var quiz = new Quiz();

        try {
            view.displayHeader();
            view.displayCourseInfoTeacher(course);
            String quizTitle = askQuizTitle(quiz.getTitle());
            LocalDate start = askStartDate(quiz.getStart());
            LocalDate end = askEndDate(start, quiz.getFinish());

            quiz.setTitle(quizTitle);
            quiz.setStart(start);
            quiz.setFinish(end);
            quiz.setCourseId(course.getId());

            res = view.askForAction();
            if (res.getAction() == 'S') {
                quiz.save();
            }
        } catch (View.ActionInterruptedException e) {
            view.pausedWarning("create quiz aborted");
        }
    }
}