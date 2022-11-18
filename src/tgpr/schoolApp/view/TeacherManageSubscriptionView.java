package tgpr.schoolApp.view;

import tgpr.schoolApp.model.Course;
import tgpr.schoolApp.model.Registration;

import java.util.List;

public class TeacherManageSubscriptionView extends View {
    public void displayHeader() {
        displayHeader("List of registered students");
    }

    public void displayCourseInfo(Course c) {
        String courseInfo = c.getId() + "  -  " + c.getCode() + "   -   " + c.getDescription() + "\n";
        printf("%s\n", courseInfo);
    }

    private void displayOneRegistration(int i, Registration registration) {
        String s1 = registration.getStudent().fullname;
        String s2 = "Status : " + registration.getStatus() + "\n";
        printf("[%2d] %-25s %s", i, s1, s2);
    }

    public void displayRegistrations(List<Registration> registrations) {
        int i = 1;
        for (var r : registrations) {
            displayOneRegistration(i, r);
            i++;
        }
    }

    public boolean askConfirmationDelete(String s) {
        return super.askConfirmationDelete(s);
    }

    public void displayCapacityReachedMessage() {
        error("\n!! Capacity for this course has been reached !!");
    }

    public View.Action askForAction(int size, boolean hasCapacity) {
        if (size > 0 && hasCapacity) {
            return doAskForAction(size, "\n[A] Add Student | [D#] Delete student | [C#] Change status | [L] Leave\n",
                    "[cC][0-9]+|[dD][0-9]+|[aA]+|[lL]");
        } else if (size > 0 && !hasCapacity) {
            return doAskForAction(size, "\n[C#] Change status | [D#] Delete student | [L] Leave\n",
                    "[cC][0-9]+|[dD][0-9]+|[lL]");
        }
        return doAskForAction(size, "\n[A] Add Student | [L] Leave\n",
                "[aA]+|[lL]");
    }
}