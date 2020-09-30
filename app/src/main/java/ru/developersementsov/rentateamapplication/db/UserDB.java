package ru.developersementsov.rentateamapplication.db;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

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
                            .addCallback(sRoomDatabaseCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    private static RoomDatabase.Callback sRoomDatabaseCallback =
            new RoomDatabase.Callback(){

                @Override
                public void onOpen (@NonNull SupportSQLiteDatabase db){
                    super.onOpen(db);
                    new PopulateDbAsync(INSTANCE).execute();
                }
            };

    /**
     * Populate the database in the background.
     */
    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {

        private final UserDao mDao;

        //String[] users = {"dolphin", "crocodile", "cobra"};

        PopulateDbAsync(UserDB db) {
            mDao = db.getUserDao();
        }

        @Override
        protected Void doInBackground(final Void... params) {
            // Start the app with a clean database every time.
            // Not needed if you only populate the database
            // when it is first created
            mDao.deleteAll();

//            for (int i = 0; i <= users.length - 1; i++) {
//                User user = new User(users[i]);
//                mDao.addUser(user);
//            }
            return null;
        }
    }
}

