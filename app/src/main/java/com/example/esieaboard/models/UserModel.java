package com.example.esieaboard.models;

import org.jetbrains.annotations.NotNull;

public class UserModel {

    private int id;
    private String firstName;
    private String lastName;
    private String emailAddress;
    private String password;

    public UserModel(int id, String first_name, String last_name, String email_address, String password) {
        this.id = id;
        this.firstName = first_name;
        this.lastName = last_name;
        this.emailAddress = email_address;
        this.password = password;
    }

    @NotNull
    @Override
    public String toString() {
        return "UserModel{" +
                "id=" + id +
                ", first_name='" + firstName + '\'' +
                ", last_name='" + lastName + '\'' +
                ", email_address='" + emailAddress + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
