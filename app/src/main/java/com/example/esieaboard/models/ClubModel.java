package com.example.esieaboard.models;

import org.jetbrains.annotations.NotNull;

import java.io.Serializable;

public class ClubModel implements Serializable {

    private int id;
    private String name;
    private String description;

    public ClubModel(int id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    @NotNull
    @Override
    public String toString() {
        return "ClubModel{" +
                "id=" + id +
                ", name='" + name + '\'' +
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
