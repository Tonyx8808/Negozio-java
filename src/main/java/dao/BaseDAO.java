package dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public interface BaseDAO<T, ID> {

    T findById(ID id) throws SQLException;

    T findById(ID id, Connection conn) throws SQLException;

    ArrayList<T> findAll() throws SQLException;

    void insert(T entity) throws SQLException;
}