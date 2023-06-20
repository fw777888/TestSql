package org.example.dao;

public interface Dao<K, E> {

    void createTable();

    void dropTable();

    void save(E element);

    E get(K id);

    void update(E element);

    void deleteId(K id);
}
