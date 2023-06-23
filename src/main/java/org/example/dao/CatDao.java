package org.example.dao;


import org.example.model.Cat;
import org.example.util.UtilConnection;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

// CRUD - Create Read Update Delete

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
            VALUES (%d, '%s')
            """;

    private final String GET_CAT_SQL = """
           SELECT * 
            FROM cat 
            WHERE id = %d 
            """;

    private final String UPDATE_CAT_SQL = """
            UPDATE cat
            SET
                id = %d,
                name = '%s' 
            WHERE
                id = %d
            """;

    private final String DELETE_CAT_SQL = """
            DELETE FROM 
                cat
            WHERE
                id = %d
            """;

    private final Connection connection = UtilConnection.getConnection();

    @Override
    public void createTable() {

        try (final var statement = connection.createStatement()) {
            statement.execute(CREATE_TABLE_CAT_SQL);

            System.out.println("Table cat created!");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void dropTable() {

        try (final var statement = connection.createStatement()) {

            statement.execute(DROP_TABLE_CAT_SQL);
            System.out.println("Table cat deleted!");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void save(Cat element) {

        try (final var statement = connection.createStatement()) {
            statement.execute(SAVE_CAT_SQL.formatted(element.getId(), element.getName()));

            System.out.println("Cat save! " + element);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Cat get(Long id) {

        try (final var statement = connection.createStatement()) {
            var resultSet = statement.executeQuery(GET_CAT_SQL.formatted(id));
            resultSet.next();

            return new Cat(
                    resultSet.getLong("id"),
                    resultSet.getString("name"));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(Cat element) {

        try (var statement = connection.createStatement()){
            final var sql = UPDATE_CAT_SQL.formatted(
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
            final var sql = DELETE_CAT_SQL.formatted(id);

            statement.execute(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}