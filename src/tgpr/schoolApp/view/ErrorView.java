package tgpr.schoolApp.view;

public class ErrorView extends View {
    public ErrorView(String error) {
        displayHeader();
        pausedError(error);
    }

    private void displayHeader() {
        displayHeader("Error");
    }
}