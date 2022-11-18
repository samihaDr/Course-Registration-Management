package tgpr.schoolApp.controller;

import tgpr.schoolApp.SchoolApp;
import tgpr.schoolApp.view.ChangeCurrentDateView;
import tgpr.schoolApp.view.View;

import java.time.LocalDate;
import java.util.List;

public class ChangeCurrentDateController extends Controller {

    private final ChangeCurrentDateView view = new ChangeCurrentDateView();

    @Override
    public void run() {
        View.Action res;
        LocalDate newDate;

        do {
            view.displayHeader();
            view.displayDate(SchoolApp.getCurrentDate());
            newDate = view.askNewDate(SchoolApp.getCurrentDate());
        } while (SchoolApp.getCurrentDate() == null);

        res = view.askForAction();
        if (res.getAction() == 'O')
            SchoolApp.setCurrentDate(newDate);
        else
            System.out.println("Try again");
    }
}
