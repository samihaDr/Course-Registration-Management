package tgpr.schoolApp.view;

import tgpr.schoolApp.model.Course;
import tgpr.schoolApp.model.Quiz;

import java.util.List;

public class TeacherSelectCourseView extends View {
    public boolean askConfirmationDelete(String s){
       return super.askConfirmationDelete(s);
    }

    public void displayHeader() {
        displayHeader("Quiz List");
    }

    public void displayQuizInfo(Course course) {
        printf(course.getId() + "  -  " + course.getCode() + "   -   " + course.getDescription() + "\n\n");
    }

    private void displayOneQuiz(int i, Quiz quiz) {
        String s1 = quiz.getTitle();
        String s2 = " - " + quiz.getStart();
        String s3 = " - " + quiz.getFinish() + "\n";
        printf("[%2d] %-20s %s%s", i, s1, s2, s3);
    }

    public void displayQuiz(List<Quiz> quizList) {
        int i = 1;
        for (var quiz : quizList) {
            displayOneQuiz(i, quiz);
            i++;
        }
    }

    public View.Action askForAction(int size) {
        return size > 0 ?
                doAskForAction(size, "\n[S#] Select quiz | [C] Create new quiz | [U#] Update quiz | [D#] Delete quiz | [L] Leave\n",
                        "[sS][0-9]+|[cC]+|[uU][0-9]+|[dD][0-9]+|[lL]") :
                doAskForAction(size, "\n[C] Create new quiz | [L] Leave\n",
                        "[cC]+|[lL]");
    }
}