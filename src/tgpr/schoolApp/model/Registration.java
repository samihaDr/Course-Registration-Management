package tgpr.schoolApp.model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Registration extends Model {
    private int courseId;
    private String studentId;
    private boolean active;

    public Registration() {
    }

    public Registration(int courseId, String studentId, boolean active) {
        this.courseId = courseId;
        this.studentId = studentId;
        this.active = active;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public boolean getActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    @Override
    public String toString() {
        return "Registration{" +
                "courseId=" + courseId +
                ", studentId=" + studentId +
                ", active=" + active +
                '}';
    }

    public String getStatus() {
        return (active ? "Active" : "Inactive");
    }

    public static void mapper(ResultSet rs, Registration registration) throws SQLException {
        registration.courseId = rs.getInt("course");
        registration.studentId = rs.getString("student");
        registration.active = rs.getBoolean("active");
    }

    public static List<Registration> getAll() {
        var list = new ArrayList<Registration>();
        try {
            var stmt = db.prepareStatement("select * from registrations order by course, student");
            var rs = stmt.executeQuery();
            while (rs.next()) {
                var registration = new Registration();
                mapper(rs, registration);
                list.add(registration);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public static List<Registration> getCoursesByStudentId(String studentId) {
        List<Registration> registrations = new ArrayList<>();
        try {
            var stmt = db.prepareStatement("select * from registrations where student=?");

            stmt.setString(1, studentId);
            var rs = stmt.executeQuery();
            while (rs.next()) {
                var registration = new Registration();
                mapper(rs, registration);
                registrations.add(registration);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return registrations;
    }

    public static Registration getById(int courseId, String studentId) {
        Registration registration = null;
        try {
            var stmt = db.prepareStatement("select * from registrations where course=? and student=?");
            stmt.setInt(1, courseId);
            stmt.setString(2, studentId);
            var rs = stmt.executeQuery();
            if (rs.next()) {
                registration = new Registration();
                mapper(rs, registration);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return registration;
    }

    public static List<Registration> getById(int courseId) {
        List<Registration> registrationList = new ArrayList<>();
        try {
            var stmt = db.prepareStatement("select * from registrations where course=?");
            stmt.setInt(1, courseId);
            var rs = stmt.executeQuery();
            while (rs.next()) {
                var r = new Registration();
                mapper(rs, r);
                registrationList.add(r);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return registrationList;
    }

    public boolean save() {
        Registration m = getById(courseId, studentId);
        int count = 0;
        try {
            PreparedStatement stmt;
            if (m == null) {
                stmt = db.prepareStatement("insert into registrations (course, student, active) values (?,?,?)");
                stmt.setInt(1, courseId);
                stmt.setString(2, studentId);
                stmt.setBoolean(3, active);
            } else {
                stmt = db.prepareStatement("update registrations set active=? where course=? and student=?");
                stmt.setBoolean(1, active);
                stmt.setInt(2, courseId);
                stmt.setString(3, studentId);
            }
            count = stmt.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return count == 1;
    }


    public boolean delete() {
        int count = 0;
        try {
            PreparedStatement stmt1 = db.prepareStatement("delete from registrations where course=? and student=?");
            stmt1.setInt(1, courseId);
            stmt1.setString(2, studentId);

            PreparedStatement stmt2 = db.prepareStatement("delete from tests where student = ? and quiz in (select id from quizzes where course = ?)");
            stmt2.setString(1, studentId);
            stmt2.setInt(2, courseId);

            count = stmt1.executeUpdate();
            stmt2.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return count == 1;
    }

    public Course getCourse() {
        return Course.getById(this.courseId);
    }

    public Student getStudent() {
        return Student.getByPseudo(this.studentId);
    }
}
