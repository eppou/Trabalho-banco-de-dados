package com.uel.sistema_analise_crimes.DAO;

import java.util.List;


import java.sql.SQLException;
import java.util.List;

public interface DAO<T> {

    public void create(T object) throws SQLException;
    public T get(Object key) throws SQLException;
    public List<T> getAll() throws SQLException;
    public void update(T object) throws SQLException;
    public void delete(Object key) throws SQLException;

}

