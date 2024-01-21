package com.example.esieaboard.model.repository;

import android.app.Application;
import androidx.lifecycle.LiveData;
import com.example.esieaboard.database.dao.AttendanceDAO;
import com.example.esieaboard.database.db.AppDatabase;
import com.example.esieaboard.model.entities.Attendance;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AttendanceRepository {
    private AttendanceDAO attendanceDAO;
    private LiveData<List<Attendance>> allAttendances;
    private ExecutorService executor;

    public AttendanceRepository(Application application) {
        AppDatabase database = AppDatabase.getInstance(application);
        this.attendanceDAO = database.attendanceDAO();
        this.allAttendances = attendanceDAO.getAll();
        this.executor = Executors.newSingleThreadExecutor();
    }

    public void insert(Attendance attendance) {
        executor.execute(() -> attendanceDAO.insert(attendance));
    }

    public void update(Attendance attendance) {
        executor.execute(() -> attendanceDAO.update(attendance));
    }

    public void delete(Attendance attendance) {
        executor.execute(() -> attendanceDAO.delete(attendance));
    }

    public LiveData<List<Attendance>> getAll() {
        return allAttendances;
    }

    public void unattend(int userId, int eventId) {
        executor.execute(() -> attendanceDAO.unattend(userId, eventId));
    }

    public LiveData<Attendance> get(int userId, int eventId) {
        return attendanceDAO.get(userId, eventId);
    }

    public LiveData<List<Attendance>> getAllByEvent(int eventId) {
        return attendanceDAO.getAllByEvent(eventId);
    }

    public void shutDownExecutor() {
        executor.shutdown();
    }
}
