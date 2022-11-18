package tgpr.schoolApp.model;

import java.sql.*;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class Student extends User {
    public Student() {
        super();
        setRole(0);
    }

    public Student(String pseudo, String password) {
        super(pseudo, password);
        setRole(0);
    }

    public Student(String pseudo, String password, String fullname, LocalDate birthdate) {
        super(pseudo, password, fullname, birthdate);
        setRole(0);
    }

    @Override
    public String toString() {
        return "Student{" +
                "pseudo='" + pseudo + '\'' +
                ", fullname='" + fullname + '\'' +
                ", birthdate=" + birthdate +
                '}';
    }

    public static List<Student> getAll() {
        var list = new ArrayList<Student>();
        try {
            var stmt = db.prepareStatement("select * from users where role=0 order by pseudo");
            var rs = stmt.executeQuery();
            while (rs.next()) {
                var user = new Student();
                mapper(rs, user);
                list.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public static Student getByPseudo(String pseudo) {
        Student student = null;
        try {
            var stmt = db.prepareStatement("select * from users where pseudo=?");
            stmt.setString(1, pseudo);
            var rs = stmt.executeQuery();
            if (rs.next()) {
                student = new Student();
                mapper(rs, student);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return student;
    }

    public boolean save() {
        Student m = getByPseudo(pseudo);
        int count = 0;
        try {
            PreparedStatement stmt;
            if (m == null) {
                stmt = db.prepareStatement("insert into users (pseudo, password, fullname, birthdate, role) values (?,?,?,?,?)");
                stmt.setString(1, pseudo);
                stmt.setString(2, password);
                stmt.setString(3, fullname.trim());
                stmt.setObject(4, birthdate);
                stmt.setInt(5, role);
            } else {
                stmt = db.prepareStatement("update users set password=?, fullname=?, birthdate=?, role=? where pseudo=?");
                stmt.setString(1, password);
                stmt.setString(2, fullname.trim());
                stmt.setObject(3, birthdate);
                stmt.setInt(4, role);
                stmt.setString(5, pseudo);
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
            PreparedStatement stmt = db.prepareStatement("delete from users where pseudo=?");
            stmt.setString(1, pseudo);
            count = stmt.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return count == 1;
    }

    public static String isValidBirthdate(LocalDate birthdate) {
        if (birthdate == null)
            return null;
        if (birthdate.compareTo(LocalDate.now()) > 0)
            return "may not be born in the future";
        if (Period.between(birthdate, LocalDate.now()).getYears() < 18)
            return "must be 18 years old";
        return null;
    }

    public static String isValidPseudo(String pseudo) {
        if (pseudo == null || !Pattern.matches("[a-zA-Z0-9]{3,128}", pseudo))
            return "invalid pseudo, must contain at least 3 alphanumeric characters";
        return null;
    }

    public static String isValidAvailablePseudo(String pseudo) {
        var error = isValidPseudo(pseudo);
        if (error != null)
            return error;
        if (getByPseudo(pseudo) != null)
            return "not available";
        return null;
    }

    public static String isValidPassword(String password) {
        if (password == null || !Pattern.matches("[a-zA-Z0-9]{3,128}", password))
            return "invalid password, must contain at least 3 alphanumeric characters";
        return null;
    }

    public static String isValidConfirmPassword(String confirmPassword, String password) {
        if (confirmPassword == null || !Pattern.matches("[a-zA-Z0-9]{3,128}", confirmPassword) || !confirmPassword.equals(password))
            return "invalid confirm password, need to match with password";
        return null;
    }

    public static String isValidFullname(String fullname) {
            return isValidString(fullname,"invalid fullname, must begin and end with alphabetic character, whitespace is allowed in between.");
    }

    public List<String> validate() {
        var errors = new ArrayList<String>();
        // field validations
        var err = isValidPseudo(pseudo);
        if (err != null) errors.add(err);
        err = isValidPassword(password);
        if (err != null) errors.add(err);
        err = isValidConfirmPassword(confirmPassword, password);
        if (err != null) errors.add(err);
        err = isValidBirthdate(birthdate);
        if (err != null) errors.add(err);
        err = isValidFullname(fullname);
        if (err != null) errors.add(err);
        return errors;
    }

    public void reload() {
        try {
            var stmt = Model.db.prepareStatement("select * from users where pseudo=?");
            stmt.setString(1, pseudo);
            var rs = stmt.executeQuery();
            if (rs.next()) {
                mapper(rs, this);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static List<Student> getNonRegisteredStudents(int courseId) {
        var studentList = new ArrayList<Student>();
        try {
            var stmt = db.prepareStatement(
                    "select *" +
                            " from users" +
                            " where pseudo not in (select student" +
                            "                      from registrations" +
                            "                      where course = ?)" +
                            " and role = 0");

            stmt.setInt(1, courseId);
            var rs = stmt.executeQuery();
            while (rs.next()) {
                var student = new Student();
                mapper(rs, student);
                studentList.add(student);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return studentList;
    }

    public static boolean subscribeStudent(List<Student> students, String otherStudent, int courseId) {
        int count = 0;
        for (Student student : students) {
            if (student.pseudo.equals(otherStudent)) {
                try {
                    var stmt = db.prepareStatement("Insert into registrations (course, student, active) value (?,?,1)");
                    stmt.setInt(1, courseId);
                    stmt.setString(2, otherStudent);
                    count = stmt.executeUpdate();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                return count == 1;
            }
        }
        return false;
    }
}