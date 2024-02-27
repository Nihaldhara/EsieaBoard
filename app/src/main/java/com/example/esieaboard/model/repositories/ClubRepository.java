package com.example.esieaboard.model.repositories;

import android.app.Application;
import androidx.lifecycle.LiveData;
import com.example.esieaboard.model.local.ClubDAO;
import com.example.esieaboard.model.local.AppDatabase;
import com.example.esieaboard.model.entities.Club;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ClubRepository {
    private ClubDAO clubDAO;
    private LiveData<List<Club>> allClubs;
    private ExecutorService executor;

    public ClubRepository(Application application) {
        AppDatabase database = AppDatabase.getInstance(application);
        this.clubDAO = database.clubDAO();
        this.allClubs = clubDAO.getAll();
        this.executor = Executors.newSingleThreadExecutor();
    }

    public void insert(Club club) {
        executor.execute(() -> clubDAO.insert(club));
    }

    public void update(Club club) {
        executor.execute(() -> clubDAO.update(club));
    }

    public void delete(Club club) {
        executor.execute(() -> clubDAO.delete(club));
    }

    public LiveData<List<Club>> getAll() {
        return allClubs;
    }

    public LiveData<Club> get(int id) {
        return clubDAO.getById(id);
    }

    public void shutDownExecutor() {
        executor.shutdown();
    }
}
