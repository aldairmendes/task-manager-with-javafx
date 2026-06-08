package com.mendes15.taskmanagerwithjavafx.controller;

import com.mendes15.taskmanagerwithjavafx.dao.TaskDAO;
import com.mendes15.taskmanagerwithjavafx.dao.UserDAO;
import com.mendes15.taskmanagerwithjavafx.interfaces.Identifiable;
import com.mendes15.taskmanagerwithjavafx.model.Task;
import com.mendes15.taskmanagerwithjavafx.model.TaskBuilder;
import com.mendes15.taskmanagerwithjavafx.model.User;
import com.mendes15.taskmanagerwithjavafx.util.AlertUtil;
import com.mendes15.taskmanagerwithjavafx.util.Util;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.control.Label;

import java.util.List;

public class MainController implements Identifiable {
    @FXML
    public TextField taskTitle;
    @FXML
    public TextField taskContent;
    @FXML
    private TextArea usersList;
    @FXML
    public VBox tasksByUser;

    private int loadedId;

    private final UserDAO userDAO;
    private final TaskDAO taskDAO;

    public MainController() {
        this.userDAO = new UserDAO();
        this.taskDAO = new TaskDAO();
    }

    @Override
    public void setUserId(int id) {
        this.loadedId = id;
    }

    public void getAllTasks() {
        tasksByUser.getChildren().clear();
        if (!usersList.isDisable()) {
            Util.disable(usersList);
        }
        List<Task> tasks = taskDAO.getByUserId(loadedId);
        if (tasks == null || tasks.isEmpty()) {
            return;
        }

        for (Task task : tasks) {
            HBox taskCard = new HBox(12);
            taskCard.setStyle("-fx-padding: 8; -fx-border-color: #ccc; -fx-border-width: 0 0 1 0; -fx-alignment: CENTER_LEFT;");

            Region region = new javafx.scene.layout.Region();
            HBox.setHgrow(region, Priority.ALWAYS);

            String info = "ID: " + task.getId() + " | Title: " + task.getTitle() + " | Content: " + task.getContent();
            Label label = new Label(info);
            label.setMinWidth(Label.USE_PREF_SIZE);

            HBox.setHgrow(label, Priority.ALWAYS);

            TextField updateTitle = new TextField();
            updateTitle.setPromptText("Edit title here");
            updateTitle.setDisable(true);
            updateTitle.setVisible(false);

            TextField updateContent = new TextField();
            updateContent.setPromptText("Edit content here");
            updateContent.setDisable(true);
            updateContent.setVisible(false);

            Button btnDelete = new Button("Delete");
            btnDelete.setStyle("-fx-background-color: #ff4d4d; -fx-text-fill: white; -fx-cursor: hand;");
            btnDelete.setMinWidth(Button.USE_PREF_SIZE);
            btnDelete.setOnAction(event -> {
                boolean confirm = AlertUtil.showConfirmAlert(
                        "Delete Task",
                        "Are you sure?",
                        "Do you really want to delete this task? This action cannot be undone."
                );

                if (confirm) {
                    taskDAO.delete(task.getId());
                    tasksByUser.getChildren().remove(taskCard);
                    getAllTasks();
                }
            });

            Button btnUpdate = new Button("Update");
            btnUpdate.setDisable(true);
            btnUpdate.setStyle("-fx-background-color: #007bff; -fx-text-fill: white; -fx-cursor: hand;");
            btnUpdate.setMinWidth(Button.USE_PREF_SIZE);

            btnUpdate.setOnAction(event -> {
                taskDAO.update(task.getId(), new String[]{"title", "content"}, new String[]{updateTitle.getText(), updateContent.getText()});
                getAllTasks();
            });

            updateTitle.setOnKeyReleased(event -> {
                btnUpdate.setDisable(updateTitle.getText().isBlank() && updateContent.getText().isBlank());
            });

            Button btnToggleArea = new Button("Edit");
            btnToggleArea.setMinWidth(Button.USE_PREF_SIZE);

            btnToggleArea.setOnAction(event -> {
                toggleUpdateArea(updateTitle, updateContent);
                toggleUpdateButton(btnUpdate);
                toggleEditButtonName(btnToggleArea);
            });

            taskCard.getChildren().addAll(label, updateTitle, updateContent, region, btnToggleArea, btnUpdate, btnDelete);
            tasksByUser.getChildren().add(taskCard);
            Util.enable(tasksByUser);
        }
    }

    public void createTask() {

        if (taskTitle.getText().isBlank() || taskContent.getText().isBlank()) {
            throw new IllegalArgumentException("All fields are required");
        }

        Task task = new TaskBuilder()
                .withTitle(taskTitle.getText())
                .withContent(taskContent.getText())
                .withUserId(loadedId)
                .build();

        taskDAO.save(task);

        taskTitle.clear();
        taskContent.clear();
        getAllTasks();
    }

    public void getAllUsers() {
        if (!tasksByUser.isDisable()) {
            Util.disable(tasksByUser);
        }
        List<User> users = userDAO.getAll();
        StringBuilder sb = new StringBuilder();

        for (User user : users) {
            sb.append("ID: ").append(user.getId())
                    .append(" | Username: ").append(user.getUsername())
                    .append(" | Email: ").append(user.getEmail())
                    .append("\n");
        }

        usersList.setText(sb.toString());
        Util.enable(usersList);
    }

    private void toggleEditButtonName(Button toggleButton) {
        if (toggleButton.getText().equals("Edit")) {
            toggleButton.setText("Cancel");
        } else {
            toggleButton.setText("Edit");
        }
    }

    private void toggleUpdateButton(Button btnUpdate) {
        btnUpdate.setDisable(!btnUpdate.isDisable());
    }

    private void toggleUpdateArea(TextField title, TextField content) {
        if (title.isDisable() && content.isDisable()) {
            Util.enable(title);
            Util.enable(content);
        } else {
            Util.disable(title);
            Util.disable(content);
        }
    }

    public void handleClickExit(ActionEvent event) {
        Util.changeScreen("/com/mendes15/taskmanagerwithjavafx/login-page.fxml", event);
    }

    public void handleClickStart() {
        Util.disable(usersList);
        Util.disable(tasksByUser);
    }
}