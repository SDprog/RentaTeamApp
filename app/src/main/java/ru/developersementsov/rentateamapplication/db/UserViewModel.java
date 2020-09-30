package ru.developersementsov.rentateamapplication.db;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import android.app.Application;
import android.os.Bundle;

import java.util.List;

import io.reactivex.Flowable;
import ru.developersementsov.rentateamapplication.model.User;

public class UserViewModel extends AndroidViewModel {

    private UserRepository mRepository;

    private LiveData<List<User>> mAllUsers;

    public UserViewModel (Application application) {
        super(application);
        mRepository = new UserRepository(application);
        mAllUsers = mRepository.getAllUsers();
    }

    public LiveData<List<User>> getAllUsers() { return mAllUsers; }

    public void insert(User user) { mRepository.insert(user); }
}
