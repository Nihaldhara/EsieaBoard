package com.example.esieaboard;

import org.jetbrains.annotations.NotNull;

public class AdministratorModel {
    private int id;
    private int userId;
    private int clubId;
    private int rights;

    public AdministratorModel(int id, int userId, int clubId, int rights) {
        this.id = id;
        this.userId = userId;
        this.clubId = clubId;
        this.rights = rights;
    }

    @NotNull
    @Override
    public String toString() {
        return "AdministratorModel{" +
                "id=" + id +
                ", userId=" + userId +
                ", clubId=" + clubId +
                ", rights=" + rights +
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

    public int getRights() {
        return rights;
    }

    public void setRights(int rights) {
        this.rights = rights;
    }
}
