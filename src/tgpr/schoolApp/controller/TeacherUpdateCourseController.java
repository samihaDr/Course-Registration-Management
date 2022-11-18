package tgpr.schoolApp.controller;

import tgpr.schoolApp.SchoolApp;
import tgpr.schoolApp.model.Course;
import tgpr.schoolApp.view.TeacherUpdateCourseView;
import tgpr.schoolApp.view.View;

import java.util.List;
import java.util.Locale;

public class TeacherUpdateCourseController extends Controller {
    private TeacherUpdateCourseView view = new TeacherUpdateCourseView();
    private Course course = new Course();

    public TeacherUpdateCourseController(Course course) {
        this.course = course;
    }

    public String askCode(String actual) {
        String code;
        String error;
        do {
            code = view.askCode(actual);
            error = Course.isValidCode(code);
            if (error != null) view.error(error);
        } while (error != null);
        return code;
    }

    public String askDescription(String actual) {
        String description;
        String error;
        do {
            error = null;
            description = view.askDescription(actual);
            error = Course.isValidDescription(description);
            if (error != null)
                view.error(error);
        } while (error != null);
        return description;
    }

    public int askCapacity(int actual) {
        int capacity;
        String error;
        do {
            capacity = view.askCapacity(actual);
            error = Course.isValidCapacity(capacity, course.getId());
            if (error != null) view.error(error);
        } while (error != null);
        return capacity;
    }


    @Override
    public void run() {
        View.Action res;
        List<String> errors;
        course = Course.getById(course.getId());
        var teacher = SchoolApp.getLoggedUser();
        try {
            do {
                view.displayHeader();

                String code = askCode(course.getCode());
                String description = askDescription(course.getDescription());
                int capacity = askCapacity(course.getCapacity());


                course.setCode(code.toUpperCase(Locale.ROOT));
                course.setDescription(description);
                course.setCapacity(capacity);
                course.setPseudo(teacher.pseudo);
                errors = course.validate();
                if (errors.size() > 0)
                    view.showErrors(errors);
            } while (errors.size() > 0);

            res = view.askForAction();
            if (res.getAction() == 'S') {
                course.save();
            }
        } catch (View.ActionInterruptedException e) {
            view.pausedWarning("update course aborted");
        }
    }

}
