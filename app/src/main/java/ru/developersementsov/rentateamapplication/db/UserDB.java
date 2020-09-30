package ru.developersementsov.rentateamapplication.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import ru.developersementsov.rentateamapplication.model.User;

@Database(entities = {User.class}, version = 1, exportSchema = false)
public abstract class UserDB extends RoomDatabase {

    public abstract UserDao getUserDao();

    private static UserDB INSTANCE;

    static UserDB getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (UserDB.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            UserDB.class, "user_database")
                            // Wipes and rebuilds instead of migrating
                            // if no Migration object.
                            // Migration is not part of this practical.
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}

