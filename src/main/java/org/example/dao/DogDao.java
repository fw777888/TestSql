package org.example.dao;

import org.example.model.Dog;
import org.example.util.UtilConnection;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DogDao {
    private final String CREATE_TABLE_DOG_SQL = """
            CREATE TABLE IF NOT EXISTS dog (
            id BIGINT,
            name VARCHAR(30),
            is_home boolean 
            );
            """;
    private final String DROP_TABLE_DOG_SQL = """
            DROP TABLE IF EXISTS dog;
            """;

    private  final String SAVE_DOG_SQL = """
            INSERT INTO dog(id, name, is_home)
            VALUES ( %d, %s, %b )
            """;
    private final String GET_DOG_SQL = """
            SELECT *
            FROM dog
            WHERE id = %d
            """;

    private final Connection connection = UtilConnection.getConnection();

    public void createTable() {

        try (var statement = connection.createStatement()) {

            statement.execute(CREATE_TABLE_DOG_SQL);
            System.out.println("Table dog created");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void dropTable() {
        try (var statement = connection.createStatement()) {
            statement.execute(DROP_TABLE_DOG_SQL);
            System.out.println("Table dog deleted");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void saveDog(Dog dog) {
        try (var statement = connection.createStatement()) {

            statement.execute(SAVE_DOG_SQL.formatted(dog.getId(), dog.getName(), dog.isHome()));
            System.out.println("Dog save " + dog);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Dog getDog(long id) {

        try (var statement = connection.createStatement()) {

            var resultSet = statement.executeQuery(GET_DOG_SQL.formatted(id));
            resultSet.next();

            return new Dog(
                    resultSet.getLong("id"),
                    resultSet.getString("name"),
                    resultSet.getBoolean("is_home"));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
