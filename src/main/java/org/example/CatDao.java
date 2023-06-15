package org.example;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class CatDao {

    private static Connection connection = null;
    private static final String CREATE_TABLE_CAT_SQL = """
            CREATE TABLE IF NOT EXISTS cats (
                index BIGINT, 
                name VARCHAR(30)
            );
            """;

    private final String DROP_TABLE_CAT_SQL = """
                DROP TABLE IF EXISTS cats;
            """;

    private final String SAVE_CAT_SQL = """
            INSERT INTO cats(index, name)
            VALUES (%d, '%s')
            """;

    private final String GET_CAT_SQL = """
           SELECT * 
            FROM cats 
            WHERE index = %d 
            """;


    public static void createTable() {

        if (connection == null) {
            connection = connectionInitialization();
        }

        try (final var statement = connection.createStatement()) {

            statement.execute(CREATE_TABLE_CAT_SQL);

            System.out.println("Table cat created!");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void dropTable() {
        if (connection == null) {
            connection = connectionInitialization();
        }

        try (final var statement = connection.createStatement()) {

            statement.execute(DROP_TABLE_CAT_SQL);

            System.out.println("Table cat deleted!");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void saveCat(Cat cat) {
        if (connection == null) {
            connection = connectionInitialization();
        }

        // Statement -> statement(sql) -> {BD}
        // PreparedStatement(sql) -> sql(? -> value) -> [sql, sql, sql] -> {BD}
        try (final var statement = connection.createStatement()) {

            statement.execute(SAVE_CAT_SQL.formatted(cat.id, cat.name));

            System.out.println("Cat save! " + cat);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Cat getCat(long id) {
        if(connection != null) {
            connection = connectionInitialization();
        }
        try (final var statement = connection.createStatement()) {
            var resultSet = statement.executeQuery(GET_CAT_SQL.formatted(id));
            resultSet.next();
            return new Cat(resultSet.getLong("index"), resultSet.getString("name"));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    private static Connection connectionInitialization() {
        try {
            return DriverManager.getConnection("jdbc:h2:mem:user", "", "");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}