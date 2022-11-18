package tgpr.schoolApp.view;

public class TeacherUpdateCourseView extends View {

    public void displayHeader() {
        super.displayHeader("Update Course");
    }

    public int askId(int actual) {
        return askInteger("Id (" + actual + "): ", actual);
    }

    public String askCode(String actual) {
        return askString("Code (" + actual + "): ", actual);
    }

    public String askDescription(String actual) {
        return askString("Description (" + actual + "): ", actual);
    }

    public int askCapacity(int actual) {
        return askInteger("Capacity (" + actual + "): ", actual);
    }

    public View.Action askForAction() {
        return doAskForAction(-1, "\n[S] Save | [C] Cancel", "[sScC]");
    }
}
