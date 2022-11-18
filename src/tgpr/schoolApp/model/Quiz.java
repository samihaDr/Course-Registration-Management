package tgpr.schoolApp.model;

import tgpr.schoolApp.SchoolApp;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;

public class Quiz extends Model {
    private int id;
    private String title;
    private LocalDate start;
    private LocalDate finish;
    private int courseId;

    public Quiz() {
    }

    public Quiz(Integer id, String title, LocalDate start, LocalDate finish, int courseId) {
        this.id = id;
        this.title = title;
        this.start = start;
        this.finish = finish;
        this.courseId = courseId;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = capitalizeFirstLetter(title);
    }

    public LocalDate getStart() {
        return start;
    }

    public void setStart(LocalDate start) {
        this.start = start;
    }

    public LocalDate getFinish() {
        return finish;
    }

    public void setFinish(LocalDate finish) {
        this.finish = finish;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    @Override
    public String toString() {
        return "Quiz{" +
                ", title='" + title + '\'' +
                ", start=" + start +
                ", finish=" + finish +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Quiz quiz = (Quiz) o;
        return courseId == quiz.courseId && Objects.equals(title, quiz.title);
    }

    public static void mapper(ResultSet rs, Quiz quiz) throws SQLException {
        quiz.id = rs.getInt("id");
        quiz.title = rs.getString("title");
        quiz.start = rs.getObject("start", LocalDate.class);
        quiz.finish = rs.getObject("finish", LocalDate.class);
        quiz.courseId = rs.getInt("course");
    }

    public static Quiz getById(int id) {
        Quiz quiz = null;
        try {
            var stmt = db.prepareStatement("select * from quizzes where id = ?");
            stmt.setInt(1, id);
            var rs = stmt.executeQuery();
            if (rs.next()) {
                quiz = new Quiz();
                mapper(rs, quiz);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return quiz;
    }

    public static List<Quiz> getByCourseId(int courseId) {
        var listQuiz = new ArrayList<Quiz>();
        try {
            var stmt = db.prepareStatement("select * from quizzes where course = ?");
            stmt.setInt(1, courseId);
            var rs = stmt.executeQuery();
            while (rs.next()) {
                var quiz = new Quiz();
                mapper(rs, quiz);
                listQuiz.add(quiz);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listQuiz;
    }

    public static Quiz getByTitle(String title) {
        //#TODO : possible refactor if quiz title is not unique in quizzes table
        Quiz quiz = null;
        try {
            var stmt = db.prepareStatement("select * from quizzes where title = ?");
            stmt.setString(1, title);
            var rs = stmt.executeQuery();
            if (rs.next()) {
                quiz = new Quiz();
                mapper(rs, quiz);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return quiz;
    }

    public static List<Quiz> getValidQuiz(List<Quiz> quiz) {
        List<Quiz> list= new ArrayList<>();

        for (int i = 0; i < quiz.size(); i++) {
            if (quiz.get(i).start.isBefore(SchoolApp.getCurrentDate()) && quiz.get(i).finish.isAfter(SchoolApp.getCurrentDate()) || quiz.get(i).finish.isBefore(SchoolApp.getCurrentDate()))
               list.add(quiz.get(i));
        }
        return list;
    }

    public boolean save() {
        Quiz quiz = getById(this.id);
        int count = 0;
        try {
            PreparedStatement stmt;
            if (quiz == null) {
                stmt = db.prepareStatement("insert into quizzes (title, start, finish, course) values (?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
                stmt.setString(1, this.title);
                stmt.setObject(2, this.start);
                stmt.setObject(3, this.finish);
                stmt.setInt(4, this.courseId);
            } else {
                stmt = db.prepareStatement("update quizzes set title = ?, start = ?, finish = ?, course = ? where id = ?");
                stmt.setString(1, this.title);
                stmt.setObject(2, this.start);
                stmt.setObject(3, this.finish);
                stmt.setInt(4, this.courseId);
                stmt.setInt(5, this.id);
            }
            count = stmt.executeUpdate();

            if (quiz == null)
                this.id = this.getLastInsertId(stmt);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count == 1;
    }

    public static Boolean existByCourse(String title, int courseId) {
        for (var quiz : Quiz.getByCourseId(courseId)) {
            if (quiz.title.equals(title))
                return true;
        }
        return false;
    }

    public static String isValidTitle(String title, int courseId) {
        if (title == null || !Pattern.matches("[a-zA-Z0-9]{3,}", title) || existByCourse(capitalizeFirstLetter(title), courseId))
            return "Quiz's title must contain at least 3 alphanumeric characters and be unique.";
        return null;
    }

    public static String isValidStartDate(LocalDate start) {
        if (start == null || start.isBefore(SchoolApp.getCurrentDate()))
            return "Invalid quiz's start date, must be after current date app (" + SchoolApp.getCurrentDate() + ").";
        return null;
    }

    public static String isValidEndDate(LocalDate finish) {
        if (finish == null || finish.isBefore(SchoolApp.getCurrentDate()))
            return "Invalid quiz's end date, must be after current date app (" + SchoolApp.getCurrentDate() + ").";
        return null;
    }

    public List<String> validate() {
        var errors = new ArrayList<String>();
        var err = isValidTitle(this.title, this.courseId);
        if (err != null) errors.add(err);
        err = isValidStartDate(this.start);
        if (err != null) errors.add(err);
        err = isValidEndDate(this.finish);
        if (err != null) errors.add(err);

        return errors;
    }

    public boolean delete() {
        int count = 0;

                try {
                    PreparedStatement stmtDel = db.prepareStatement("delete from quizzes where id = ?");
                    stmtDel.setInt(1, this.id);
                    count = stmtDel.executeUpdate();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                return count == 1;
    }

    public boolean hasTest(){
        try {
            PreparedStatement stmt = db.prepareStatement("Select count(*) as countTestByQuiz from tests where quiz=?");
            stmt.setInt(1,this.id);
            var rs = stmt.executeQuery();
            if (rs.next() && rs.getInt("countTestByQuiz") > 0) {
                return true;
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return false;
    }
}