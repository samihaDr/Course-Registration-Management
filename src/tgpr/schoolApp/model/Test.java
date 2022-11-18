package tgpr.schoolApp.model;

import tgpr.schoolApp.SchoolApp;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Test extends Model {

    private int id;
    private String studentPseudo;
    private int quizId;
    private LocalDateTime start;

    public Test(String studentPseudo, int quizId, LocalDateTime start) {
        this.studentPseudo = studentPseudo;
        this.quizId = quizId;
        this.start = start;
    }

    public Test() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStudentPseudo() {
        return studentPseudo;
    }

    public void setStudentPseudo(String studentPseudo) {
        this.studentPseudo = studentPseudo;
    }

    public int getQuizId() {
        return quizId;
    }

    public void setQuizId(int quizId) {
        this.quizId = quizId;
    }

    public LocalDateTime getStart() {
        return start;
    }

    public void setStart(LocalDate start) {
        this.start = LocalDateTime.now();
    }

    public static void mapper(ResultSet rs, Test test) throws SQLException {
        test.id = rs.getInt("id");
        test.studentPseudo = rs.getString("student");
        test.quizId = rs.getInt("quiz");
        test.start = rs.getObject("start", LocalDateTime.class);
    }

    public static Test getById(int id) {
        Test test = null;
        try {
            var stmt = db.prepareStatement("select * from tests where id =?");
            stmt.setInt(1, id);
            var rs = stmt.executeQuery();
            if (rs.next()) {
                test = new Test();
                mapper(rs, test);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return test;
    }

    public static List<Test> getByQuiz(int quizId){
        List<Test> testList = new ArrayList<>();
        try{
            var stmt = db.prepareStatement("select * from tests where quiz = ?");
            stmt.setInt(1,quizId);
            var rs = stmt.executeQuery();
            while(rs.next()){
                var test = new Test();
                mapper(rs, test);
                testList.add(test);
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return testList;
    }

    public static List<Test> getAllByStudent(String pseudo) {
        var list = new ArrayList<Test>();
        try {
            var stmt = db.prepareStatement("select * from tests where student=?");
            stmt.setString(1, pseudo);
            var rs = stmt.executeQuery();
            while (rs.next()) {
                var test = new Test();
                mapper(rs, test);
                list.add(test);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public static List<Test> getAll() {
        var listTest = new ArrayList<Test>();
        try {
            var stmt = db.prepareStatement("select * from tests order by id");
            var rs = stmt.executeQuery();
            while (rs.next()) {
                var test = new Test();
                mapper(rs, test);
                listTest.add(test);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listTest;
    }

    public boolean save() {
        Test test = getById(this.id);
        int count = 0;
        try {
            PreparedStatement stmt;
            if (test == null) {
                stmt = db.prepareStatement("Insert into tests (student , quiz, start ) " +
                        "values (?,?,?)", Statement.RETURN_GENERATED_KEYS);
                stmt.setString(1, this.studentPseudo);
                stmt.setInt(2, this.quizId);
                stmt.setObject(3, LocalDateTime.now());
            } else {
                stmt = db.prepareStatement("update tests set student=?, quiz=? where id=? ");
                stmt.setString(1, this.studentPseudo);
                stmt.setInt(2, this.quizId);
                stmt.setInt(3, this.id);
            }

            count = stmt.executeUpdate();
            if (test == null)
                this.id = this.getLastInsertId(stmt);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count == 1;

    }

    public boolean delete() {
        int count = 0;
        try {
            PreparedStatement stmt = db.prepareStatement("delete from tests where id = ?");
            stmt.setInt(1, id);
            count = stmt.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return count == 1;
    }

    public void reload() {
        try {
            var stmt = db.prepareStatement("Select * from tests where id=?");
            stmt.setInt(1, this.id);
            var rs = stmt.executeQuery();
            if (rs.next()) {
                mapper(rs, this);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Test test = (Test) o;
        return id == test.id && quizId == test.quizId && Objects.equals(studentPseudo, test.studentPseudo) && Objects.equals(start, test.start);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, studentPseudo, quizId, start);
    }

    @Override
    public String toString() {
        return "Test{" +
                ", studentPseudo='" + studentPseudo + '\'' +
                ", quizId=" + quizId +
                ", start=" + start +
                '}';
    }
}
