module com.mendes15.taskmanagerwithjavafx {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires java.sql;
    requires java.dotenv;
    requires annotations;

    opens com.mendes15.taskmanagerwithjavafx to javafx.fxml;
    opens com.mendes15.taskmanagerwithjavafx.controller to javafx.fxml;

    exports com.mendes15.taskmanagerwithjavafx;
    exports com.mendes15.taskmanagerwithjavafx.controller;
    exports com.mendes15.taskmanagerwithjavafx.dao;
    exports com.mendes15.taskmanagerwithjavafx.repositories;
    exports com.mendes15.taskmanagerwithjavafx.model;
}