package com.example.esieaboard.viewmodel;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import com.example.esieaboard.model.entities.Club;
import com.example.esieaboard.model.repository.ClubRepository;

import java.util.List;

public class ClubViewModel extends AndroidViewModel {
    private ClubRepository clubRepository;
    private LiveData<List<Club>> allClubs;

    public ClubViewModel(Application application) {
        super(application);
        clubRepository = new ClubRepository(application);
        allClubs = clubRepository.getAll();
    }

    public void insert(Club club) {
        clubRepository.insert(club);
    }

    public void update(Club club) {
        clubRepository.update(club);
    }

    public void delete(Club club) {
        clubRepository.delete(club);
    }

    public LiveData<List<Club>> getAll() {
        return allClubs;
    }

    public LiveData<Club> get(int id) {
        return clubRepository.get(id);
    }
}
