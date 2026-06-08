package com.mendes15.taskmanagerwithjavafx.util;

import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;

import java.util.Optional;

public class AlertUtil {
    public static boolean showConfirmAlert(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);

        Button confirmButton = new Button("Confirm");
        Button cancelButton = new Button("Cancel");

        ButtonType typeConfirm = new ButtonType(confirmButton.getText());
        ButtonType typeCancel = new ButtonType(cancelButton.getText());

        alert.getButtonTypes().setAll(typeConfirm, typeCancel);

        Optional<ButtonType> result = alert.showAndWait();

        return result.isPresent() && result.get() == typeConfirm;
    }
}
