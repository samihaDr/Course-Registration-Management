package tgpr.schoolApp.view;

public class TeacherCreateQuestionView extends View{
    public void displayHeader(){ super.displayHeader("Create Question"); }

    public String askQuestionTitle(String actual) {
        return askString("Title (" + actual + "): ", actual);
    }

    public String askQuestionType(String actual) {
        return askString("Type (" + actual + "): ", actual);
    }

    public View.Action askForAction() {
        return doAskForAction(-1, "\n[S] Save | [C] Cancel\n", "[sScC]");
    }
}
