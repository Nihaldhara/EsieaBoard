package com.example.esieaboard.viewmodel;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import com.example.esieaboard.model.entities.User;
import com.example.esieaboard.model.repository.UserRepository;

import java.util.List;

public class UserViewModel extends AndroidViewModel {
    private UserRepository userRepository;
    private LiveData<List<User>> allUsers;

    public UserViewModel(Application application) {
        super(application);
        userRepository = new UserRepository(application);
        allUsers = userRepository.getAll();
    }

    public void insert(User user) {
        userRepository.insert(user);
    }

    public void update(User user) {
        userRepository.update(user);
    }

    public void delete(User user) {
        userRepository.delete(user);
    }

    public LiveData<User> get(String email) {
        return userRepository.get(email);
    }

    public LiveData<User> get(String email, String password) {
        return userRepository.get(email, password);
    }

    public LiveData<User> get(int id) {
        return userRepository.get(id);
    }

    public LiveData<List<User>> getAll() {
        return allUsers;
    }

    public void updateUserRights(User user, int rights) {
        userRepository.updateUserRights(user, rights);
    }
}
