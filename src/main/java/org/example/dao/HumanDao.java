package org.example.dao;

import lombok.extern.log4j.Log4j;
import org.example.model.Human;
import org.example.util.UtilConnection;

import java.sql.*;

@Log4j
public class HumanDao implements Dao<Long, Human> {

    private final String CREATE_TABLE_HUMAN_SQL = """
            CREATE TABLE IF NOT EXISTS human (
                id BIGINT,
                name VARCHAR(30),
                last_name VARCHAR(30)
            );
            """;

    private final String DROP_TABLE_HUMAN_SQL = """
            DROP TABLE IF EXISTS human
            """;
    private final String GET_HUMAN_SQL = """
            SELECT *
            FROM 
                human
            WHERE 
                id = ?
            """;

    private final String SAVE_HUMAN_SQL = """
            INSERT INTO human(id, name, last_name)
            VALUES ( ?, ?, ?)
            """;

    private final String UPDATE_HUMAN_SQL = """
            UPDATE 
                human
            SET 
                id = ?,
                name = ?,
                last_name = ?
            WHERE 
                id = ?        
            """;

    private final String DELETE_HUMAN_SQL = """
            DELETE FROM 
                human
            WHERE  
                id = ?           
            """;

    private final Connection connection = UtilConnection.getConnection();

    @Override
    public void createTable() {
        try (final var statement = connection.prepareStatement(CREATE_TABLE_HUMAN_SQL)) {
            statement.execute();
            log.info("Table human is created");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void dropTable() {
        try (final var statement = connection.prepareStatement(DROP_TABLE_HUMAN_SQL)) {
            statement.execute();
            log.info("Table human is deleted");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void save(Human element) {
        try (var preparedStatement = connection.prepareStatement(SAVE_HUMAN_SQL)) {
            preparedStatement.setLong(1, element.getId());
            preparedStatement.setString(2, element.getName());
            preparedStatement.setString(3, element.getLastName());
            preparedStatement.execute();
            log.info("human save!");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

//        try (final var statement = connection.createStatement()) {
//            statement.execute(SAVE_HUMAN_SQL.formatted(
//                    element.getId(),
//                    element.getName(),
//                    element.getLastName()));
//
//            System.out.println("Human save " + element);
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
    }

    //    SELECT *
//    FROM
//            human
//    WHERE
//    id = 2
    @Override
    public Human get(Long id) { //2
        Human human = null;

        try (var preparedStatement = connection.prepareStatement(GET_HUMAN_SQL)) {
            preparedStatement.setLong(1, id);

            final var resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                human = Human.builder()
                        .id(resultSet.getLong("id"))
                        .name(resultSet.getString("name"))
                        .lastName(resultSet.getString("last_name"))
                        .build();
                log.info("user return!");
            } else {
                throw new RuntimeException("Такого пользователя нет!");
            }
            return human;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(Human element) {
        try (var preparedStatement = connection.prepareStatement(UPDATE_HUMAN_SQL)) {
            preparedStatement.setLong(1, element.getId());
            preparedStatement.setString(2, element.getName());
            preparedStatement.setString(3, element.getLastName());
            preparedStatement.setLong(4, element.getId());
            preparedStatement.execute();
            log.info("user update!");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteId(Long id) {
        try (var preparedStatement = connection.prepareStatement(DELETE_HUMAN_SQL)) {
            preparedStatement.setLong(1, id);
            preparedStatement.execute();
            log.info("user deleted!");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}