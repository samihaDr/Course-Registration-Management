package tgpr.schoolApp.view;

import tgpr.schoolApp.model.Course;
import tgpr.schoolApp.model.Registration;
import tgpr.schoolApp.model.User;

import java.util.List;

public class StudentCourseListView extends View {
    public void displayHeader() {
        displayHeader("Student Course List");
    }

    public void displayCourses(List<Course> courseList, List<Registration> registrationList) {
        for (int i = 0; i < courseList.size(); i++) {
            printf("[%2d] %-8s%s\n", i + 1, registrationList.get(i).getStatus(), " - " + courseList.get(i).getId() + " - "
                    + courseList.get(i).getCode() + " - " + courseList.get(i).getDescription() + " - "
                    + User.getByPseudo(courseList.get(i).getPseudo()).fullname + "\n");
        }
    }

    public void displayUnsubscribeMessage(String courseDescription) {
        pausedWarning("This course : \"" + courseDescription + "\" is already inactive.");
    }

    public void displaySelectMessage(String courseDescription) {
        pausedWarning("This course : \"" + courseDescription + "\" is not active, you don't have permissions.");
    }

    public View.Action askForAction(int size, int sizeR) {
        if (size > 0 && sizeR > 0) {
            return doAskForAction(size, "\n[S#] Select course | [R] Register for a course | [U#] Unsubscribe from course | [L] Logout\n",
                    "[sS][0-9]+|[rR]+|[uU][0-9]+|[lL]");
        } else if (size > 0 && sizeR == 0) {
            return doAskForAction(size, "\n[S#] Select course | [U#] Unsubscribe from course | [L] Logout\n",
                    "[sS][0-9]+|[uU][0-9]+|[lL]");
        } else if (size == 0 && sizeR > 0) {
            return doAskForAction(size, "\n[R] Register for a course | [L] Logout\n",
                    "[rR]+|[lL]");
        } else
            return doAskForAction(size, "\n[L] Logout\n",
                    "[lL]");
    }
}