package com.example.esieaboard.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.*;
import com.example.esieaboard.model.entities.Attendance;
import com.example.esieaboard.model.entities.User;

import java.util.List;

@Dao
public interface UserDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(User user);

    @Update
    void update(User user);

    @Query("SELECT * FROM user_table WHERE emailAddress = :email AND password = :password")
    LiveData<User> get(String email, String password);

    @Query("SELECT * FROM user_table WHERE emailAddress = :email")
    LiveData<User> get(String email);

    @Query("SELECT * FROM user_table WHERE id = :id")
    LiveData<User> get(int id);

    @Query("SELECT * FROM user_table")
    LiveData<List<User>> getAll();

    @Delete
    void delete(User user);

    @Query("DELETE FROM user_table")
    void deleteAll();
}
