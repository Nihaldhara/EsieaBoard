package com.example.esieaboard.model.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;
import org.jetbrains.annotations.NotNull;

import java.io.Serializable;

@Entity(tableName = "subscriptions",
    foreignKeys = {@ForeignKey(entity = Club.class,
                parentColumns = "id",
                childColumns = "clubId",
                onDelete = ForeignKey.CASCADE),
                @ForeignKey(entity = User.class,
                parentColumns = "id",
                childColumns = "userId",
                onDelete = ForeignKey.CASCADE)})
public class Subscription implements Serializable {
    @PrimaryKey(autoGenerate = true) private int id;
    @ColumnInfo(name = "userId") private int userId;
    @ColumnInfo(name = "clubId") private int clubId;
    @ColumnInfo(name = "nature") private int nature;

    public Subscription(int userId, int clubId, int nature) {
        this.userId = userId;
        this.clubId = clubId;
        this.nature = nature;
    }

    @NotNull
    @Override
    public String toString() {
        return "SubscriptionModel{" +
                "id=" + id +
                ", userId=" + userId +
                ", clubId=" + clubId +
                ", nature=" + nature +
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

    public int getClubId() {
        return clubId;
    }

    public void setClubId(int clubId) {
        this.clubId = clubId;
    }

    public int getNature() {
        return nature;
    }

    public void setNature(int nature) {
        this.nature = nature;
    }
}
