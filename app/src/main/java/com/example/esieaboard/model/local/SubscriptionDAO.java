package com.example.esieaboard.model.local;

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

    @Query("SELECT * FROM subscriptions WHERE id = :id AND clubId = :clubId")
    LiveData<Subscription> getById(int id, int clubId);

    @Query("SELECT * FROM subscriptions")
    LiveData<List<Subscription>> getAll();

    @Query("SELECT * FROM subscriptions WHERE userId = :userId AND clubId = :clubId")
    LiveData<Subscription> getByUserId(int userId, int clubId);

    @Query("SELECT * FROM subscriptions WHERE userId = :userId")
    LiveData<List<Subscription>> getAllByUser(int userId);

    @Query("DELETE FROM subscriptions WHERE userId = :userId AND clubId = :clubId")
    void unsubscribeUser(int userId, int clubId);

    @Delete
    void delete(Subscription subscription);
}
