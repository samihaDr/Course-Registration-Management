package tgpr.schoolApp.model;

import tgpr.schoolApp.controller.ChangeCurrentDateController;
import tgpr.schoolApp.view.ChangeCurrentDateView;
import tgpr.schoolApp.view.View;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Period;
import java.util.Objects;
import java.util.regex.Pattern;

public abstract class User extends Model {
    public String pseudo;
    public String password;
    public String confirmPassword;
    public String fullname;
    public LocalDate birthdate;
    public int role;

    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public void setBirthdate(LocalDate birthdate) {
        this.birthdate = birthdate;
    }

    public void setRole(int role) {
        this.role = role;
    }

    public int getRole() {
        return role;
    }

    public String getPseudo() {
        return pseudo;
    }

    public String getPassword() {
        return password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public String getFullname() {
        return fullname;
    }

    public LocalDate getBirthdate() {
        return birthdate;
    }

    public static void mapper(ResultSet rs, User user) throws SQLException {
        user.pseudo = rs.getString("pseudo");
        user.password = rs.getString("password");
        user.fullname = rs.getString("fullname");
        user.birthdate = rs.getObject("birthdate", LocalDate.class);
        user.role = rs.getInt("role");
    }

    @Override
    public String toString() {
        return "User{" +
                "pseudo='" + pseudo + '\'' +
                ", password='" + password + '\'' +
                ", fullname='" + fullname + '\'' +
                ", birthdate=" + birthdate +
                ", role=" + role +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(pseudo, user.pseudo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(pseudo, password, fullname, birthdate, role);
    }

    public User(String pseudo, String password, String fullname, LocalDate birthdate) {
        this.pseudo = pseudo;
        this.password = password;
        this.fullname = fullname;
        this.birthdate = birthdate;
    }

    public User(String pseudo, String password) {
        this.pseudo = pseudo;
        this.password = password;
    }

    public User() {
    }

    public void reload() {
        try {
            var stmt = db.prepareStatement("select * from users where pseudo = ?");
            stmt.setString(1, pseudo);
            var rs = stmt.executeQuery();
            if (rs.next()) {
                mapper(rs, this);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean delete() {
        int count = 0;
        try {
            PreparedStatement stmt = db.prepareStatement("delete from users where pseudo = ?");
            stmt.setString(1, pseudo);
            count = stmt.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return count == 1;
    }

    public static User getByPseudo(String pseudo) {
        User user = null;
        try {
            var stmt = db.prepareStatement("select * from users where pseudo = ?");
            stmt.setString(1, pseudo);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                if (rs.getInt("role") == 0) {
                    user = new Student();
                    Student.mapper(rs, user);
                }
                if (rs.getInt("role") == 1) {
                    user = new Teacher();
                    Teacher.mapper(rs, user);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    public static String isValidNewDate(LocalDate newDate) {
        if (newDate == null)
            return null;
        if (newDate.compareTo(LocalDate.now()) < 0)
            return "invalid date";

        return null;
    }
}
