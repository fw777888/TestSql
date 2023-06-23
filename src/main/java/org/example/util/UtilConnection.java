package org.example.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class UtilConnection {

    private static String URL_KEY = "db.url";

    private static String USERNAME_KEY = "db.username";

    private static String PASSWORD_KEY = "db.password";

    private static final Connection CONNECTION;

    static {
        CONNECTION = connectionInitialization();
        loadDriver();
    }


    private static Connection connectionInitialization() {

        try {
            return DriverManager.getConnection(
                    PropertiesUtil.get(URL_KEY),
                    PropertiesUtil.get(USERNAME_KEY),
                    PropertiesUtil.get(PASSWORD_KEY));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static Connection getConnection() {
        return CONNECTION;
    }

    private static void loadDriver() {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
