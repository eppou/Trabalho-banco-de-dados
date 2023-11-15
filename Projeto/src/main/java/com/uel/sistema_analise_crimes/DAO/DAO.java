package com.uel.sistema_analise_crimes.DAO;

import java.util.List;

public interface DAO<T> {

    public int create(T object);
    public T get(Object key);
    public List<T> getAll();
    public int update(T object);
    public int delete(Object key);

}

