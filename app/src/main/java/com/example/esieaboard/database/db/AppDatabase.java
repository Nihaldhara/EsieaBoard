package com.example.esieaboard.database.db;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import com.example.esieaboard.database.dao.*;
import com.example.esieaboard.model.entities.*;

@Database(entities = {Attendance.class, Club.class, Event.class, Subscription.class, User.class}, version = 9)
public abstract class AppDatabase extends RoomDatabase {
    public abstract AttendanceDAO attendanceDAO();

    public abstract ClubDAO clubDAO();

    public abstract EventDAO eventDAO();

    public abstract SubscriptionDAO subscriptionDAO();

    public abstract UserDAO userDAO();

    public static final String DATABASE_NAME = "esieaboard.db";

    private static volatile AppDatabase INSTANCE;

    public static synchronized AppDatabase getInstance(final Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                AppDatabase.class, DATABASE_NAME)
                        .fallbackToDestructiveMigration()
                        .addCallback(roomCallback)
                        .build();
            }
        }
        return INSTANCE;
    }

    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback() {
        public void onCreate(androidx.sqlite.db.SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDbAsyncTask(INSTANCE).execute();
        }

        public void onOpen(androidx.sqlite.db.SupportSQLiteDatabase db) {
            super.onOpen(db);
        }
    };

    private static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void> {
        private AttendanceDAO attendanceDAO;
        private ClubDAO clubDAO;
        private EventDAO eventDAO;
        private SubscriptionDAO subscriptionDAO;
        private UserDAO userDAO;

        private PopulateDbAsyncTask(AppDatabase db) {
            attendanceDAO = db.attendanceDAO();
            clubDAO = db.clubDAO();
            eventDAO = db.eventDAO();
            subscriptionDAO = db.subscriptionDAO();
            userDAO = db.userDAO();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            userDAO.insert(new User("Orlina", "Joly", "ojoly@et.esiea.fr", "password",
                    "Application creator, music enthusiast and a bit addicted to video games.", 2));
            userDAO.insert(new User("Hugh", "Jackman", "hjackman@et.esiea.fr", "password",
                    "The real Wolverine`s account. Beware fo the claws.", 1));
            userDAO.insert(new User("Jacques", "Dutronc", "jdutronc@et.esiea.fr", "password",
                    "Le roi de la chanson française (pas vraiment, mais moi je l`aime bien).", 0));
            return null;
        }
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }
}
