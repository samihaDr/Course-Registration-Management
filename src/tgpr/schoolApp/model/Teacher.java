package tgpr.schoolApp.model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class Teacher extends User {
    public Teacher(String pseudo, String password, String fullname, LocalDate birthdate) {
        super(pseudo, password, fullname, birthdate);
        setRole(1);
    }

    public Teacher(String pseudo, String password) {
        super(pseudo, password);
        setRole(1);
    }

    public Teacher() {
        super();
        setRole(1);
    }

    public static List<Teacher> getAll() {
        var teacherList = new ArrayList<Teacher>();
        try {
            var stmt = db.prepareStatement("select * from users where role = 1");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                var t = new Teacher();
                User.mapper(rs, t);
                teacherList.add(t);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return teacherList;
    }

    public static Teacher getByPseudo(String pseudo) {
        Teacher teacher = null;
        try {
            var stmt = db.prepareStatement("select * from users where pseudo = ?");
            stmt.setString(1, pseudo);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                teacher = new Teacher();
                mapper(rs, teacher);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return teacher;
    }

    public boolean save() {
        Teacher m = getByPseudo(pseudo);
        int count = 0;
        try {
            PreparedStatement stmt;
            if (m == null) {
                stmt = db.prepareStatement("insert into users (pseudo, password, fullname, birthdate, role) values (?,?,?,?,?)");
                stmt.setString(1, pseudo);
                stmt.setString(2, password);
                stmt.setString(3, fullname);
                stmt.setObject(4, birthdate);
                stmt.setInt(5, role);
            } else {
                stmt = db.prepareStatement("update users set password = ?, fullname = ?, birthdate = ? where pseudo = ? and role = 1");
                stmt.setString(1, password);
                stmt.setString(2, fullname);
                stmt.setObject(3, birthdate);
                stmt.setString(4, pseudo);
            }
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
        if (pseudo == null || !Pattern.matches("[a-zA-Z0-9]{3,}", pseudo))
            return "invalid pseudo";
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
        if (password == null || !Pattern.matches("[a-zA-Z0-9]{3,}", password))
            return "invalid password";
        return null;
    }

    public List<String> validate() {
        var errors = new ArrayList<String>();

        // field validations
        var err = isValidPseudo(pseudo);
        if (err != null) errors.add(err);
        err = isValidPassword(password);
        if (err != null) errors.add(err);
        err = isValidBirthdate(birthdate);
        if (err != null) errors.add(err);

        return errors;
    }
}