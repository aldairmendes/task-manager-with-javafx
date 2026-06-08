package com.mendes15.taskmanagerwithjavafx;

import io.github.cdimascio.dotenv.Dotenv;
import javafx.application.Application;

public class Launcher {
    public static void main(String[] args) {
        Dotenv dotenv = Dotenv.configure().ignoreIfMissing().load();
        dotenv.entries().forEach(entry ->
                System.setProperty(entry.getKey(), entry.getValue())
        );
        Application.launch(HelloApplication.class, args);
    }
}
