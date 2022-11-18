package tgpr.schoolApp.controller;

import tgpr.schoolApp.model.Course;
import tgpr.schoolApp.model.Quiz;
import tgpr.schoolApp.model.Test;
import tgpr.schoolApp.view.TeacherTestParticipantListView;
import tgpr.schoolApp.view.View;

public class TeacherTestParticipantListController extends Controller{
    Quiz quiz;
    TeacherTestParticipantListView view = new TeacherTestParticipantListView();
    public TeacherTestParticipantListController(Quiz quiz){
        this.quiz = quiz;
    }

    @Override
    public void run() {
        try {
            View.Action res;
            do {
                var participantList = Test.getByQuiz(quiz.getId());
                var course = Course.getById(quiz.getCourseId());
                view.displayHeader();
                view.displayCourseInfoTeacher(course);
                view.displayQuizInfo(quiz);
                view.displayTestParticipantList(participantList);
                res = view.askForAction(participantList.size());

                switch (res.getAction()) {
                    case 'C': // consult student test
                        var test = participantList.get(res.getNumber() -1);
                        new TeacherConsultStudentTestController(quiz,test).run();
                        break;
                }
            }while(res.getAction() != 'L');
        } catch (View.ActionInterruptedException e){
        }
    }
}
