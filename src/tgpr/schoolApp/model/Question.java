package tgpr.schoolApp.model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class Question extends Model {

    private int id;
    private String title;
    private String type;
    private int quizId;

    public Question() {
    }

    public Question(int id, String title, String type, int quizId) {
        this.id = id;
        this.title = title;
        this.type = type;
        this.quizId = quizId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getQuizId() {
        return quizId;
    }

    public void setQuizId(int quizId) {
        this.quizId = quizId;
    }

    @Override
    public String toString() {
        return "Question{" +
                ", title='" + title + '\'' +
                ", type='" + type + '\'' +
                '}';
    }

    public static void mapper(ResultSet rs, Question question) throws SQLException {
        question.id = rs.getInt("id");
        question.title = rs.getString("title");
        question.type = rs.getString("type");
        question.quizId = rs.getInt("quiz");
    }

    public static List<Question> getAll() {
        var list = new ArrayList<Question>();
        try {
            var stmt = db.prepareStatement("select * from questions order by id");
            var rs = stmt.executeQuery();
            while (rs.next()) {
                var question = new Question();
                mapper(rs, question);
                list.add(question);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public static Question getById(int id) {
        Question question = null;
        try {
            var stmt = db.prepareStatement("select * from questions where id=?");
            stmt.setInt(1, id);
            var rs = stmt.executeQuery();
            if (rs.next()) {
                question = new Question();
                mapper(rs, question);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return question;
    }

    public static List<Question> getByQuiz(int quizId) {
        List<Question> questions = new ArrayList<>();
        try {
            var stmt = db.prepareStatement("select * from questions where quiz = ?");
            stmt.setInt(1, quizId);
            var rs = stmt.executeQuery();
            while (rs.next()) {
                Question question = new Question();
                mapper(rs, question);
                questions.add(question);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return questions;
    }

    public static List<Question> getAnsweredQuestionsByQuizByStudent(int quizId, String student) {
        List<Question> questions = new ArrayList<>();
        try {
            var stmt = db.prepareStatement("select * from questions where id in (select question from options where id in (select option from answers where test in (select id from tests where quiz = ? and student = ?)))");
            stmt.setInt(1, quizId);
            stmt.setString(2,student);
            var rs = stmt.executeQuery();
            while (rs.next()) {
                Question question = new Question();
                mapper(rs, question);
                questions.add(question);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return questions;
    }

    public boolean save() {
        Question q = getById(id);
        int count = 0;
        try {
            PreparedStatement stmt;
            if (q == null) {
                stmt = db.prepareStatement("insert into questions (title, type, quiz) values (?,?,?)", Statement.RETURN_GENERATED_KEYS);
                stmt.setString(1, title);
                stmt.setString(2, type);
                stmt.setInt(3, quizId);
            } else {
                stmt = db.prepareStatement("update questions set title=?, type=?, quiz=? where id=?");
                stmt.setString(1, title);
                stmt.setString(2, type);
                stmt.setInt(3, quizId);
                stmt.setInt(4, id);
            }
            count = stmt.executeUpdate();
            if (q == null)
                id = this.getLastInsertId(stmt);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return count == 1;
    }

    public boolean delete() {
        int count = 0;
        try {
            PreparedStatement stmt = db.prepareStatement("delete from questions where id=?");
            stmt.setInt(1, id);
            count = stmt.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return count == 1;
    }

    public void reload() {
        try {
            var stmt = Model.db.prepareStatement("select * from questions where id=?");
            stmt.setInt(1, id);
            var rs = stmt.executeQuery();
            if (rs.next()) {
                mapper(rs, this);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<String> validate() {
        var errors = new ArrayList<String>();
        // field validations
        var err = isValidTitle(title);
        if (err != null) errors.add(err);
        err = isValidType(type);
        if (err != null) errors.add(err);
        return errors;
    }

    public static String isValidTitle(String title) {
        return isValidQuestion(title, "Invalid title, title can't be blank or contain only numbers, but must end with '?'.");
    }

    public static String isValidType(String type) {
        if (type == null || (!type.equalsIgnoreCase("QCM") && !type.equalsIgnoreCase("QRM"))) {
            return "Invalid type, type must be \"QCM\" or \"QRM\".";
        }
        return null;
    }

    public boolean hasAnswer(List<Answer> answers, List<Option> options) {
        for (var option : options) {
            for (var answer : answers) {
                if (option.getId() == answer.getOptionId())
                    return true;
            }
        }
        return false;
    }
}