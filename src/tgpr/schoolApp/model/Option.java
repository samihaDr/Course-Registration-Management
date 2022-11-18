package tgpr.schoolApp.model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;

public class Option extends Model {
    private int id;
    private String title;
    private boolean correct;
    private int questionId;

    public Option() {
    }

    public Option(int id, String title, boolean correct, int questionId) {
        this.id = id;
        this.title = title;
        this.correct = correct;
        this.questionId = questionId;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public boolean isCorrect() {
        return correct;
    }

    public int getQuestionId() {
        return questionId;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setCorrect(boolean correct) {
        this.correct = correct;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Option option = (Option) o;
        return id == option.id &&
                correct == option.correct &&
                questionId == option.questionId &&
                Objects.equals(title, option.title);
    }

    @Override
    public String toString() {
        return "Option{" +
                "title='" + title + '\'' +
                ", correct=" + correct +
                ", question=" + questionId +
                '}';
    }

    public static void mapper(ResultSet rs, Option option) throws SQLException {
        option.id = rs.getInt("id");
        option.title = rs.getString("title");
        option.correct = rs.getBoolean("correct");
        option.questionId = rs.getInt("question");


    }

    public static List<Option> getAll() {
        var list = new ArrayList<Option>();
        try {
            var stmt = db.prepareStatement("select * from options order by id");
            var rs = stmt.executeQuery();
            while (rs.next()) {
                var option = new Option();
                mapper(rs, option);
                list.add(option);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public static List<Option> getByIdQuestion(int idQuestion) {
        List<Option> options = new ArrayList<>();
        try {
            var stmt = db.prepareStatement("select * from options where question=?");
            stmt.setInt(1, idQuestion);
            var rs = stmt.executeQuery();
            while (rs.next()) {
                var option = new Option();
                mapper(rs, option);
                options.add(option);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return options;
    }

    public static Option getById(int id) {
        Option option = null;
        try {
            var stmt = db.prepareStatement("select * from options where id=?");
            stmt.setInt(1, id);
            var rs = stmt.executeQuery();
            if (rs.next()) {
                option = new Option();
                mapper(rs, option);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return option;
    }

    public boolean save() {
        Option o = getById(id);
        int count = 0;
        try {
            PreparedStatement stmt;
            if (o == null) {
                stmt = db.prepareStatement("insert into options (title, correct, question) values (?,?,?)", Statement.RETURN_GENERATED_KEYS);
                stmt.setString(1, title);
                stmt.setBoolean(2, correct);
                stmt.setInt(3, questionId);
            } else {
                stmt = db.prepareStatement("update options set title=?, correct=? , question=? where id=?");
                stmt.setString(1, title);
                stmt.setBoolean(2, correct);
                stmt.setInt(3, questionId);
                stmt.setInt(4, id);
            }
            count = stmt.executeUpdate();
            if (o == null)
                id = this.getLastInsertId(stmt);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return count == 1;
    }

    public boolean delete() {
        int count = 0;
        try {
            PreparedStatement stmt = db.prepareStatement("delete from options where id=?");
            stmt.setInt(1, id);
            count = stmt.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return count == 1;
    }


    public static Boolean isValidOptionQcm(Boolean correct, int questionId) {
        boolean found = false;

        var optionsList = Option.getByIdQuestion(questionId);
        if (optionsList.size() != 0 && correct) {
            for (Option o : optionsList) {
                if (o.isCorrect()) {
                    found = true;
                    break;
                }
            }
        }
        return found;
    }

    public static String isValidTitle(String title) {
        return isValidTitleOption(title, "Invalid title, title can't be blank.");
    }

    public static String isValidInputQcm(String correct, int questionId) {
        if (correct == null || (!correct.equalsIgnoreCase("true") && !correct.toString().equalsIgnoreCase("false"))) {
            return "Invalid input, correct must be \"False\" or \"True\".";
        } else if (isValidOptionQcm(Boolean.parseBoolean(correct), questionId)) {
            return "There is already correct answer, input should be False.";
        }
        return null;
    }

    public static String isValidInputQrm(String correct, int questionId) {
        if (correct == null || !correct.equalsIgnoreCase("true") && !correct.toString().equalsIgnoreCase("false")) {
            return "Invalid input, correct must be \"False\" or \"True\".";
        }
        return null;
    }


}
