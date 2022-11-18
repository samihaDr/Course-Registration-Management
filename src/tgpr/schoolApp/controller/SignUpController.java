package tgpr.schoolApp.controller;

import tgpr.schoolApp.model.Student;
import tgpr.schoolApp.view.SignUpView;
import tgpr.schoolApp.view.View;

import java.time.LocalDate;
import java.util.List;

public class SignUpController extends Controller {

    private SignUpView view = new SignUpView();

    // permet une validation immédiate du mot de passe
    public String askPseudo(String actual) {
        String pseudo;
        String error;
        do {
            pseudo = view.askPseudo(actual);
            error = Student.isValidAvailablePseudo(pseudo);
            if (error != null) view.error(error);
        } while (error != null);
        return pseudo;
    }

    public String askConfirmPassword(String cPassword, String password) {
        String confirmPassword;
        String error;
        do {
            confirmPassword = view.askConfirmPassword(cPassword);
            error = Student.isValidConfirmPassword(confirmPassword, password);
            if (error != null) view.error(error);
        } while (error != null);
        return confirmPassword;
    }

    public String askPassword(String actual) {
        String password;
        String error;
        do {
            password = view.askPassword(actual);
            error = Student.isValidPassword(password);
            if (error != null) view.error(error);
        } while (error != null);
        return password;
    }

    public String askFullname(String actual){
        String fullname;
        String error;
        do {
            fullname = view.askFullname(actual);
            error = Student.isValidFullname(fullname);
            if(error != null) view.error(error);
        }while (error != null);
        return fullname;
    }

    public void run() {
        try {
            View.Action res;
            List<String> errors;
            var student = new Student();
            do {
                view.displayHeader();

                String pseudo = askPseudo(student.getPseudo());  // avec validation immédiate
                String password = askPassword(student.getPassword());
                String confirmPassword = askConfirmPassword(student.getConfirmPassword(), password);
                String fullname = askFullname(student.getFullname());
                LocalDate birthDate = view.askBirthDate(student.getBirthdate());
                int role = student.getRole();

                student.setPseudo(pseudo);
                student.setPassword(password);
                student.setConfirmPassword(confirmPassword);
                student.setFullname(fullname);
                student.setBirthdate(birthDate);
                student.setRole(role);

                // validation différée (on vérifie toutes les règles métier d'un coup)
                errors = student.validate();
                if (errors.size() > 0)
                    view.showErrors(errors);

            } while (errors.size() > 0);

            res = view.askForAction();
            if (res.getAction() == 'S') {
                student.setPassword(student.getPassword());
                student.save();
            }
        } catch (View.ActionInterruptedException e) {
            view.pausedWarning("add student aborted");
        }
    }
}
