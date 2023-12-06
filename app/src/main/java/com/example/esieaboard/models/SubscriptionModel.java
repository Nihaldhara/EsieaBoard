package com.example.esieaboard.models;

import org.jetbrains.annotations.NotNull;

import java.io.Serializable;

public class SubscriptionModel implements Serializable {
    private int id;
    private int userId;
    private int clubId;
    private int nature;

    public SubscriptionModel(int id, int userId, int clubId, int nature) {
        this.id = id;
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
