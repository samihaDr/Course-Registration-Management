package tgpr.schoolApp.view;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class ChangeCurrentDateView extends View {

    public void displayHeader() {
        displayHeader("Change the current date");
    }

    public void displayDate(LocalDate currentDate) {
        println("Date: " + currentDate);
    }

    public LocalDate askNewDate(LocalDate actual) {
        return askDate("New date (" +
                (actual == null ? "null" : DateTimeFormatter.ofPattern("yyyy-MM-dd").format(actual)) + "): ", actual);
    }

    public View.Action askForAction() {
        return doAskForAction(-1,
                "\n[O] Confirm, [C] Cancel", "[oO]|[cC]");
    }
}
