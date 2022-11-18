package tgpr.schoolApp.model;

import java.sql.*;
import java.util.regex.Pattern;

public abstract class Model {
    protected static Connection db;

    static {
        try {
            db = DriverManager.getConnection("jdbc:mariadb://localhost:3306/tgpr-2122-g01?user=root&password=root");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static boolean checkDb() {
        try {
            if (db == null)
                return false;
            return db.isValid(0);   // 0 pour ne pas utiliser de timeout
        } catch (SQLException e) {
            return false;
        }
    }

    public int getLastInsertId(Statement st) throws SQLException {
        ResultSet keys = st.getGeneratedKeys();
        if (keys.next())
            return keys.getInt(1);
        return 0;
    }

    public static String capitalizeFirstLetter(String s) {
        return s.length() > 0 ? s.substring(0, 1).toUpperCase() + s.substring(1) : null;
    }

    public static String isValidQuestion(String s, String message) {
        if (s == null || !Pattern.matches("^\\d*[a-zA-Z',:=/\\-\\+\\*][\\wèéàùçôûêâî',:=/\\-\\+\\*\\s]*\\?+$", s))
            return message;
        return null;
    }

    public static String isValidString(String s, String message) {
        if (s == null || !Pattern.matches("^\\d*[a-zA-Z][\\wèéàùçôûêâî.',:=/\\-\\+\\s]*", s))
            return message;
        return null;
    }
    public static String isValidTitleOption(String s, String message){
        if(s == null || !Pattern.matches("^\\d*[a-zA-Z0-9][\\wèéàùçôûêâî.',:=/\\-\\+\\s]*", s))
            return message;
        return null;
    }
}