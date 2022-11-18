package tgpr.schoolApp.view;

import tgpr.schoolApp.model.Course;
import tgpr.schoolApp.model.Registration;

import java.util.List;

public class TeacherListCourseView extends View {
    public void displayHeader() {
        displayHeader("Teacher Course List");
    }

    private void displayOneCourse(int i, Course course) {
        printf("[%2d] %s\n", i, course.getId() + " - " + course.getCode() + " - " + course.getDescription() + " - " +
                Registration.getById(course.getId()).size() + "/" + course.getCapacity());
    }

    public void displayCourses(List<Course> courses) {
        int i = 1;
        for (var c : courses) {
            displayOneCourse(i, c);
            i++;
        }
    }

    public boolean askConfirmationDelete(String s) {
        return super.askConfirmationDelete(s);
    }

    public View.Action askForAction(int size) {
        return (size > 0) ?
                doAskForAction(size, "\n[S#] Select course | [C] Create new course | [U#] Update course | [D#] Delete course | [M#] Manage subscription | [L] Logout\n",
                        "[sS][0-9]+|[cC]+|[uU][0-9]+|[dD][0-9]+|[mM][0-9]+|[lL]") :

                doAskForAction(size, "\n[C] Create new course | [L] Logout\n",
                        "[cC]+|[lL]");
    }
}
