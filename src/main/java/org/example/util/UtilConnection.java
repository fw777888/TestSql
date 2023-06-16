package org.example.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class UtilConnection {

    static {
        CONNECTION = connectionInitialization();
    }

    private static final Connection CONNECTION;
    private static Connection connectionInitialization() {

        try {
            return DriverManager.getConnection("jdbc:h2:mem:user", "", "");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static Connection getConnection() {
        return CONNECTION;
    }
}
