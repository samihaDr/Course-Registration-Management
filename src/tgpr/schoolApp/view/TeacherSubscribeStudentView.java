package tgpr.schoolApp.view;

import tgpr.schoolApp.model.Course;
import tgpr.schoolApp.model.Student;

import java.util.List;

public class TeacherSubscribeStudentView extends View {
    public void displayHeader() {
        displayHeader("List of Unregistered students");
    }

    public void displayHeaderInfoCourse(Course course) {
        printf(course.getId() + " - " + course.getCode() + " - " + course.getDescription() + "\n\n");
    }

    public void displayNonRegisteredStudents(List<Student> students) {
        int idx = 1;
        for (var student : students) {
            printf("[%2d] %s\n", idx++, student.fullname);
        }
    }

    public View.Action askForAction(int size, boolean hasCapacity) {
        return size > 0 && hasCapacity ?
                doAskForAction(size, "\n[S#] Subscribe | [L] Leave\n",
                        "[sS][0-9]+|[lL]") :
                doAskForAction(size, "\n[L] Leave\n",
                        "[lL]");
    }
}