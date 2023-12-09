package com.example.esieaboard.models;

public class AttendanceModel {
    private int id;
    private int userId;
    private int eventId;
    private int status;

    public AttendanceModel(int id, int userId, int eventId, int status) {
        this.id = id;
        this.userId = userId;
        this.eventId = eventId;
        this.status = status;
    }

    @Override
    public String toString() {
        return "AttendanceModel{" +
                "id=" + id +
                ", userId=" + userId +
                ", eventId=" + eventId +
                ", status=" + status +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getEventId() {
        return eventId;
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
