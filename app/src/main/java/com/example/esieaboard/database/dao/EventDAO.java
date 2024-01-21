package com.example.esieaboard.database.dao;

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

    @Query("SELECT * FROM event_table")
    LiveData<List<Event>> getAll();

    @Query("SELECT * FROM event_table WHERE clubId = :clubId")
    LiveData<List<Event>> getAllByClub(int clubId);

    @Query("SELECT * FROM event_table INNER JOIN subscription_table ON event_table.clubId = subscription_table.clubId WHERE subscription_table.userId = :userId GROUP BY event_table.id")
    LiveData<List<Event>> getAllByUser(int userId);

    @Query("SELECT * FROM event_table WHERE id = :id")
    LiveData<Event> get(int id);

    @Delete
    void delete(Event event);
}
