package tgpr.schoolApp.controller;

import tgpr.schoolApp.model.Course;
import tgpr.schoolApp.model.Registration;
import tgpr.schoolApp.view.TeacherManageSubscriptionView;
import tgpr.schoolApp.view.View;

public class TeacherManageSubscriptionController extends Controller {

    private int courseId;

    public TeacherManageSubscriptionController(int courseId) {
        this.courseId = courseId;
    }

    @Override
    public void run() {
        var registrations = Registration.getById(courseId);
        var view = new TeacherManageSubscriptionView();
        Course c = Course.getById(courseId);

        try {
            View.Action res;
            do {
                view.displayHeader();
                view.displayCourseInfo(c);
                view.displayRegistrations(registrations);
                if (!c.hasCapacity())
                    view.displayCapacityReachedMessage();

                res = view.askForAction(registrations.size(), c.hasCapacity());

                switch (res.getAction()) {
                    case 'C':
                        var r = registrations.get(res.getNumber() - 1);
                        r.setActive(!r.getActive());
                        r.save();
                        break;
                    case 'D': //delete student from course
                        r = registrations.get(res.getNumber() - 1);
                        if (view.askConfirmationDelete(r.getStudent().fullname))
                            r.delete();
                        break;
                    case 'A': //add student to course
                        var addStudent = new TeacherSubscribeStudentController(courseId);
                        addStudent.run();
                        break;
                }
                registrations = Registration.getById(courseId);
                view = new TeacherManageSubscriptionView();

            } while (res.getAction() != 'L');
        } catch (View.ActionInterruptedException e) {
        }
    }
}