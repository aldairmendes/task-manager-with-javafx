package com.mendes15.taskmanagerwithjavafx.dao;

import com.mendes15.taskmanagerwithjavafx.model.User;
import com.mendes15.taskmanagerwithjavafx.model.UserBuilder;
import com.mendes15.taskmanagerwithjavafx.util.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public class UserDAO {

    public void save(User user) {
        String sql = "INSERT INTO users (username, email, pwd) VALUES (?, ?, ?)";

        try (Connection con = DatabaseConnection.getConnection()) {

            con.setAutoCommit(false);

            if (usernameExists(user.getUsername())) {
                throw new IllegalArgumentException("Username already exists.");
            }
            if (emailExists(user.getEmail())) {
                throw new IllegalArgumentException("Email already exists.");
            }

            PreparedStatement pstm = con.prepareStatement(sql);
            pstm.setString(1, user.getUsername());
            pstm.setString(2, user.getEmail());
            pstm.setString(3, user.getPassword());
            pstm.executeUpdate();

            con.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<User> getAll() {
        List<User> users = new ArrayList<>();
        String sql = "SELECT id, username, email FROM users";

        try (Connection con = DatabaseConnection.getConnection()) {
            PreparedStatement pstm = con.prepareStatement(sql);
            ResultSet rs = pstm.executeQuery();

            while (rs.next()) {
                User user = new UserBuilder()
                        .withId(rs.getInt("id"))
                        .withUsername(rs.getString("username"))
                        .withEmail(rs.getString("email"))
                        .build();
                users.add(user);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    public Optional<User> getById(int id) {
        String sql = "SELECT username, email FROM users WHERE id = ?";

        try (Connection con = DatabaseConnection.getConnection()) {
            con.setAutoCommit(false);
            PreparedStatement pstm = con.prepareStatement(sql);
            pstm.setInt(1, id);
            ResultSet rs = pstm.executeQuery();
            if (rs.next()) {
                User user = new UserBuilder()
                        .withId(id)
                        .withUsername(rs.getString("username"))
                        .withEmail(rs.getString("email"))
                        .build();
                return Optional.of(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    public void update(int id, String[] fields, Object[] values) {

        if (fields == null || values == null || fields.length != values.length) {
            throw new IllegalArgumentException("The number of fields must match the number of values.");
        }

        Set<String> allowedColumns = Set.of("username", "email", "pwd");

        for (String field : fields) {
            if (!allowedColumns.contains(field)) {
                throw new IllegalArgumentException("Field inexistent or not allowed: " + field);
            }
        }

        String sql = "UPDATE users SET " + String.join(" = ?, ", fields) + " = ? WHERE id = ?";

        try (Connection con = DatabaseConnection.getConnection()) {
            con.setAutoCommit(false);
            PreparedStatement pstm = con.prepareStatement(sql);
            for (int i = 0; i < values.length; i++) {
                pstm.setObject(i + 1, values[i]);
            }

            pstm.setInt(values.length + 1, id);

            pstm.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete(int id) {
        String sql = "DELETE FROM users WHERE id = ?";

        try (Connection con = DatabaseConnection.getConnection()) {
            con.setAutoCommit(false);
            PreparedStatement pstm = con.prepareStatement(sql);
            pstm.setInt(1, id);
            pstm.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean usernameExists(String username) {
        String sql = "SELECT 1 FROM users WHERE username = ?";

        try (Connection con = DatabaseConnection.getConnection()) {
            con.setAutoCommit(false);
            PreparedStatement pstm = con.prepareStatement(sql);

            pstm.setString(1, username.trim());
            ResultSet rs = pstm.executeQuery();
            return rs.next();

        } catch (SQLException e) {
            throw new RuntimeException("Error on verify if username exists.", e);
        }
    }

    public boolean emailExists(String email) {
        String sql = "SELECT 1 FROM users WHERE email = ?";

        try (Connection con = DatabaseConnection.getConnection()) {
            con.setAutoCommit(false);
            PreparedStatement pstm = con.prepareStatement(sql);

            pstm.setString(1, email.trim());

            try (ResultSet rs = pstm.executeQuery()) {
                return rs.next();
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error on verify if email exists.", e);
        }
    }

    public boolean validateLoginUsername(String username, String password) {
        String sql = "SELECT 1 FROM users WHERE username = ? AND pwd = ?";

        try (Connection con = DatabaseConnection.getConnection()) {
            con.setAutoCommit(false);

            PreparedStatement pstm = con.prepareStatement(sql);
            pstm.setString(1, username.trim());
            pstm.setString(2, password);

            ResultSet rs = pstm.executeQuery();

            if (rs.next()) {
                con.commit();
                return true;
            } else {
                con.rollback();
                return false;
            }

        } catch (SQLException e) {
            System.err.println("SQL Error: " + e.getMessage());
            throw new RuntimeException("Error validating login with username due to a database failure.", e);
        }
    }

    public boolean validateLoginEmail(String email, String password) {
        String sql = "SELECT 1 FROM users WHERE email = ? AND pwd = ?";

        try (Connection con = DatabaseConnection.getConnection()) {
            con.setAutoCommit(false);

            PreparedStatement pstm = con.prepareStatement(sql);
            pstm.setString(1, email.trim());
            pstm.setString(2, password);

            ResultSet rs = pstm.executeQuery();

            if (rs.next()) {
                con.commit();
                return true;
            } else {
                con.rollback();
                return false;
            }

        } catch (SQLException e) {
            System.err.println("SQL Error: " + e.getMessage());
            throw new RuntimeException("Error validating login with email due to a database failure.", e);
        }
    }

    public Optional<User> getByUsername(String username) {
        String sql = "SELECT id, username, email FROM users WHERE username = ?";

        try (Connection con = DatabaseConnection.getConnection()) {
            con.setAutoCommit(false);
            PreparedStatement pstm = con.prepareStatement(sql);
            pstm.setString(1, username);
            try (ResultSet rs = pstm.executeQuery()) {
                if (rs.next()) {
                    User user = new UserBuilder()
                            .withId(rs.getInt("id"))
                            .withUsername(username)
                            .withEmail(rs.getString("email"))
                            .build();
                    return Optional.of(user);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    public Optional<User> getByEmail(String email) {
        String sql = "SELECT id, username, email FROM users WHERE email = ?";

        try (Connection con = DatabaseConnection.getConnection()) {
            con.setAutoCommit(false);
            PreparedStatement pstm = con.prepareStatement(sql);
            pstm.setString(1, email);
            try (ResultSet rs = pstm.executeQuery()) {
                if (rs.next()) {
                    User user = new UserBuilder()
                            .withId(rs.getInt("id"))
                            .withUsername(rs.getString("username"))
                            .withEmail(email)
                            .build();
                    return Optional.of(user);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }
}