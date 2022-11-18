package tgpr.schoolApp.view;

import tgpr.schoolApp.model.Course;
import tgpr.schoolApp.model.Question;
import tgpr.schoolApp.model.Quiz;

import java.util.List;

public class TeacherSelectQuizView extends View {
    public void displayHeader() {
        super.displayHeader("Question List");
    }

    public void displayHeaderSelectQuiz(Course course) {
        printf(course.getId() + " - " + course.getCode() + " - " + course.getDescription() + "\n\n");

    }

    public void displayQuizInfo(Quiz quiz) {
        printf(quiz.getTitle() + " - " + quiz.getStart() + " - " + quiz.getFinish() + "\n\n");
    }

    public void displayQuestions(List<Question> questions) {
        int idx = 1;
        for (var question : questions) {
            printf("[%2d] %s\n", idx++, question.getType() + " : " + question.getTitle());
        }
    }

    public boolean askConfirmationDelete(String s) {
        return super.askConfirmationDelete(s);
    }

    public View.Action askForAction(int size, int testParticipantListSize) {
        if (size > 0 && testParticipantListSize > 0)
            return doAskForAction(size, "\n[S#] Select question | [C] Create new question | [U#] Update question | [D#] Delete question | [V] View test participants | [L] Leave\n", "[sS][0-9]+|[cC]+|[uU][0-9]+|[dD][0-9]+|[vV]+|[lL]");
        else if (size > 0 && testParticipantListSize == 0)
            return doAskForAction(size, "\n[S#] Select question | [C] Create new question | [U#] Update question | [D#] Delete question | [L] Leave\n", "[sS][0-9]+|[cC]+|[uU][0-9]+|[dD][0-9]+|[lL]");
        return doAskForAction(size, "\n[C] Create new question | [L] Leave\n", "[cC]+|[lL]");
    }

    public void displayUpdateImpossible(String s) {
        super.pausedWarning("\nImpossible to update question \"" + s + "\" because someone already answered to this question.");
    }
}