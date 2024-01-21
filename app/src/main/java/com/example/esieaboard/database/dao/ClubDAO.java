package com.example.esieaboard.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.*;
import com.example.esieaboard.model.entities.Attendance;
import com.example.esieaboard.model.entities.Club;

import java.util.List;

@Dao
public interface ClubDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Club club);

    @Update
    void update(Club club);

    @Query("SELECT * FROM club_table")
    LiveData<List<Club>> getAll();

    @Query("SELECT * FROM club_table WHERE id = :id")
    LiveData<Club> getById(int id);

    @Delete
    void delete(Club club);
}
