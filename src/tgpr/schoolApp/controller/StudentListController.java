package tgpr.schoolApp.controller;

import tgpr.schoolApp.SchoolApp;
import tgpr.schoolApp.model.Student;
import tgpr.schoolApp.view.StudentListView;
import tgpr.schoolApp.view.View;

public class StudentListController extends Controller {
    @Override
    public void run() {
        var members = Student.getAll();
        var view = new StudentListView();
        var current = SchoolApp.getLoggedUser();
        try {
            View.Action res;
            do {
                view.displayHeader();
                view.displayMembers(members);
                if (SchoolApp.isRole())
                    res = view.askForActionAdmin(members.size());
                else
                    res = view.askForAction(members.size());
                switch (res.getAction()) {
                    case 'A':
                        new SignUpController().run();
                        members = Student.getAll();
                        break;
                }
            } while (res.getAction() != 'L');
        } catch (View.ActionInterruptedException e) {
            // just leave the loop
        }
    }
}