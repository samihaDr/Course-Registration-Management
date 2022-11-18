package tgpr.schoolApp.view;

import tgpr.schoolApp.model.Course;
import tgpr.schoolApp.model.User;

import java.util.List;

public class StudentSubscribeView extends View {
    public void displayHeader() {
        displayHeader("Available Courses List");
    }

    private void displayOneCourse(int i, Course course) {
        printf("[%2d] %s\n", i, course.getId() + " - " + course.getCode() + " - " + course.getDescription()
                + " - " + User.getByPseudo(course.getPseudo()).fullname + "\n");
    }

    public void displayCourses(List<Course> courseList) {
        int i = 1;
        for (var course : courseList) {
            displayOneCourse(i, course);
            i++;
        }
    }

    public View.Action askForAction(int size) {
        return size > 0 ?
                doAskForAction(size, "\n[S#] Subscribe to course | [L] Leave",
                        "[sS][0-9]+|[lL]") :
                doAskForAction(size, "\n[L] Leave",
                        "[lL]");
    }
}

