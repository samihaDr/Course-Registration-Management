package tgpr.schoolApp.view;

import tgpr.schoolApp.SchoolApp;

public class StartMenuView extends View {
    public void displayHeader() {
        displayHeader("Start Menu");
    }

    public void displayMenu() {
        println("[L] Login");
        println("\n[S] Sign Up");
        println("\n[C] Change current date" +"   " +"(" +SchoolApp.getCurrentDate()+")");
        println("\n[Q] Quit");
    }

    public Action askForAction() {
        return doAskForAction(-1, "", "[qQ]|[lL]|[sS]|[cC]");
    }
}