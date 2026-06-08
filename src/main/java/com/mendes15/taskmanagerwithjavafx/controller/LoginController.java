package com.mendes15.taskmanagerwithjavafx.controller;

import com.mendes15.taskmanagerwithjavafx.dao.UserDAO;
import com.mendes15.taskmanagerwithjavafx.exception.NotFoundException;
import com.mendes15.taskmanagerwithjavafx.model.User;
import com.mendes15.taskmanagerwithjavafx.util.Util;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;


import static com.mendes15.taskmanagerwithjavafx.util.Util.disable;
import static com.mendes15.taskmanagerwithjavafx.util.Util.enable;

public class LoginController {

    @FXML
    TextField txtUsername;
    @FXML
    TextField txtEmail;
    @FXML
    PasswordField txtPassword;
    @FXML
    VBox usernameStep;
    @FXML
    VBox passwordStep;

    private final UserDAO userDAO = new UserDAO();

    public void handleNext() {
        try {
            if (txtUsername.getText().isBlank() && txtEmail.getText().isBlank()) {
                throw new IllegalArgumentException("Please enter either your username or email.");
            }

            if (userDAO.usernameExists(txtUsername.getText()) || userDAO.emailExists(txtEmail.getText())) {
                disable(usernameStep);
                enable(passwordStep);
            } else {
                throw new NotFoundException("User not found.", 404);
            }

        } catch (Exception e) {
            System.out.println("ERROR: " + e.getMessage());
        }
    }

    public void handleLogin(ActionEvent event) {
        if (txtPassword.getText().isBlank()) {
            throw new IllegalArgumentException("This field cannot be empty.");
        }

        if (userDAO.validateLoginUsername(txtUsername.getText(), txtPassword.getText())) {
            User user = userDAO.getByUsername(txtUsername.getText())
                    .orElseThrow(() -> new NotFoundException("User not found.", 404));
            int id = user.getId();
            Util.changeScreenWithId("/com/mendes15/taskmanagerwithjavafx/main-page.fxml", id, event);
        }
        if(userDAO.validateLoginEmail(txtEmail.getText(), txtPassword.getText())) {
            User user = userDAO.getByEmail(txtEmail.getText())
                    .orElseThrow(() -> new NotFoundException("User not found.", 404));
            int id = user.getId();
            Util.changeScreenWithId("/com/mendes15/taskmanagerwithjavafx/main-page.fxml", id, event);

        }
    }

    public void handleChangeUsername() {
        txtEmail.clear();
    }

    public void handleChangeEmail() {
        txtUsername.clear();
    }

    public void handleForgotPassword(ActionEvent event) {
        Util.changeScreen("/com/mendes15/taskmanagerwithjavafx/forgot-password-page.fxml", event);
    }

    public void handleNavigateToRegister(ActionEvent event) {
        Util.changeScreen("/com/mendes15/taskmanagerwithjavafx/register-page.fxml", event);
    }
}
