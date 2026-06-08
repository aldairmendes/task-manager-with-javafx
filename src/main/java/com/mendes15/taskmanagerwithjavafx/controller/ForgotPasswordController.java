package com.mendes15.taskmanagerwithjavafx.controller;

import com.mendes15.taskmanagerwithjavafx.dao.UserDAO;
import com.mendes15.taskmanagerwithjavafx.model.User;
import com.mendes15.taskmanagerwithjavafx.util.Util;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class ForgotPasswordController {
    @FXML
    public TextField txtUsername;
    @FXML
    TextField txtEmail;
    @FXML
    Label emailInstruction;

    private final UserDAO userDAO;

    public ForgotPasswordController() {
        this.userDAO = new UserDAO();
    }

    public void send() {
        if (userDAO.usernameExists(txtUsername.getText())) {
            String email = userDAO.getByUsername(txtUsername.getText()).
                    map(User::getEmail).orElse(null);


            emailInstruction.setText("An email was sent to <" + email + ">");
            Util.enable(emailInstruction);
            return;
        }
        if (userDAO.emailExists(txtEmail.getText())) {
            emailInstruction.setText("An email was sent to <" + txtEmail.getText() + ">");
            Util.enable(emailInstruction);
            return;
        }
        emailInstruction.setText("Please enter a valid email address.");
        Util.enable(emailInstruction);
    }

    public void handleChangeUsername() {
        txtEmail.clear();
    }

    public void handleChangeEmail() {
        txtUsername.clear();
    }

    public void handleBackToLogin(ActionEvent event) {
        Util.changeScreen("/com/mendes15/taskmanagerwithjavafx/login-page.fxml", event);
    }
}
