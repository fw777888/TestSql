package org.example.dao;

import lombok.extern.log4j.Log4j;
import org.example.model.Dog;
import org.example.util.UtilConnection;

import java.sql.Connection;
import java.sql.SQLException;

@Log4j
public class DogDao implements Dao<Long, Dog> {
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
            VALUES ( ?, ?, ? )
            """;
    private final String GET_DOG_SQL = """
            SELECT *
            FROM 
                dog
            WHERE 
                id = ?
            """;

    private final String UPDATE_DOG_SQL = """
            UPDATE 
                dog
            SET
                id = ?,
                name = ?,
                is_home = ?,
            WHERE 
                id = ?
            """;
    private final String DELETE_DOG_SQL = """
            DELETE FROM 
                dog
            WHERE 
                id = ?
            """;

    private final Connection connection = UtilConnection.getConnection();

    @Override
    public void createTable() {

        try (final var statement = connection.createStatement()) {

            statement.execute(CREATE_TABLE_DOG_SQL);
            log.info("Table dog created");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void dropTable() {
        try (final var statement = connection.createStatement()) {
            statement.execute(DROP_TABLE_DOG_SQL);
            log.info("Table dog deleted");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void save(Dog element) {
        try (var preparedStatement = connection.prepareStatement(SAVE_DOG_SQL)) {
            preparedStatement.setLong(1, element.getId());
            preparedStatement.setString(2, element.getName());
            preparedStatement.setBoolean(3, element.isHome());
            preparedStatement.execute();
            log.info("Dog save ");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Dog get(Long id) {
        Dog dog = null;

        try (var preparedStatement = connection.prepareStatement(GET_DOG_SQL)) {
            preparedStatement.setLong(1, id);

            final var resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                dog = Dog.builder()
                        .id(resultSet.getLong("id"))
                        .name(resultSet.getString("name"))
                        .isHome(resultSet.getBoolean("isHome"))
                        .build();
                log.info("dog return");
            } else {
                throw new RuntimeException("The Dog is not exist");
            }
            return dog;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(Dog element) {

        try (var preparedStatement = connection.prepareStatement(UPDATE_DOG_SQL)){
            preparedStatement.setLong(1, element.getId());
            preparedStatement.setString(2, element.getName());
            preparedStatement.setBoolean(3, element.isHome());
            preparedStatement.setLong(4, element.getId());
            preparedStatement.execute();
            log.info("Dog is updated");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteId(Long id) {
       try (var preparedStatement = connection.prepareStatement(DELETE_DOG_SQL)){
            preparedStatement.setLong(1, id);
            preparedStatement.execute();
            log.info("Dog deleted");
        } catch (SQLException e) {
           throw new RuntimeException(e);
       }
    }
}
