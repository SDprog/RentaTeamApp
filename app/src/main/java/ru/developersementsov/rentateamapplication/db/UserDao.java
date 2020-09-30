package ru.developersementsov.rentateamapplication.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import io.reactivex.Flowable;
import ru.developersementsov.rentateamapplication.model.User;

@Dao
public interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addUser(User user);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addAll(User... users);

    @Delete
    void delete(User user);

    @Query("DELETE FROM user_table")
    void deleteAll();

    @Query("SELECT * FROM user_table")
    LiveData<List<User>> getAllUsers();
}
