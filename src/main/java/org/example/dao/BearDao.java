package org.example.dao;

import lombok.extern.log4j.Log4j;
import org.example.model.Bear;
import org.example.util.UtilConnection;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

@Log4j
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
            VALUES ( ?, ? )
            """;

    private final String GET_BEAR_SQL = """
            SELECT *
                FROM bear
                WHERE id = ?
            """;

    private final String UPDATE_BEAR_SQL = """
            UPDATE bear
            SET
                id = ?,
                name = ?
            WHERE
                id = ?
            """;

    private final String DELETE_BEAR_SQL = """
            DELETE FROM 
                bear
            WHERE 
                id = ?
            """;

    private final Connection connection = UtilConnection.getConnection();

    @Override
    public void createTable() {
        try (final var statement = connection.prepareStatement(CREATE_TABLE_BEAR_SQL)) {
            statement.execute();
            log.info("Table bear created");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void dropTable() {
        try (final var statement = connection.prepareStatement(DROP_TABLE_BEAR_SQL)) {

            statement.execute();
            log.info("Table bear is deleted");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void save(Bear element) {
        try (var statement = connection.prepareStatement(SAVE_BEAR_SQL)) {
            statement.setLong(1, element.getId());
            statement.setString(2, element.getName());
            statement.execute();
            log.info("Bear save " + element);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Bear get(Long id) {
        Bear bear = null;

        try (var statement = connection.prepareStatement(GET_BEAR_SQL)) {
            statement.setLong(1, id);

            final var resultSet = statement.executeQuery();

            if (resultSet.next()) {
                bear = Bear.builder()
                        .id(resultSet.getLong("id"))
                        .name(resultSet.getString("name"))
                        .build();
                log.info("bear return");
            } else {
                throw new RuntimeException("The bear is not exists");
            }
            return bear;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(Bear element) {
        try (var statement = connection.prepareStatement(UPDATE_BEAR_SQL)) {
            statement.setLong(1, element.getId());
            statement.setString(2, element.getName());
            statement.setLong(3, element.getId());
            statement.execute();
            log.info("Bear is updated");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteId(Long id) {
        try (var statement = connection.prepareStatement(DELETE_BEAR_SQL)) {
            statement.setLong(1, id);
            statement.execute();
            log.info("Bear is deleted");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
