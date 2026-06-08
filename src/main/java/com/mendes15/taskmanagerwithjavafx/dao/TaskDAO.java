package com.mendes15.taskmanagerwithjavafx.dao;

import com.mendes15.taskmanagerwithjavafx.interfaces.CrudInterface;
import com.mendes15.taskmanagerwithjavafx.model.Task;
import com.mendes15.taskmanagerwithjavafx.model.TaskBuilder;
import com.mendes15.taskmanagerwithjavafx.util.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TaskDAO implements CrudInterface<Task> {

    @Override
    public void save(Task task) {
        String sql = "INSERT INTO tasks (title, content, user_id) VALUES (?, ?, ?)";

        try (Connection con = DatabaseConnection.getConnection()) {
            PreparedStatement pstm = con.prepareStatement(sql);
            pstm.setString(1, task.getTitle());
            pstm.setString(2, task.getContent());
            pstm.setInt(3, task.getUserId());

            pstm.executeUpdate();

        } catch (SQLException e) {
            System.err.println("SQL Error on save task: " + e.getMessage());
            throw new RuntimeException("Error saving task to database.", e);
        }
    }

    @Override
    public List<Task> getAll() {
        String sql = "SELECT * FROM tasks";
        List<Task> tasks = new ArrayList<>();

        try (Connection con = DatabaseConnection.getConnection()) {
            PreparedStatement pstm = con.prepareStatement(sql);
            ResultSet rs = pstm.executeQuery();

            while (rs.next()) {
                Task task = new TaskBuilder()
                        .withId(rs.getInt("id"))
                        .withTitle(rs.getString("title"))
                        .withContent(rs.getString("content"))
                        .withUserId(rs.getInt("user_id"))
                        .build();
                tasks.add(task);
            }

            return tasks;

        } catch (SQLException e) {
            System.err.println("SQL Error on getAll tasks: " + e.getMessage());
            throw new RuntimeException("Error retrieving tasks from database.", e);
        }
    }

    @Override
    public Task getById(int id) {
        String sql = "SELECT * FROM tasks WHERE id = ?";

        try (Connection con = DatabaseConnection.getConnection()) {
            PreparedStatement pstm = con.prepareStatement(sql);
            pstm.setInt(1, id);
            ResultSet rs = pstm.executeQuery();

            Task task = null;
            if (rs.next()) {
                task = new TaskBuilder()
                        .withId(rs.getInt("id"))
                        .withTitle(rs.getString("title"))
                        .withContent(rs.getString("content"))
                        .withUserId(rs.getInt("user_id"))
                        .build();
            }

            return task;

        } catch (SQLException e) {
            System.err.println("SQL Error on getById task: " + e.getMessage());
            throw new RuntimeException("Error finding task by ID.", e);
        }
    }

    @Override
    public void update(int id, String[] columns, Object[] values) {
        if (columns == null || values == null || columns.length == 0 || columns.length != values.length) {
            throw new IllegalArgumentException("Columns and values arrays must not be empty and must have the same length.");
        }

        StringBuilder sqlBuilder = new StringBuilder("UPDATE tasks SET ");
        for (int i = 0; i < columns.length; i++) {
            sqlBuilder.append(columns[i]).append(" = ?");
            if (i < columns.length - 1) {
                sqlBuilder.append(", ");
            }
        }
        sqlBuilder.append(" WHERE id = ?");

        String sql = sqlBuilder.toString();

        try (Connection con = DatabaseConnection.getConnection()) {
            PreparedStatement pstm = con.prepareStatement(sql);

            int paramIndex = 1;
            for (Object value : values) {
                pstm.setObject(paramIndex, value);
                paramIndex++;
            }

            pstm.setInt(paramIndex, id);

            pstm.executeUpdate();

        } catch (SQLException e) {
            System.err.println("SQL Error on update task: " + e.getMessage());
            throw new RuntimeException("Error updating task.", e);
        }
    }

    @Override
    public void delete(int id) {
        String sql = "DELETE FROM tasks WHERE id = ?";

        try (Connection con = DatabaseConnection.getConnection()) {
            PreparedStatement pstm = con.prepareStatement(sql);
            pstm.setInt(1, id);

            pstm.executeUpdate();

        } catch (SQLException e) {
            System.err.println("SQL Error on delete task: " + e.getMessage());
            throw new RuntimeException("Error deleting task.", e);
        }
    }

    public List<Task> getByUserId(int userId) {
        String sql = "SELECT * FROM tasks WHERE user_id = ?";
        List<Task> tasks = new ArrayList<>();

        try (Connection con = DatabaseConnection.getConnection()) {
            PreparedStatement pstm = con.prepareStatement(sql);
            pstm.setInt(1, userId);
            ResultSet rs = pstm.executeQuery();

            while (rs.next()) {
                Task task = new TaskBuilder()
                        .withId(rs.getInt("id"))
                        .withTitle(rs.getString("title"))
                        .withContent(rs.getString("content"))
                        .withUserId(rs.getInt("user_id"))
                        .build();
                tasks.add(task);
            }

            return tasks;

        } catch (SQLException e) {
            System.err.println("SQL Error on getByUserId tasks: " + e.getMessage());
            throw new RuntimeException("Error retrieving user tasks.", e);
        }
    }
}