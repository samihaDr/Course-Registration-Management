package tgpr.schoolApp.view;

public class LoginView extends View {

    public void displayHeader() {
        displayHeader("Login");
    }

    public String askPseudo() {
        return askString("Pseudo: ", null);
    }

    public String askPassword() {
        return askString("Password: ", null, true);
    }
}