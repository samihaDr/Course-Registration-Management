package tgpr.schoolApp.model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;

public class Course extends Model {
    private int id;
    private String code;
    private String description;
    private int capacity;
    String pseudo;

    public Course() {
    }

    public Course(int id, String code, String description, int capacity, String pseudo) {
        this.id = id;
        this.code = code;
        this.description = description;
        this.capacity = capacity;
        this.pseudo = pseudo;
    }

    public int getId() {
        return id;
    }

    public String getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    public int getCapacity() {
        return capacity;
    }

    public String getPseudo() {
        return pseudo;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Course course = (Course) o;
        return id == course.id &&
                capacity == course.capacity &&
                Objects.equals(code, course.code) &&
                Objects.equals(description, course.description) &&
                Objects.equals(pseudo, course.pseudo);
    }

    public static List<Course> getAll() {
        var list = new ArrayList<Course>();
        try {
            var stmt = db.prepareStatement("select * from courses order by code");
            var rs = stmt.executeQuery();
            while (rs.next()) {
                var course = new Course();
                mapper(rs, course);
                list.add(course);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public static void mapper(ResultSet rs, Course course) throws SQLException {
        course.id = rs.getInt("id");
        course.code = rs.getString("code");
        course.description = rs.getString("description");
        course.capacity = rs.getInt("capacity");
        course.pseudo = rs.getString("teacher");
    }

    public boolean hasCapacity(int size) {
        return getById(id).capacity > size;
    }

    public boolean hasCapacity() {
        try {
            var stmt = db.prepareStatement("select count(*) as countRegistration from registrations where course = ?");
            stmt.setInt(1, id);
            var rs = stmt.executeQuery();
            if (rs.next()) {
                int registration = rs.getInt("countRegistration");
                return hasCapacity(registration);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static Course getById(int id) {
        Course course = null;
        try {
            var stmt = db.prepareStatement("select * from courses where id=?");
            stmt.setInt(1, id);
            var rs = stmt.executeQuery();
            if (rs.next()) {
                course = new Course();
                mapper(rs, course);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return course;
    }

    public static List<Course> getByTeacher(String teacher) {
        var list = new ArrayList<Course>();
        try {
            var stmt = db.prepareStatement("select * from courses where teacher=?");
            stmt.setString(1, teacher);
            var rs = stmt.executeQuery();
            while (rs.next()) {
                var course = new Course();
                mapper(rs, course);
                list.add(course);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public static List<Course> getRegistrationCoursesByStudent(String student) {
        var courseList = new ArrayList<Course>();
        try {
            var stmt = db.prepareStatement("select * from courses where id in (select course from registrations where student = ?)");
            stmt.setString(1, student);
            var rs = stmt.executeQuery();

            while (rs.next()) {
                var course = new Course();
                mapper(rs, course);
                courseList.add(course);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return courseList;
    }

    public static List<Course> getActiveByStudent(String student) {
        var courseList = new ArrayList<Course>();
        try {
            var stmt = db.prepareStatement("select id, code, description, capacity, teacher from courses, registrations where id = course and active = 1 and student = ?");
            stmt.setString(1, student);
            var rs = stmt.executeQuery();

            while (rs.next()) {
                var course = new Course();
                mapper(rs, course);
                courseList.add(course);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return courseList;
    }

    //Méthode qui va chercher la liste des cours auquels l'etudiant n'est pas inscrit
    public static List<Course> getAvailableCourses(String student) {
        var courseList = new ArrayList<Course>();
        try {
            var stmt = db.prepareStatement("Select * FROM courses c WHERE c.id Not In (select r.course from registrations r where r.student = ?)");
            stmt.setString(1, student);
            var rs = stmt.executeQuery();
            while (rs.next()) {
                var course = new Course();
                mapper(rs, course);
                if (course.hasCapacity())
                    courseList.add(course);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return courseList;
    }

    public boolean save() {
        Course c = getById(id);
        int count = 0;
        try {
            PreparedStatement stmt;
            if (c == null) {
                stmt = db.prepareStatement("insert into courses (id,code, description, capacity, teacher) values (?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
                stmt.setInt(1, id);
                stmt.setString(2, code);
                stmt.setString(3, description);
                stmt.setInt(4, capacity);
                stmt.setString(5, pseudo);
            } else {
                stmt = db.prepareStatement("update courses set code =?, description=?, capacity=?, teacher=? where id=?");
                stmt.setString(1, code);
                stmt.setString(2, description);
                stmt.setInt(3, capacity);
                stmt.setString(4, pseudo);
                stmt.setInt(5, id);
            }
            count = stmt.executeUpdate();
            if (c == null)
                id = this.getLastInsertId(stmt);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return count == 1;
    }

    public boolean delete() {
        int count = 0;
        try {
            PreparedStatement stmt = db.prepareStatement("delete from courses where id=?");
            stmt.setInt(1, id);
            count = stmt.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return count == 1;
    }

    public void reload() {
        try {
            var stmt = Model.db.prepareStatement("select * from courses where id=?");
            stmt.setInt(1, id);
            var rs = stmt.executeQuery();
            if (rs.next()) {
                mapper(rs, this);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static String isValidCapacity(int capacity, int courseId) {
        int currentSubscribedStudent = Registration.getById(courseId).size();
        if (capacity < 0 || capacity < currentSubscribedStudent)
            return "the capacity cannot be less than " + currentSubscribedStudent;
        return null;
    }

    public static String isValidCode(String code) {
        if (code == null || !Pattern.matches("[a-zA-Z0-9]{4}", code))
            return "invalid code";
        return null;
    }

    public static String isValidDescription(String description) {
        if (description == null || description.trim().length() == 0)
            return "invalid description";
        return null;
    }

    public static String isValidId(int id) {
        if (id < 1000 || id > 9999) {
            return "invalid Id";
        }
        return null;
    }

    public List<String> validate() {
        var errors = new ArrayList<String>();

        var err = isValidId(id);
        if (err != null) errors.add(err);
        err = isValidCode(code);
        if (err != null) errors.add(err);
        err = isValidDescription(description);
        err = isValidCapacity(capacity, this.id);
        if (err != null) errors.add(err);

        return errors;
    }

    public static String isValidAvailableId(int id) {
        var error = isValidId(id);
        if (error != null)
            return error;
        if (getById(id) != null)
            return "course already exist";
        return null;
    }

    @Override
    public String toString() {
        return "Course{" +
                "code='" + code + '\'' +
                ", description='" + description + '\'' +
                ", capacity=" + capacity +
                ", teacher=" + pseudo +
                '}';
    }
}