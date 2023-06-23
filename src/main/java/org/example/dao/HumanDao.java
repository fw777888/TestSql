package org.example.dao;

import org.example.model.Human;
import org.example.util.UtilConnection;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

//DZ
public class HumanDao implements Dao <Long, Human>{

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
                id = %d
            """;
    private final String SAVE_HUMAN_SQL = """
            INSERT INTO human(id, name, last_name)
            VALUES ( %d, '%s', '%s')
            """;
    private final String UPDATE_HUMAN_SQL = """
            UPDATE 
                human
            SET 
                id = %d,
                name = '%s',
                last_name = '%s'
            WHERE 
                id = %d        
            """;
    private final String DELETE_HUMAN_SQL = """
            DELETE FROM 
                human
            WHERE  
                id = %d           
            """;

    private final Connection connection = UtilConnection.getConnection();

    @Override
    public void createTable() {

        try (final var statement = connection.createStatement()) {
            statement.execute(CREATE_TABLE_HUMAN_SQL);

            System.out.println("Table human is created");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void dropTable() {

        try (final var statement = connection.createStatement()) {
            statement.execute(DROP_TABLE_HUMAN_SQL);

            System.out.println("Table human is deleted");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void save(Human element) {

        try (final var statement = connection.createStatement()) {
            statement.execute(SAVE_HUMAN_SQL.formatted(
                    element.getId(),
                    element.getName(),
                    element.getLastName()));

            System.out.println("Human save " + element);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Human get(Long id) {
        try (final var statement = connection.createStatement()) {
            var resultSet = statement.executeQuery(GET_HUMAN_SQL.formatted(id));
            resultSet.next();
            return new Human(
                    resultSet.getLong("id"),
                    resultSet.getString("name"),
                    resultSet.getString("last_name"));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(Human element) {

        try (var statement = connection.createStatement()) {

            final var sqlUpdate = UPDATE_HUMAN_SQL.formatted(
                    element.getId(),
                    element.getName(),
                    element.getLastName(),
                    element.getId());

            statement.execute(sqlUpdate);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

   @Override
    public void deleteId(Long id) {

        try (var statement = connection.createStatement()) {

            final var sqlDelete = DELETE_HUMAN_SQL.formatted(id);
            statement.execute(sqlDelete);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}