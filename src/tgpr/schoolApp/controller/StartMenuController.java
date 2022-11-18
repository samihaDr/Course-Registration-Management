package tgpr.schoolApp.controller;

import tgpr.schoolApp.view.StartMenuView;
import tgpr.schoolApp.view.View;

public class StartMenuController extends Controller {
    public void run() {
        StartMenuView view = new StartMenuView();
        try {
            View.Action res;
            do {
                view.displayHeader();
                view.displayMenu();
                res = view.askForAction();
                switch (res.getAction()) {
                    case 'L':
                        new LoginController().run();
                        break;
                    case 'S':
                        new SignUpController().run();
                        break;
                    case 'C':
                        new ChangeCurrentDateController().run();
                        break;
                }
            } while (res.getAction() != 'Q');
        } catch (View.ActionInterruptedException e) {
            // just leave
        }
        view.pausedWarning("leaving the application");
        view.close();
    }
}