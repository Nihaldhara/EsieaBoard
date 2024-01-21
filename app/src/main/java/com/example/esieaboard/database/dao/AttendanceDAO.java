package com.example.esieaboard.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.*;
import com.example.esieaboard.model.entities.Attendance;

import java.util.List;

@Dao
public interface AttendanceDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Attendance attendance);

    @Update
    void update(Attendance attendance);

    @Query("SELECT * FROM attendance_table")
    LiveData<List<Attendance>> getAll();

    @Query("DELETE FROM attendance_table WHERE userId = :userId AND eventId = :eventId")
    void unattend(int userId, int eventId);

    @Query("SELECT * FROM attendance_table WHERE userId = :userId AND eventId = :eventId")
    LiveData<Attendance> get(int userId, int eventId);

    @Query("SELECT * FROM attendance_table WHERE eventId = :eventId")
    LiveData<List<Attendance>> getAllByEvent(int eventId);

    @Delete
    void delete(Attendance attendance);
}
