package tgpr.schoolApp.controller;

import tgpr.schoolApp.SchoolApp;
import tgpr.schoolApp.model.Course;
import tgpr.schoolApp.view.TeacherListCourseView;
import tgpr.schoolApp.view.View;

public class TeacherListCourseController extends Controller {
    @Override
    public void run() {
        var current = SchoolApp.getLoggedUser();
        var courses = Course.getByTeacher(current.getPseudo());
        var view = new TeacherListCourseView();
        try {
            View.Action res;
            do {
                view.displayHeader();
                view.displayCourses(courses);
                res = view.askForAction(courses.size());
                Course course;

                switch (res.getAction()) {
                    case 'S':
                        course = courses.get(res.getNumber()-1);
                        new TeacherSelectCourseController(course).run();
                        break;

                    case 'U': //Update course
                        course = courses.get(res.getNumber()-1);
                        new TeacherUpdateCourseController(course).run();
                        break;

                    case 'D': //Delete course
                        course = courses.get(res.getNumber()-1);
                        if (view.askConfirmationDelete(course.getCode()))
                            course.delete();
                        break;

                    case 'C':
                        new TeacherCreateCourseController().run();
                        break;

                    case 'M':
                        course = courses.get(res.getNumber()-1);
                        new TeacherManageSubscriptionController(course.getId()).run();
                        break;
                }

                courses = Course.getByTeacher(current.getPseudo());
            } while (res.getAction() != 'L');
        } catch (View.ActionInterruptedException e){
            view.pausedWarning("logged out");
        }
        SchoolApp.logout();
    }
}