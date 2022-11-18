package tgpr.schoolApp.view;

public class TeacherUpdateOptionView extends View {
    public void displayHeader(){ super.displayHeader("Create Option"); }

    public String askOptionTitle(String actual) {
        return askString("Title (" + actual + "): ", actual);
    }

    public String askOptionCorrect(String actual) {
        return askString("Correct (" + actual + "): ", actual);
    }

    public View.Action askForAction() {
        return doAskForAction(-1, "\n[S] Save | [C] Cancel\n", "[sScC]");
    }
}
