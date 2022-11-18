package tgpr.schoolApp.view;

import tgpr.schoolApp.model.Option;
import tgpr.schoolApp.model.Question;

import java.util.List;

public class TeacherSelectQuestionView extends View {
    public void displayHeaderSelectQuestion() {
        displayHeader("Option List");
    }

    public void displayOptions(List<Option> Options) {
        int idx = 1;
        for (var option : Options) {
            printf("[%2d] %s\n", idx++, option.getTitle() + " : " + (option.isCorrect() ? "Correct" : "Incorrect"));
        }
    }

    public boolean askConfirmationDelete(String s) {
        return super.askConfirmationDelete(s);
    }

    public View.Action askForAction(int size, boolean hasAnswers) {

        if (size > 0 && !hasAnswers)
            return doAskForAction(size, "\n[C] Create option | [U#] Update option | [D#] Delete option | [L] Leave\n", "[cC]+|[uU][0-9]+|[dD][0-9]+|[lL]");
        else if (hasAnswers && size > 0)
            return doAskForAction(size, "\n[L] Leave\n", "[lL]");
        else
            return doAskForAction(size, "\n[C] Create option | [L] Leave\n", "[cC]+|[lL]");
    }

}
