package com.example.esieaboard.model.local;

import androidx.lifecycle.LiveData;
import androidx.room.*;
import com.example.esieaboard.model.entities.Attendance;
import com.example.esieaboard.model.entities.Event;
import com.example.esieaboard.model.entities.Subscription;

import java.util.List;

@Dao
public interface EventDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Event event);

    @Update
    void update(Event event);

    @Query("SELECT * FROM events")
    LiveData<List<Event>> getAll();

    @Query("SELECT * FROM events WHERE clubId = :clubId ORDER BY date ASC")
    LiveData<List<Event>> getAllByClub(int clubId);

    @Query("SELECT events.* FROM events JOIN subscriptions ON events.clubId = subscriptions.clubId WHERE subscriptions.userId = :userId")
    LiveData<List<Event>> getAllByUser(int userId);

    @Query("SELECT * FROM events WHERE id = :id")
    LiveData<Event> get(int id);

    @Delete
    void delete(Event event);
}
