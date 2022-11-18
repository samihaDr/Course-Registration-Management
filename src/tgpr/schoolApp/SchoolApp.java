package tgpr.schoolApp;

import tgpr.schoolApp.controller.StartMenuController;
import tgpr.schoolApp.model.*;
import tgpr.schoolApp.view.ErrorView;

import java.time.LocalDate;

public class SchoolApp {
    private static User loggedUser = null;

    private static LocalDate currentDate = LocalDate.now();

    public static User getLoggedUser() {
        return loggedUser;
    }

    public static void setLoggedUser(User loggedStudent) {
        SchoolApp.loggedUser = loggedStudent;
    }

    public static LocalDate getCurrentDate() {
        return currentDate;
    }

    public static void setCurrentDate(LocalDate currentDate) {
        SchoolApp.currentDate = currentDate;
    }

    public static boolean isLogged() {
        return loggedUser != null;
    }

    public static void logout() {
        setLoggedUser(null);
    }

    //#TODO : adapt or delete
    public static boolean isRole() {
        //return loggedStudent != null && loggedStudent.isRole();
        return true;
    }

    public static void main(String[] args) {
        if (!Model.checkDb())
            new ErrorView("Database is not available").close();
        else
            new StartMenuController().run();
    }

    public static boolean isTeacher(){
        return loggedUser instanceof Teacher;
    }

/*    private static void testModel() {
        User stud = User.getByPseudo("caro");
        System.out.println(stud.getClass());
        System.out.println(stud);
        User teac = User.getByPseudo("ben");
        System.out.println(teac.getClass());
        System.out.println(teac);
    }*/
}