package tgpr.schoolApp.controller;

import tgpr.schoolApp.SchoolApp;
import tgpr.schoolApp.model.Course;
import tgpr.schoolApp.model.Registration;
import tgpr.schoolApp.view.StudentSubscribeView;
import tgpr.schoolApp.view.View;

public class StudentSubscribeController extends Controller {

    @Override
    public void run() {
        var view = new StudentSubscribeView();
        var student = SchoolApp.getLoggedUser();

        try {
            View.Action res;
            do {
                var availableCourseList = Course.getAvailableCourses(student.getPseudo());
                view.displayHeader();
                view.displayCourses(availableCourseList);
                res = view.askForAction(availableCourseList.size());

                switch (res.getAction()) {
                    case 'S':     // subscribe from course
                        var registration = new Registration();
                        var course = availableCourseList.get(res.getNumber() - 1);
                        registration.setStudentId(SchoolApp.getLoggedUser().getPseudo());
                        registration.setCourseId(course.getId());
                        registration.setActive(false);
                        registration.save();
                        break;
                }
            } while (res.getAction() != 'L');

        } catch (View.ActionInterruptedException e) {
        }
    }
}
