package com.example.esieaboard.model.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import org.jetbrains.annotations.NotNull;

import java.io.Serializable;

@Entity(tableName = "club_table")
public class Club implements Serializable {

    @PrimaryKey(autoGenerate = true) private int id;
    @ColumnInfo(name = "name") private String name;
    @ColumnInfo(name = "email") private String email;
    @ColumnInfo(name = "description") private String description;

    public Club(String name, String email, String description) {
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
