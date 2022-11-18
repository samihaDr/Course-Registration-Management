package tgpr.schoolApp.controller;

import tgpr.schoolApp.model.Course;
import tgpr.schoolApp.model.Quiz;
import tgpr.schoolApp.view.StudentSelectCourseView;
import tgpr.schoolApp.view.View;

public class StudentSelectCourseController extends Controller {
    private final Course course;

    public StudentSelectCourseController(Course course) {
        this.course = course;
    }

    @Override
    public void run() {
        var view= new StudentSelectCourseView(course);
        View.Action res;
        try {
            var list= Quiz.getByCourseId(course.getId());
            do {
                view.displayHeader();
                view.displayCourseInfoStudent(course);
               list= Quiz.getValidQuiz(list);
                view.displayQuiz(list);
                res= view.askForAction(list.size());
                switch (res.getAction()) {
                    case 'P':
                        //Pass Quiz
                        break;

                }
            } while (res.getAction() != 'L');

        } catch (View.ActionInterruptedException e) {

        }
    }
}
