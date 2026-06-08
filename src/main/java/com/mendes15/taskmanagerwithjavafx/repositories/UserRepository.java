package com.mendes15.taskmanagerwithjavafx.repositories;

import com.mendes15.taskmanagerwithjavafx.dao.UserDAO;
import com.mendes15.taskmanagerwithjavafx.exception.NotFoundException;
import com.mendes15.taskmanagerwithjavafx.interfaces.CrudInterface;
import com.mendes15.taskmanagerwithjavafx.model.User;

import java.util.List;

public class UserRepository implements CrudInterface<User> {
    private final UserDAO userDAO;

    public UserRepository(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @Override
    public void save(User user) {
        userDAO.save(user);
    }

    @Override
    public List<User> getAll() {
        return userDAO.getAll();
    }

    @Override
    public User getById(int id) {
        return userDAO.getById(id)
                .orElseThrow(() -> new NotFoundException("User not found.", 404));
    }

    @Override
    public void update(int id, String[] fields, Object[] values) {
        userDAO.update(id, fields, values);
    }

    @Override
    public void delete(int id) {
        userDAO.delete(id);
    }
}
