package tgpr.schoolApp.view;

import java.time.LocalDate;

public class TeacherCreateQuizView extends View {
    public void displayHeader() {
        super.displayHeader("Create Quiz");
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