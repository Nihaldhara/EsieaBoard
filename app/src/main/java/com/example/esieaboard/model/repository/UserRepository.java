package com.example.esieaboard.model.repository;

import android.app.Application;
import androidx.lifecycle.LiveData;
import com.example.esieaboard.database.dao.UserDAO;
import com.example.esieaboard.database.db.AppDatabase;
import com.example.esieaboard.model.entities.User;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class UserRepository {
    private UserDAO userDAO;
    private LiveData<List<User>> allUsers;
    private ExecutorService executor;

    public UserRepository(Application application) {
        AppDatabase database = AppDatabase.getInstance(application);
        this.userDAO = database.userDAO();
        this.allUsers = userDAO.getAll();
        this.executor = Executors.newSingleThreadExecutor();
    }

    public void insert(User user) {
        executor.execute(() -> userDAO.insert(user));
    }

    public void update(User user) {
        executor.execute(() -> userDAO.update(user));
    }

    public void delete(User user) {
        executor.execute(() -> userDAO.delete(user));
    }

    public void deleteAll() {
        executor.execute(() -> userDAO.deleteAll());
    }

    public LiveData<User> get(String email) {
        return userDAO.get(email);
    }

    public LiveData<User> get(String email, String password) {
        return userDAO.get(email, password);
    }

    public LiveData<User> get(int id) {
        return userDAO.get(id);
    }

    public LiveData<List<User>> getAll() {
        return allUsers;
    }

    public void updateUserRights(User user, int rights) {
        user.setRights(rights);
        executor.execute(() -> userDAO.update(user));
    }

    public void shutDownExecutor() {
        executor.shutdown();
    }
}
