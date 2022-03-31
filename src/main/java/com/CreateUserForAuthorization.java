package com;

public class CreateUserForAuthorization {

    public String email;
    public String password;

    public CreateUserForAuthorization(String email, String password) {
        this.email = email;
        this.password = password;

    }

    public CreateUserForAuthorization() {

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