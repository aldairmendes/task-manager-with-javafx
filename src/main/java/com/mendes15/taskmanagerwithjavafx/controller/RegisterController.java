package com.mendes15.taskmanagerwithjavafx.controller;

import com.mendes15.taskmanagerwithjavafx.dao.UserDAO;
import com.mendes15.taskmanagerwithjavafx.model.User;
import com.mendes15.taskmanagerwithjavafx.model.UserBuilder;
import com.mendes15.taskmanagerwithjavafx.util.Util;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class RegisterController {

    @FXML
    TextField txtUsername;
    @FXML
    TextField txtEmail;
    @FXML
    PasswordField txtPassword;

    private final UserDAO userDAO;

    public RegisterController() {
        this.userDAO = new UserDAO();
    }

    public void signin(ActionEvent event) {
        try {
            if (txtUsername.getText().isBlank() || txtPassword.getText().isBlank() || txtPassword.getText().isBlank()) {
                throw new IllegalArgumentException("All fields are required.");
            }

            if (!Util.isValidEmail(txtEmail.getText())) {
                throw new IllegalArgumentException("Please enter a valid username or email address.");
            }

            User user = new UserBuilder()
                    .withUsername(txtUsername.getText())
                    .withEmail(txtEmail.getText())
                    .withPassword(txtPassword.getText())
                    .build();
            userDAO.save(user);

            int id = userDAO.getByUsername(txtUsername.getText())
                    .map(User::getId)
                    .orElse(-1);

            Util.changeScreenWithId("/com/mendes15/taskmanagerwithjavafx/main-page.fxml", id, event);
        } catch (Exception e) {
            System.out.println("ERROR: " + e.getMessage());
        }
    }

    public void handleBackToLogin(ActionEvent event) {
        Util.changeScreen("/com/mendes15/taskmanagerwithjavafx/login-page.fxml", event);
    }
}