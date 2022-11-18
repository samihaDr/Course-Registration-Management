package tgpr.schoolApp.controller;

import tgpr.schoolApp.model.Course;
import tgpr.schoolApp.model.Model;
import tgpr.schoolApp.model.Quiz;
import tgpr.schoolApp.view.TeacherUpdateQuizView;
import tgpr.schoolApp.view.View;

import java.time.LocalDate;

public class TeacherUpdateQuizController extends Controller {
    private final Quiz quiz;
    private final Course course;
    private final TeacherUpdateQuizView view = new TeacherUpdateQuizView();

    public TeacherUpdateQuizController(Quiz quiz, Course course) {
        this.quiz = quiz;
        this.course = course;
    }

    public String askQuizTitle(String actual) {
        String title;
        String error = null;
        do {
            error = null;
            title = Model.capitalizeFirstLetter(view.askQuizTitle(actual));
            assert title != null;
            if (!title.equals(quiz.getTitle()))
                error = Quiz.isValidTitle(title, course.getId());
            if (error != null) view.error(error);
        } while (error != null);
        return title;
    }

    public LocalDate askStartDate(LocalDate start) {
        LocalDate startDate;
        String error;
        do {
            error=null;
            startDate = view.askStartDate(start);
            if(!startDate.equals(start))
                error = Quiz.isValidStartDate(startDate);
            if (error != null) view.error(error);
        } while (error != null);
        return startDate;
    }

    public LocalDate askEndDate(LocalDate start, LocalDate end) {
        LocalDate endDate;
        String error;
        do {
            error=null;
            endDate = view.askEndDate(end);
            if(!endDate.equals(end))
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
        try {
            view.displayHeader();
            view.displayCourseInfoTeacher(course);
            String quizTitle = askQuizTitle(quiz.getTitle());
            LocalDate start = null;
            if (!quiz.hasTest()) {
                start = askStartDate(quiz.getStart());
            } else {
                start = quiz.getStart();
                view.displayStartDate(start);
            }

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
            view.pausedWarning("Update quiz aborted");
        }
    }

}

