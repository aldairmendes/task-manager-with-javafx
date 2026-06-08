package com.mendes15.taskmanagerwithjavafx.util;

import com.mendes15.taskmanagerwithjavafx.interfaces.Identifiable;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextInputControl;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Util {

    private static final String EMAIL_REGEX = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
    private static final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);

    public static<T extends Node> void enable(T t) {
        if(t != null){
            t.setDisable(false);
            t.setManaged(true);
            t.setVisible(true);
        }
    }

    public static<T extends Node> void disable(T t) {
        if(t != null){
            t.setDisable(true);
            t.setManaged(false);
            t.setVisible(false);
            if(t instanceof TextInputControl) {
                ((TextInputControl) t).clear();
            }
        }
    }

    public static <E extends Event> void changeScreen(String fxmlPath, E event) {
        try {
            FXMLLoader loader = new FXMLLoader(Util.class.getResource(fxmlPath));
            Parent root = loader.load();

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            stage.setScene(new Scene(root));
            stage.show();

        } catch (IOException e) {
            System.err.println("File error: [" + fxmlPath + "]");
            e.printStackTrace();
        }
    }

    public static <E extends Event> void changeScreenWithId(String fxmlPath, int id, E event) {
        try {
            FXMLLoader loader = new FXMLLoader(Util.class.getResource(fxmlPath));
            Parent root = loader.load();

            Object controller = loader.getController();

            if (controller instanceof Identifiable) {
                ((Identifiable) controller).setUserId(id);
            }

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();

        } catch (IOException e) {
            System.err.println("File error: [" + fxmlPath + "]");
            e.printStackTrace();
        }
    }

    public static boolean isValidEmail(String email) {
        if (email == null || email.isBlank()) {
            return false;
        }
        Matcher matcher = EMAIL_PATTERN.matcher(email.trim());
        return matcher.matches();
    }
}
