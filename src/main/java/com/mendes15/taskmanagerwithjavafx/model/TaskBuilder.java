package com.mendes15.taskmanagerwithjavafx.model;

public class TaskBuilder {
    private int id;
    private String title;
    private String content;
    private int userId;

    public TaskBuilder withId(int id) {
        this.id = id;
        return this;
    }

    public TaskBuilder withTitle(String title) {
        this.title = title;
        return this;
    }

    public TaskBuilder withContent(String content) {
        this.content = content;
        return this;
    }

    public TaskBuilder withUserId(int userId) {
        this.userId = userId;
        return this;
    }

    public Task build() {
        return new Task(id, title, content, userId);
    }
}
