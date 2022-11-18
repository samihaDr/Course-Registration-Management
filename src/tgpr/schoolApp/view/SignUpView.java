package tgpr.schoolApp.view;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collections;

public class SignUpView extends View {

    public void displayHeader() {
        super.displayHeader("Sign Up");
    }

    public String askPseudo(String actual) {
        return askString("Pseudo (" + actual + "): ", actual);
    }

    public String askPassword(String actual) {
        String stars = null;
        if (actual != null)
            stars = String.join("", Collections.nCopies(actual.length(), "*"));
        return askString("Password (" + stars + "): ", actual, true);
    }

    public String askConfirmPassword(String actual) {
        String stars = null;
        if (actual != null)
            stars = String.join("", Collections.nCopies(actual.length(), "*"));
        return askString("Confirm password (" + stars + "): ", actual, true);
    }

    public String askFullname(String actual) {
        return askString("FullName (" + actual + "): ", actual);
    }

    public LocalDate askBirthDate(LocalDate actual) {
        return askDate("Birth Date (" +
                (actual == null ? "null" : DateTimeFormatter.ofPattern("dd/MM/yyyy").format(actual)) + "): ", actual);
    }

    public View.Action askForAction() {
        return doAskForAction(-1, "\n[S] Save, [C] Cancel", "[sScC]");
    }
}
