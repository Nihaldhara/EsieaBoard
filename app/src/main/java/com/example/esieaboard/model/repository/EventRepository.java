package com.example.esieaboard.model.repository;

import android.app.Application;
import androidx.lifecycle.LiveData;
import com.example.esieaboard.database.dao.EventDAO;
import com.example.esieaboard.database.db.AppDatabase;
import com.example.esieaboard.model.entities.Event;
import com.example.esieaboard.model.entities.Subscription;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class EventRepository {
    private EventDAO eventDAO;
    private LiveData<List<Event>> allEvents;
    private ExecutorService executor;

    public EventRepository(Application application) {
        AppDatabase database = AppDatabase.getInstance(application);
        this.eventDAO = database.eventDAO();
        this.allEvents = eventDAO.getAll();
        this.executor = Executors.newSingleThreadExecutor();
    }

    public void insert(Event event) {
        executor.execute(() -> eventDAO.insert(event));
    }

    public void update(Event event) {
        executor.execute(() -> eventDAO.update(event));
    }

    public void delete(Event event) {
        executor.execute(() -> eventDAO.delete(event));
    }

    public LiveData<List<Event>> getAll() {
        return allEvents;
    }

    public LiveData<List<Event>> getAllByClub(int clubId) {
        return eventDAO.getAllByClub(clubId);
    }

    public LiveData<List<Event>> getAllByUser(int userId) {
        return eventDAO.getAllByUser(userId);
    }

    public LiveData<Event> get(int id) {
        return eventDAO.get(id);
    }

    public void shutDownExecutor() {
        executor.shutdown();
    }
}
