package com.mendes15.taskmanagerwithjavafx.interfaces;

import java.util.List;

public interface CrudInterface<T> {
    void save(T t);
    List<T> getAll();
    T getById(int id);
    void update(int id, String[] fields, Object[] values);
    void delete(int id);
}
