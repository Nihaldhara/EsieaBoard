package com.example.esieaboard.model.repository;

import android.app.Application;
import androidx.lifecycle.LiveData;
import com.example.esieaboard.database.dao.SubscriptionDAO;
import com.example.esieaboard.database.db.AppDatabase;
import com.example.esieaboard.model.entities.Subscription;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SubscriptionRepository {
    private SubscriptionDAO subscriptionDAO;
    private LiveData<List<Subscription>> allSubscriptions;
    private ExecutorService executor;

    public SubscriptionRepository(Application application) {
        AppDatabase database = AppDatabase.getInstance(application);
        this.subscriptionDAO = database.subscriptionDAO();
        this.allSubscriptions = subscriptionDAO.getAll();
        this.executor = Executors.newSingleThreadExecutor();
    }

    public void insert(Subscription subscription) {
        executor.execute(() -> subscriptionDAO.insert(subscription));
    }

    public void update(Subscription subscription) {
        executor.execute(() -> subscriptionDAO.update(subscription));
    }

    public void delete(Subscription subscription) {
        executor.execute(() -> subscriptionDAO.delete(subscription));
    }

    public LiveData<Subscription> getById(int id, int clubId) {
        return subscriptionDAO.getById(id, clubId);
    }

    public LiveData<List<Subscription>> getAllByUser(int userId) {
        return subscriptionDAO.getAllByUser(userId);
    }

    public LiveData<List<Subscription>> getAll() {
        return allSubscriptions;
    }

    public LiveData<Subscription> getByUserId(int userId, int clubId) {
        return subscriptionDAO.getByUserId(userId, clubId);
    }

    public void unsubscribeUser(int userId, int clubId) {
        executor.execute(() -> subscriptionDAO.unsubscribeUser(userId, clubId));
    }

    public void shutDownExecutor() {
        executor.shutdown();
    }
}
