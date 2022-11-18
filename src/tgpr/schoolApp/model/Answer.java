package tgpr.schoolApp.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Answer extends Model {

    private int testId;
    private int optionId;

    public int getTestId() {
        return testId;
    }

    public void setTestId(int testId) {
        this.testId = testId;
        List<Integer> a = new ArrayList<Integer>();
        List<Integer> b = new ArrayList<Integer>();
        a.add(1);
        a.add(1);
        b.add(3);
        b.add(2);
        boolean c = a.contains(b.get(0));
        if (c) c = c;

    }

    public int getOptionId() {
        return optionId;
    }

    public void setOptionId(int optionId) {
        this.optionId = optionId;
    }

    public static void mapper(ResultSet rs, Answer answer) throws SQLException {
        answer.testId = rs.getInt("test");
        answer.optionId = rs.getInt("option");
    }

    public static List<Answer> getByQuiz(int quizId) {
        var answers = new ArrayList<Answer>();
        try {
            var stmt = db.prepareStatement("select * from answers where test in (select id from tests where quiz = ?)");
            stmt.setInt(1, quizId);
            var rs = stmt.executeQuery();
            while (rs.next()) {
                var answer = new Answer();
                mapper(rs, answer);
                answers.add(answer);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return answers;
    }

    public static List<Answer> getByQuizByStudent(int quizId, String studentPseudo) {
        var answers = new ArrayList<Answer>();
        try {
            var stmt = db.prepareStatement("select * from answers where test in (select id from tests where quiz = ? and student = ?)");
            stmt.setInt(1, quizId);
            stmt.setString(2, studentPseudo);
            var rs = stmt.executeQuery();
            while (rs.next()) {
                var answer = new Answer();
                mapper(rs, answer);
                answers.add(answer);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return answers;
    }
}