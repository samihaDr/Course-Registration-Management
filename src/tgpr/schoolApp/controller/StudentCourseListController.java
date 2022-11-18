package tgpr.schoolApp.controller;

import tgpr.schoolApp.SchoolApp;
import tgpr.schoolApp.model.Course;
import tgpr.schoolApp.model.Registration;
import tgpr.schoolApp.view.StudentCourseListView;
import tgpr.schoolApp.view.View;

public class StudentCourseListController extends Controller {
    @Override
    public void run() {
        var view = new StudentCourseListView();
        var student = SchoolApp.getLoggedUser();

        try {
            View.Action res;
            do {
                view = new StudentCourseListView();
                view.displayHeader();
                var availableCourseList = Course.getAvailableCourses(student.getPseudo());
                var studentRegistrationCourses = Course.getRegistrationCoursesByStudent(student.getPseudo());
                var studentRegistrations = Registration.getCoursesByStudentId(student.getPseudo());

                view.displayCourses(studentRegistrationCourses, studentRegistrations);
                res = view.askForAction(studentRegistrationCourses.size(), availableCourseList.size());

                switch (res.getAction()) {
                    case 'U': // unsubscribe from course
                        var registration = studentRegistrations.get(res.getNumber() - 1);
                        var course = studentRegistrationCourses.get(res.getNumber() - 1);

                        if (registration.getActive()) {
                            registration.setActive(!registration.getActive());
                            registration.save();
                        } else
                            view.displayUnsubscribeMessage(course.getDescription());
                        break;
                    case 'S': // select a course
                        registration = studentRegistrations.get(res.getNumber() - 1);
                        course = studentRegistrationCourses.get(res.getNumber() - 1);

                        if (registration.getActive()) {
                            new StudentSelectCourseController(course).run();
                        } else {
                            view.displaySelectMessage(course.getDescription());
                        }
                        break;
                    case 'R':
                        new StudentSubscribeController().run();
                        break;
                }
            } while (res.getAction() != 'L');
        } catch (View.ActionInterruptedException e) {
            view.pausedWarning("logged out");
        }
        SchoolApp.logout();
    }
}