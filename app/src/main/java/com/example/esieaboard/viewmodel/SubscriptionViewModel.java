package com.example.esieaboard.viewmodel;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import com.example.esieaboard.model.entities.Subscription;
import com.example.esieaboard.model.repositories.SubscriptionRepository;

import java.util.List;

public class SubscriptionViewModel extends AndroidViewModel {
    private SubscriptionRepository subscriptionRepository;
    private LiveData<List<Subscription>> allSubscriptions;

    public SubscriptionViewModel(Application application) {
        super(application);
        subscriptionRepository = new SubscriptionRepository(application);
        allSubscriptions = subscriptionRepository.getAll();
    }

    public void insert(Subscription subscription) {
        subscriptionRepository.insert(subscription);
    }

    public LiveData<Subscription> getById(int id, int clubId) {
        return subscriptionRepository.getById(id, clubId);
    }

    public void update(Subscription subscription) {
        subscriptionRepository.update(subscription);
    }

    public void delete(Subscription subscription) {
        subscriptionRepository.delete(subscription);
    }

    public LiveData<List<Subscription>> getAll() {
        return allSubscriptions;
    }

    public void unsubscribeUser(int userId, int clubId) {
        subscriptionRepository.unsubscribeUser(userId, clubId);
    }

    public LiveData<Subscription> getByUserId(int userId, int clubId) {
        return subscriptionRepository.getByUserId(userId, clubId);
    }
}
