package com.example.esieaboard.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.*;
import com.example.esieaboard.model.entities.Attendance;
import com.example.esieaboard.model.entities.Subscription;

import java.util.List;

@Dao
public interface SubscriptionDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Subscription subscription);

    @Update
    void update(Subscription subscription);

    @Query("SELECT * FROM subscription_table WHERE id = :id AND clubId = :clubId")
    LiveData<Subscription> getById(int id, int clubId);

    @Query("SELECT * FROM subscription_table")
    LiveData<List<Subscription>> getAll();

    @Query("SELECT * FROM subscription_table WHERE userId = :userId AND clubId = :clubId")
    LiveData<Subscription> getByUserId(int userId, int clubId);

    @Query("SELECT * FROM subscription_table WHERE userId = :userId")
    LiveData<List<Subscription>> getAllByUser(int userId);

    @Query("DELETE FROM subscription_table WHERE userId = :userId AND clubId = :clubId")
    void unsubscribeUser(int userId, int clubId);

    @Delete
    void delete(Subscription subscription);
}
