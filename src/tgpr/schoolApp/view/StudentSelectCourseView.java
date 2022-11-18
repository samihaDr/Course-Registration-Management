package tgpr.schoolApp.view;

import tgpr.schoolApp.model.Course;
import tgpr.schoolApp.model.Quiz;
import tgpr.schoolApp.model.User;


import java.util.List;

public class StudentSelectCourseView extends View {
    private final Course course;

    public StudentSelectCourseView(Course course) {
        this.course = course;
    }

    public void displayHeader() {
        displayHeader("Quiz List");
    }

    private void displayOneQuiz(int i, Quiz quiz) {
        printf("[%2d] %s\n", i, quiz.getTitle() + " - " + quiz.getStart() + " - " + quiz.getFinish());
    }

    public void displayQuiz(List<Quiz> quizzes) {
        int i = 1;
        for (var q : quizzes) {
            displayOneQuiz(i, q);
            ++i;
        }
    }

    public View.Action askForAction(int size) {
        return doAskForAction(size, "\n[L] Leave\n", "[lL]");
    }
}
