package org.example.dao;

import lombok.Builder;
import lombok.extern.log4j.Log4j;
import org.example.model.Cat;
import org.example.util.UtilConnection;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

// CRUD - Create Read Update Delete
@Log4j
public class CatDao implements Dao<Long, Cat> {

    private final String CREATE_TABLE_CAT_SQL = """
            CREATE TABLE IF NOT EXISTS cat (
                id BIGINT, 
                name VARCHAR(30)
            );
            """;

    private final String DROP_TABLE_CAT_SQL = """
                DROP TABLE IF EXISTS cat;
            """;

    private final String SAVE_CAT_SQL = """
            INSERT INTO cat(id, name)
            VALUES (?, ?)
            """;

    private final String GET_CAT_SQL = """
           SELECT * 
            FROM cat 
            WHERE id = ? 
            """;

    private final String UPDATE_CAT_SQL = """
            UPDATE cat
            SET
                id = ?,
                name = ? 
            WHERE
                id = ?
            """;

    private final String DELETE_CAT_SQL = """
            DELETE FROM 
                cat
            WHERE
                id = ?
            """;

    private final Connection connection = UtilConnection.getConnection();

    @Override
    public void createTable() {

        try (final var statement = connection.prepareStatement(CREATE_TABLE_CAT_SQL)) {
            statement.execute();

            log.info("Table cat created!");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void dropTable() {

        try (final var statement = connection.prepareStatement(DROP_TABLE_CAT_SQL)) {

            statement.execute();
            System.out.println("Table cat deleted!");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void save(Cat element) {

        try (final var statement = connection.prepareStatement(SAVE_CAT_SQL)) {
            statement.setLong(1, element.getId());
            statement.setString(2, element.getName());
            statement.execute();

            log.info("Cat save! ");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Cat get(Long id) {
        Cat cat = null;

        try (final var statement = connection.prepareStatement(GET_CAT_SQL)) {
            statement.setLong(1, id);

            final var resultSet = statement.executeQuery();

            if (resultSet.next()) {
                cat = Cat.builder()
                        .id(resultSet.getLong("id"))
                        .name(resultSet.getString( "name"))
                        .build();
                log.info("cat return");
            } else {
                throw new RuntimeException("The cat is not exists");
            }
            return cat;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(Cat element) {

        try (var statement = connection.prepareStatement(UPDATE_CAT_SQL)){
            statement.setLong(1, element.getId());
            statement.setString(2, element.getName());
            statement.setLong(3, element.getId());
            statement.execute();
            log.info("Cat is update");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteId(Long id) {

        try (var statement = connection.prepareStatement(DELETE_CAT_SQL)) {
            statement.setLong(1, id);
            statement.execute();
            log.info("cat is deleted");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}