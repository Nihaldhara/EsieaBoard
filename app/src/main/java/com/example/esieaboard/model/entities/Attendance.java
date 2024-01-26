package com.example.esieaboard.model.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "attendance",
    foreignKeys = {@ForeignKey(entity = User.class,
                parentColumns = "id",
                childColumns = "userId",
                onDelete = androidx.room.ForeignKey.CASCADE),
                @ForeignKey(entity = Event.class,
                parentColumns = "id",
                childColumns = "eventId",
                onDelete = androidx.room.ForeignKey.CASCADE)})
public class Attendance {
    @PrimaryKey(autoGenerate = true) private int id;
    @ColumnInfo(name = "userId") private int userId;
    @ColumnInfo(name = "eventId") private int eventId;
    @ColumnInfo(name = "status") private int status;

    public Attendance(int userId, int eventId, int status) {
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
