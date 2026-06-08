package com.mendes15.taskmanagerwithjavafx.model;

public class UserBuilder {
    private int id;
    private String username;
    private String email;
    private String password;

    public UserBuilder withId(int id) {
        this.id = id;
        return this;
    }

    public UserBuilder withUsername(String username) {
        this.username = username;
        return this;
    }

    public UserBuilder withEmail(String email) {
        this.email = email;
        return this;
    }

    public UserBuilder withPassword(String password) {
        this.password = password;
        return this;
    }

    public User build() {
        return new User(id, username, email, password);
    }
}

