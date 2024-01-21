package com.example.esieaboard.viewmodel;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import com.example.esieaboard.model.entities.Attendance;
import com.example.esieaboard.model.repository.AttendanceRepository;

import java.util.List;

public class AttendanceViewModel extends AndroidViewModel {
    private AttendanceRepository attendanceRepository;
    private LiveData<List<Attendance>> allAttendances;

    public AttendanceViewModel(Application application) {
        super(application);
        attendanceRepository = new AttendanceRepository(application);
        allAttendances = attendanceRepository.getAll();
    }

    public void insert(Attendance attendance) {
        attendanceRepository.insert(attendance);
    }

    public void update(Attendance attendance) {
        attendanceRepository.update(attendance);
    }

    public void delete(Attendance attendance) {
        attendanceRepository.delete(attendance);
    }

    public void unattend(int userId, int eventId) {
        attendanceRepository.unattend(userId, eventId);
    }

    public LiveData<Attendance> get(int userId, int eventId) {
        return attendanceRepository.get(userId, eventId);
    }

    public LiveData<List<Attendance>> getAllByEvent(int eventId) {
        return attendanceRepository.getAllByEvent(eventId);
    }

    public LiveData<List<Attendance>> getAll() {
        return allAttendances;
    }
}
