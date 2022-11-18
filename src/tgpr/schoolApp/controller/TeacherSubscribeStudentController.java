package tgpr.schoolApp.controller;

import tgpr.schoolApp.model.Course;
import tgpr.schoolApp.model.Student;
import tgpr.schoolApp.view.TeacherSubscribeStudentView;
import tgpr.schoolApp.view.View;

public class TeacherSubscribeStudentController extends Controller {

    private int courseId;

    public TeacherSubscribeStudentController(int courseId) {
        this.courseId = courseId;
    }

    @Override
    public void run() {
        var view = new TeacherSubscribeStudentView();
        var studentList = Student.getNonRegisteredStudents(courseId);
        Course c = Course.getById(courseId);

        try {
            View.Action res;
            do {
                view.displayHeader();
                view.displayHeaderInfoCourse(c);
                view.displayNonRegisteredStudents(studentList);
                res = view.askForAction(studentList.size(), c.hasCapacity());

                switch (res.getAction()) {
                    case 'S': // Register a student for a course
                        var student = studentList.get(res.getNumber() - 1);
                        Student.subscribeStudent(studentList, student.getPseudo(), courseId);
                        view = new TeacherSubscribeStudentView();
                        studentList = Student.getNonRegisteredStudents(courseId);
                        break;

                    case 'O': //other action
                        break;
                }
            } while (res.getAction() != 'L');
        } catch (View.ActionInterruptedException e) {
        }
    }
}