package com.example.esieaboard.models;

import org.jetbrains.annotations.NotNull;

import java.io.Serializable;

public class UserModel implements Serializable {

    private int id;
    private String firstName;
    private String lastName;
    private String emailAddress;
    private String password;
    private String description;
    private int rights;

    public UserModel(int id, String first_name, String last_name, String email_address, String password, String description, int rights) {
        this.id = id;
        this.firstName = first_name;
        this.lastName = last_name;
        this.emailAddress = email_address;
        this.password = password;
        this.description = description;
        this.rights = rights;
    }

    @Override
    public String toString() {
        return "UserModel{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", emailAddress='" + emailAddress + '\'' +
                ", password='" + password + '\'' +
                ", description='" + description + '\'' +
                ", rights=" + rights +
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getRights() {
        return rights;
    }

    public void setRights(int rights) {
        this.rights = rights;
    }
}
