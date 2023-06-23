package org.example.dao;

import org.example.model.Bear;
import org.example.util.UtilConnection;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class BearDao implements Dao <Long, Bear>{

    private final String CREATE_TABLE_BEAR_SQL = """
            CREATE TABLE IF NOT EXISTS bear (
            id BIGINT,
            name VARCHAR(30)
            );
            """;

    private final String DROP_TABLE_BEAR_SQL = """
            DROP TABLE IF EXISTS bear;
            """;

    private final String SAVE_BEAR_SQL = """
            INSERT INTO bear(id, name)
            VALUES ( %d, '%s' )
            """;

    private final String GET_BEAR_SQL = """
            SELECT *
                FROM bear
                WHERE id = %d
            """;

    private final String UPDATE_BEAR_SQL = """
            UPDATE bear
            SET
                id = %d,
                name = '%s'
            WHERE
                id = %d
            """;

    private final String DELETE_BEAR_SQL = """
            DELETE FROM 
                bear
            WHERE 
                id = %d
            """;

    private final Connection connection = UtilConnection.getConnection();

    @Override
    public void createTable() {
        try (var statement = connection.createStatement()) {
            statement.execute(CREATE_TABLE_BEAR_SQL);
            System.out.println("Table bear created");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void dropTable() {
        try (var statement = connection.createStatement()) {

            statement.execute(DROP_TABLE_BEAR_SQL);
            System.out.println("Table bear is deleted");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void save(Bear element) {
        try (var statement = connection.createStatement()) {
            statement.execute(SAVE_BEAR_SQL.formatted(element.getId(), element.getName()));
            System.out.println("Bear save " + element);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Bear get(Long id) {
        try (var statement = connection.createStatement()) {
            var resultSet = statement.executeQuery(GET_BEAR_SQL.formatted(id));
            resultSet.next();

            return new Bear(
              resultSet.getLong("id"),
              resultSet.getString("name"));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(Bear element) {
        try (var statement = connection.createStatement()) {
            final var sql = UPDATE_BEAR_SQL.formatted(
                    element.getId(),
                    element.getName(),
                    element.getId());

            statement.execute(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteId(Long id) {
        try (var statement = connection.createStatement()) {
            final var sql = DELETE_BEAR_SQL.formatted(id);

            statement.execute(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
