package tgpr.schoolApp.view;

public class TeacherUpdateQuestionView extends View {
    public void displayHeader() {
        super.displayHeader("Update Question");
    }

    public String askType(String actual) {
        return askString("Type (" + actual + "): ", actual);
    }

    public String askTitle(String actual) {
        return askString("Title (" + actual + "): ", actual);
    }

    public View.Action askForAction() {
        return doAskForAction(-1, "\n[S] Save | [C] Cancel\n", "[sScC]");
    }
}
