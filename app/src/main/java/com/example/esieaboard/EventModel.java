package com.example.esieaboard;

import org.jetbrains.annotations.NotNull;

public class EventModel {
    private int id;
    private int clubId;
    private String name;
    private String description;
    private String date;
    private String location;
    private int capacity;

    public EventModel(int id, int clubId, String name, String description, String date, String location, int capacity) {
        this.id = id;
        this.clubId = clubId;
        this.name = name;
        this.description = description;
        this.date = date;
        this.location = location;
        this.capacity = capacity;
    }

    @NotNull
    @Override
    public String toString() {
        return "EventModel{" +
                "id=" + id +
                ", clubId=" + clubId +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", date='" + date + '\'' +
                ", location='" + location + '\'' +
                ", capacity=" + capacity +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getClubId() {
        return clubId;
    }

    public void setClubId(int clubId) {
        this.clubId = clubId;
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }
}
