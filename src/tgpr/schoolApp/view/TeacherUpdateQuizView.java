package tgpr.schoolApp.view;

import java.time.LocalDate;

public class TeacherUpdateQuizView extends View{

    public void displayHeader(){super.displayHeader("Update Quiz");}
    public void displayStartDate(LocalDate start){
        printf("Start date (" + start+ "): " + start + "\n");
    }

    public String askQuizTitle(String actual) {
        return askString("Title (" + actual + "): ", actual);
    }

    public LocalDate askStartDate(LocalDate actual) {
        return askDate("Start date (" + actual + "): ", actual);
    }

    public LocalDate askEndDate(LocalDate actual) {
        return askDate("End date (" + actual + "): ", actual);
    }

    public View.Action askForAction() {
        return doAskForAction(-1, "\n[S] Save | [C] Cancel\n", "[sScC]");
    }
}
