package tgpr.schoolApp.view;

import tgpr.schoolApp.model.Student;

import java.util.List;

public class StudentListView extends View {
    public void displayHeader() {
        displayHeader("Student List");
    }

    private void displayOneMember(int i, Student m) {
        String status = "";
        /*if (m.isMutualFriend(SchoolApp.getLoggedUser()))
            status = "(mutual friend)";
        else if (m.isFollower(SchoolApp.getLoggedUser()))
            status = "(following you)";
        else if (m.isFollowee(SchoolApp.getLoggedUser()))
            status = "(followed by you)";*/
        printf("[%2d] %s %s\n", i, m.getPseudo(), status);
    }

    public void displayMembers(List<Student> students) {
        int i = 1;
        for (var m : students) {
            displayOneMember(i, m);
            ++i;
        }
    }

    public View.Action askForAction(int size) {
        return doAskForAction(size, "\n[F] Follow, [U] Unfollow, [V] View, [L] Leave",
                "[vV][0-9]+|[fF][0-9]+|[uU][0-9]+|[lL]");
    }

    public View.Action askForActionAdmin(int size) {
        return doAskForAction(size, "\n[A] Add Student, [F] Follow, [U] Unfollow, [V] View, [L] Leave",
                "[aA]|[vV][0-9]+|[fF][0-9]+|[uU][0-9]+|[lL]");
    }
}