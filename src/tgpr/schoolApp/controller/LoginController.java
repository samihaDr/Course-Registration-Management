package tgpr.schoolApp.controller;

import tgpr.schoolApp.SchoolApp;
import tgpr.schoolApp.model.User;
import tgpr.schoolApp.model.Student;
import tgpr.schoolApp.view.LoginView;
import tgpr.schoolApp.view.View;

public class LoginController extends Controller {

    private final LoginView view = new LoginView();

    private User askPseudo() {
        String pseudo;
        User user;
        do {
            pseudo = view.askPseudo();
            user = User.getByPseudo(pseudo);
            if (user == null)
                view.error("unknown user");
        } while (user == null);
        return user;
    }

    private void askPassword(User user) {
        String password;
        do {
            password = view.askPassword();
            if (password == null || !password.equals(user.getPassword()))
                view.error("bad password");
        } while (password == null || !password.equals(user.getPassword()));
    }

    public void run() {
        view.displayHeader();
        try {
            var user = askPseudo();
            askPassword(user);
            SchoolApp.setLoggedUser(user);
            if (SchoolApp.isTeacher()){
                new TeacherListCourseController().run();
            }else{
                new StudentCourseListController().run();
            }
        } catch (View.ActionInterruptedException e) {
            view.pausedWarning("aborted login");
        }
    }
}