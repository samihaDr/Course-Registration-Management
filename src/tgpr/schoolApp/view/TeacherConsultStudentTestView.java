package tgpr.schoolApp.view;

import tgpr.schoolApp.model.Answer;
import tgpr.schoolApp.model.Option;
import tgpr.schoolApp.model.Question;

import java.util.List;

public class TeacherConsultStudentTestView extends View {

    public void displayHeader() {
        super.displayHeader("Student Test");
    }

    public void displayStudent(String pseudo) {
        printf("Student : " + pseudo + "\n\n");
    }

    public void displayQuestion(Question q, int count) {
        printf("%d. %s", count, q.getType() + " - " + q.getTitle() + "\n");
    }

    public boolean displayStudentAnswer(List<Answer> answers, List<Option> options, boolean isCorrectAnswer) {
        printf("\n   Student's answer(s) : ");
        int answeredQuestionCounter = 0;
        int optionCounter = 0;
        for (var option : options) {
            for (var answer : answers) {
                if (option.getId() == answer.getOptionId()) {
                    if (option.isCorrect()) {
                        displayAnswer(option.getTitle() + " (Correct)");
                    } else {
                        displayAnswer(option.getTitle() + " (Incorrect)");
                        isCorrectAnswer = false;
                    }
                } else {
                    answeredQuestionCounter++;
                }
            }
            if (!option.isCorrect()) {
                optionCounter++;
            }
        }
        if (answeredQuestionCounter == options.size() * answers.size()) {
            warning("This question has not been answered.");
            if (optionCounter == options.size()) {
                isCorrectAnswer = true;
            } else {
                isCorrectAnswer = false;
            }
        }
        return isCorrectAnswer;
    }

    public void displayCorrectAnswer(List<Option> options) {
        int incorrectOptionCounter = 0;

        printf("\n\n   The right answer(s) : ");
        for (var option : options) {
            if (option.isCorrect())
                displayAnswer(option.getTitle());
            else
                incorrectOptionCounter++;
        }

        if (incorrectOptionCounter == options.size()) {
            warning("There is no correct answer.");
        }
        printf("\n\n");
    }

    public void displayAnswer(String answer) {
        printf("\n   %s", "-> " + answer);
    }

    public void displayGrade(int grade, int size) {
        printf("Total grade : " + grade + " correct answer(s) out of " + size + " question(s)\n");
    }

    public View.Action askForAction() {
        return doAskForAction(-1, "\n[L] Leave\n", "[lL]");
    }
}
