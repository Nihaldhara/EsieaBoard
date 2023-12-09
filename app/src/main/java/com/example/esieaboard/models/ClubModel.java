package com.example.esieaboard.models;

import org.jetbrains.annotations.NotNull;

import java.io.Serializable;

public class ClubModel implements Serializable {

    private int id;
    private String name;
    private String email;
    private String description;

    public ClubModel(int id, String name, String email, String description) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.description = description;
    }

    @NotNull
    @Override
    public String toString() {
        return "ClubModel{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", description='" + description + '\'' +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
