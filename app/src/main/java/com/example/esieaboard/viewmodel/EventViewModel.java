package com.example.esieaboard.viewmodel;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import com.example.esieaboard.model.entities.Event;
import com.example.esieaboard.model.entities.Subscription;
import com.example.esieaboard.model.repository.EventRepository;
import com.example.esieaboard.model.repository.SubscriptionRepository;

import java.util.List;

public class EventViewModel extends AndroidViewModel {
    private EventRepository eventRepository;
    private SubscriptionRepository subscriptionRepository;
    private LiveData<List<Event>> allEvents;

    public EventViewModel(Application application) {
        super(application);
        eventRepository = new EventRepository(application);
        subscriptionRepository = new SubscriptionRepository(application);
        allEvents = eventRepository.getAll();
    }

    public void insert(Event event) {
        eventRepository.insert(event);
    }

    public void update(Event event) {
        eventRepository.update(event);
    }

    public void delete(Event event) {
        eventRepository.delete(event);
    }

    public LiveData<List<Event>> getAll() {
        return allEvents;
    }

    public LiveData<List<Event>> getAllByClub(int clubId) {
        return eventRepository.getAllByClub(clubId);
    }

    public LiveData<List<Event>> getAllByUser(int userId) {
        return eventRepository.getAllByUser(userId);
    }

    public LiveData<Event> get(int id) {
        return eventRepository.get(id);
    }
}
