package tgpr.schoolApp.view;

import tgpr.schoolApp.model.Test;

import java.util.List;

public class TeacherTestParticipantListView extends View{
    public void displayHeader() {
        super.displayHeader("Test participant list");
    }

    public void displayTestParticipantList(List<Test> participants) {
        int idx = 1;
        for (var participant : participants) {
            printf("[%2d] %s\n", idx++, participant.getStudentPseudo());
        }
    }

    public View.Action askForAction(int size) {
        return size > 0 ?
                doAskForAction(size, "\n[C#] Consult student test  | [L] Leave\n", "[cC][0-9]+|[lL]") :
                doAskForAction(size, "\n[L] Leave\n", "[lL]");
    }
}
