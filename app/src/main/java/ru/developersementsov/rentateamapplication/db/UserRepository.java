package ru.developersementsov.rentateamapplication.db;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

import io.reactivex.Flowable;
import ru.developersementsov.rentateamapplication.model.User;

public class UserRepository {
    private UserDao mUserDao;
    private LiveData<List<User>> mAllUsers;

    UserRepository(Application application) {
        UserDB db = UserDB.getDatabase(application);
        mUserDao = db.getUserDao();
        mAllUsers = mUserDao.getAllUsers();
    }

    public LiveData<List<User>> getAllUsers() {
        return mAllUsers;
    }

    public void insert(User user) {
        new insertAsyncTask(mUserDao).execute(user);
    }

    private static class insertAsyncTask extends AsyncTask<User, Void, Void> {

        private UserDao mAsyncTaskDao;

        insertAsyncTask(UserDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final User... params) {
            mAsyncTaskDao.addUser(params[0]);
            return null;
        }
    }
}