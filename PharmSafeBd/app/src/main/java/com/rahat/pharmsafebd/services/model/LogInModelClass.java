package com.rahat.pharmsafebd.services.model;

public class LogInModelClass {
    private String email;
    private String password;

    public LogInModelClass(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public LogInModelClass() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
