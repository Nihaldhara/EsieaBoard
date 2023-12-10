package com.example.esieaboard;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.media.metrics.Event;
import android.util.Log;
import android.widget.Toast;
import androidx.annotation.Nullable;
import com.example.esieaboard.models.*;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import android.content.Context;

public class DataBaseHelper extends SQLiteOpenHelper {

    public static final String USER_TABLE = "Users";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_FIRST_NAME = "first_name";
    public static final String COLUMN_LAST_NAME = "last_name";
    public static final String COLUMN_EMAIL_ADDRESS = "email_address";
    public static final String COLUMN_PASSWORD_HASH = "password_hash";
    public static final String COLUMN_ID = "id";
    public static final String CLUB_TABLE = "Clubs";
    public static final String COLUMN_DESCRIPTION = "description";
    public static final String COLUMN_CLUB_ID = "club_id";
    public static final String COLUMN_DATE = "date";
    public static final String COLUMN_LOCATION = "location";
    public static final String COLUMN_CAPACITY = "capacity";
    public static final String COLUMN_USER_ID = "user_id";
    public static final String COLUMN_RIGHTS = "rights";
    public static final String SUBSCRIPTION_TABLE = "Subscriptions";
    public static final String EVENT_TABLE = "Events";
    public static final String COLUMN_NATURE = "nature";
    private static final String ATTENDANCE_TABLE = "Attendance";
    private static final String COLUMN_EVENT_ID = "envet_id";
    private static final String COLUMN_STATUS = "status";

    private final Context context;

    public DataBaseHelper(@Nullable Context context) {
        super(context, "esieaboard.db", null, 13);
        this.context = context;
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        if (!db.isReadOnly()) {
            db.execSQL("PRAGMA foreign_keys=ON;");
        }
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        String createUserTable = "CREATE TABLE " + USER_TABLE +
                " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                COLUMN_FIRST_NAME + " VARCHAR(128)," +
                COLUMN_LAST_NAME + " VARCHAR(128)," +
                COLUMN_EMAIL_ADDRESS + " VARCHAR(128) NOT NULL," +
                COLUMN_PASSWORD_HASH + " VARCHAR(128) NOT NULL," +
                COLUMN_DESCRIPTION + " TEXT," +
                COLUMN_RIGHTS + " INT NOT NULL" +
                ");\n";

        String createClubTable = "CREATE TABLE " + CLUB_TABLE +
                " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                COLUMN_NAME + " VARCHAR(128) NOT NULL, " +
                COLUMN_EMAIL_ADDRESS + " VARCHAR(128) NOT NULL," +
                COLUMN_DESCRIPTION + " TEXT NOT NULL" +
                ");\n";

        String createEventTable = "CREATE TABLE " + EVENT_TABLE +
                " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                COLUMN_CLUB_ID + " INT NOT NULL, " +
                COLUMN_NAME + " VARCHAR(128) NOT NULL, " +
                COLUMN_DESCRIPTION + " TEXT NOT NULL, " +
                COLUMN_DATE + " DATETIME NOT NULL, " +
                COLUMN_LOCATION + " VARCHAR(128) NOT NULL, " +
                COLUMN_CAPACITY + " INT, " +
                "FOREIGN KEY (" + COLUMN_CLUB_ID + ")" +
                "REFERENCES " + CLUB_TABLE + " (" + COLUMN_ID + ") ON DELETE CASCADE" +
                ");\n";

        String createSubscriptionTable = "CREATE TABLE " + SUBSCRIPTION_TABLE +
                "(" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                COLUMN_USER_ID + " INT NOT NULL, " +
                COLUMN_CLUB_ID + " INT NOT NULL, " +
                COLUMN_NATURE + " INT NOT NULL, " +
                "FOREIGN KEY (" + COLUMN_USER_ID + ") " +
                "REFERENCES " + USER_TABLE + " (" + COLUMN_ID + ") ON DELETE CASCADE, " +
                "FOREIGN KEY (" + COLUMN_CLUB_ID + ") " +
                "REFERENCES " + CLUB_TABLE + " (" + COLUMN_ID + ") ON DELETE CASCADE" +
                ");\n";

        String createAttendanceTable = "CREATE TABLE " + ATTENDANCE_TABLE +
                "(" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                COLUMN_USER_ID + " INT NOT NULL, " +
                COLUMN_EVENT_ID + " INT NOT NULL, " +
                COLUMN_STATUS + " INT NOT NULL, " +
                "FOREIGN KEY (" + COLUMN_USER_ID + ") " +
                "REFERENCES " + USER_TABLE + " (" + COLUMN_ID + ") ON DELETE CASCADE," +
                "FOREIGN KEY (" + COLUMN_EVENT_ID + ") " +
                "REFERENCES " + EVENT_TABLE + " (" + COLUMN_ID + ") ON DELETE CASCADE" +
                ");\n";

        String initializeUsers = "INSERT INTO " + USER_TABLE + "(first_name, last_name, email_address, password_hash, description, rights)" +
                "VALUES ('Orlina', 'Joly', 'ojoly@et.esiea.fr', 'password', " +
                "'Application creator, music enthusiast and a bit addicted to video games.', 2),\n" +
                "('Hugh', 'Jackman', 'hjackman@et.esiea.fr', 'password', " +
                "'The real Wolverine`s account. Beware fo the claws.', 1),\n" +
                "('Jacques', 'Dutronc', 'jdutronc@et.esiea.fr', 'password', " +
                "'Le roi de la chanson française (pas vraiment, mais moi je l`aime bien).', 0);";

        sqLiteDatabase.execSQL(createUserTable);
        sqLiteDatabase.execSQL(createClubTable);
        sqLiteDatabase.execSQL(createEventTable);
        sqLiteDatabase.execSQL(createSubscriptionTable);
        sqLiteDatabase.execSQL(createAttendanceTable);
        sqLiteDatabase.execSQL(initializeUsers);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + USER_TABLE);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + CLUB_TABLE);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + EVENT_TABLE);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + SUBSCRIPTION_TABLE);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + ATTENDANCE_TABLE);

        onCreate(sqLiteDatabase);
    }

    public boolean addUser(UserModel userModel) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_FIRST_NAME, userModel.getFirstName());
        cv.put(COLUMN_LAST_NAME, userModel.getLastName());
        cv.put(COLUMN_EMAIL_ADDRESS, userModel.getEmailAddress());
        cv.put(COLUMN_PASSWORD_HASH, userModel.getPassword());
        cv.put(COLUMN_RIGHTS, userModel.getRights());

        long insert = sqLiteDatabase.insert(USER_TABLE, null, cv);

        return insert != -1;
    }

    public boolean addClub(ClubModel clubModel) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_NAME, clubModel.getName());
        cv.put(COLUMN_EMAIL_ADDRESS, clubModel.getEmail());
        cv.put(COLUMN_DESCRIPTION, clubModel.getDescription());

        long insert = sqLiteDatabase.insert(CLUB_TABLE, null, cv);

        return insert != -1;
    }

    public boolean addEvent(EventModel eventModel) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_CLUB_ID, eventModel.getClubId());
        cv.put(COLUMN_NAME, eventModel.getName());
        cv.put(COLUMN_DESCRIPTION, eventModel.getDescription());
        cv.put(COLUMN_DATE, eventModel.getDate());
        cv.put(COLUMN_LOCATION, eventModel.getLocation());
        cv.put(COLUMN_CAPACITY, eventModel.getCapacity());

        long insert = sqLiteDatabase.insert(EVENT_TABLE, null, cv);

        return insert != -1;
    }

    public boolean addSubscription(SubscriptionModel subscriptionModel) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_CLUB_ID, subscriptionModel.getClubId());
        cv.put(COLUMN_USER_ID, subscriptionModel.getUserId());
        cv.put(COLUMN_NATURE, subscriptionModel.getNature());

        long insert = sqLiteDatabase.insert(SUBSCRIPTION_TABLE, null, cv);

        return insert != -1;
    }

    public boolean addAttendance(AttendanceModel attendanceModel) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_EVENT_ID, attendanceModel.getEventId());
        cv.put(COLUMN_USER_ID, attendanceModel.getUserId());
        cv.put(COLUMN_STATUS, attendanceModel.getStatus());

        long insert = sqLiteDatabase.insert(ATTENDANCE_TABLE, null, cv);

        return insert != -1;
    }

    public Cursor readSubscriptionData() {
        String query = "SELECT * FROM " + SUBSCRIPTION_TABLE;
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();

        Cursor cursor = null;
        if (sqLiteDatabase != null) {
            cursor = sqLiteDatabase.rawQuery(query, null);
        }
        return cursor;
    }

    public Cursor readUserData() {
        String query = "SELECT * FROM " + USER_TABLE;
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();

        Cursor cursor = null;
        if (sqLiteDatabase != null) {
            cursor = sqLiteDatabase.rawQuery(query, null);
        }
        return cursor;
    }

    public Cursor readClubData() {
        String query = "SELECT * FROM " + CLUB_TABLE;
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();

        Cursor cursor = null;
        if (sqLiteDatabase != null) {
            cursor = sqLiteDatabase.rawQuery(query, null);
        }
        return cursor;
    }

    public Cursor readEventData() {
        String query = "SELECT * FROM " + EVENT_TABLE;
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();

        Cursor cursor = null;
        if (sqLiteDatabase != null) {
            cursor = sqLiteDatabase.rawQuery(query, null);
        }
        return cursor;
    }

    public Cursor readAttendanceData() {
        String query = "SELECT * FROM " + ATTENDANCE_TABLE;
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();

        Cursor cursor = null;
        if (sqLiteDatabase != null) {
            cursor = sqLiteDatabase.rawQuery(query, null);
        }
        return cursor;
    }

    public void updateClub(ClubModel club) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_NAME, club.getName());
        cv.put(COLUMN_EMAIL_ADDRESS, club.getEmail());
        cv.put(COLUMN_DESCRIPTION, club.getDescription());

        sqLiteDatabase.update(CLUB_TABLE, cv, " id=?", new String[]{String.valueOf(club.getId())});
    }

    public void updateUser(UserModel user) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_FIRST_NAME, user.getFirstName());
        cv.put(COLUMN_LAST_NAME, user.getLastName());
        cv.put(COLUMN_EMAIL_ADDRESS, user.getEmailAddress());
        cv.put(COLUMN_PASSWORD_HASH, user.getPassword());
        cv.put(COLUMN_DESCRIPTION, user.getDescription());
        cv.put(COLUMN_RIGHTS, user.getRights());

        sqLiteDatabase.update(USER_TABLE, cv, " id=?", new String[]{String.valueOf(user.getId())});
    }

    public void updateEvent(EventModel event) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_NAME, event.getName());
        cv.put(COLUMN_DATE, event.getDate());
        cv.put(COLUMN_LOCATION, event.getLocation());
        cv.put(COLUMN_DESCRIPTION, event.getDescription());
        cv.put(COLUMN_CAPACITY, String.valueOf(event.getCapacity()));

        sqLiteDatabase.update(EVENT_TABLE, cv, " id=?", new String[]{String.valueOf(event.getId())});
    }

    @SuppressLint("Range")
    public UserModel getUserByEmailAndPassword(String email, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        UserModel user = null;

        String query = "SELECT * FROM " + USER_TABLE +
                " WHERE " + COLUMN_EMAIL_ADDRESS + " = ? AND " + COLUMN_PASSWORD_HASH + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{email, password});

        if (cursor.moveToFirst()) {
            int userId = cursor.getInt(cursor.getColumnIndex(COLUMN_ID));
            String firstName = cursor.getString(cursor.getColumnIndex(COLUMN_FIRST_NAME));
            String lastName = cursor.getString(cursor.getColumnIndex(COLUMN_LAST_NAME));
            String emailAddress = cursor.getString(cursor.getColumnIndex(COLUMN_EMAIL_ADDRESS));
            String userPassword = cursor.getString(cursor.getColumnIndex(COLUMN_PASSWORD_HASH));
            String userDescription = cursor.getString(cursor.getColumnIndex(COLUMN_DESCRIPTION));
            int userRights = cursor.getInt(cursor.getColumnIndex(COLUMN_RIGHTS));

            user = new UserModel(userId, firstName, lastName, emailAddress, userPassword, userDescription, userRights);
        }

        cursor.close();
        db.close();

        return user;
    }

    @SuppressLint("Range")
    public UserModel getUserByEmail(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        UserModel user = null;

        String query = "SELECT * FROM " + USER_TABLE +
                " WHERE " + COLUMN_EMAIL_ADDRESS + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{email});

        if (cursor.moveToFirst()) {
            int userId = cursor.getInt(cursor.getColumnIndex(COLUMN_ID));
            String firstName = cursor.getString(cursor.getColumnIndex(COLUMN_FIRST_NAME));
            String lastName = cursor.getString(cursor.getColumnIndex(COLUMN_LAST_NAME));
            String emailAddress = cursor.getString(cursor.getColumnIndex(COLUMN_EMAIL_ADDRESS));
            String userPassword = cursor.getString(cursor.getColumnIndex(COLUMN_PASSWORD_HASH));
            String userDescription = cursor.getString(cursor.getColumnIndex(COLUMN_DESCRIPTION));
            int userRights = cursor.getInt(cursor.getColumnIndex(COLUMN_RIGHTS));

            user = new UserModel(userId, firstName, lastName, emailAddress, userPassword, userDescription, userRights);
        }

        cursor.close();
        db.close();

        return user;
    }

    @SuppressLint("Range")
    public UserModel getUserById(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        UserModel user = null;

        String query = "SELECT * FROM " + USER_TABLE +
                " WHERE " + COLUMN_ID + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(id)});

        if (cursor.moveToFirst()) {
            int userId = cursor.getInt(cursor.getColumnIndex(COLUMN_ID));
            String firstName = cursor.getString(cursor.getColumnIndex(COLUMN_FIRST_NAME));
            String lastName = cursor.getString(cursor.getColumnIndex(COLUMN_LAST_NAME));
            String emailAddress = cursor.getString(cursor.getColumnIndex(COLUMN_EMAIL_ADDRESS));
            String userPassword = cursor.getString(cursor.getColumnIndex(COLUMN_PASSWORD_HASH));
            String userDescription = cursor.getString(cursor.getColumnIndex(COLUMN_DESCRIPTION));
            int userRights = cursor.getInt(cursor.getColumnIndex(COLUMN_RIGHTS));

            user = new UserModel(userId, firstName, lastName, emailAddress, userPassword, userDescription, userRights);
        }

        cursor.close();
        db.close();

        return user;
    }

    @SuppressLint("Range")
    public ClubModel getClubById(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        ClubModel club = null;

        String query = "SELECT * FROM " + CLUB_TABLE +
                " WHERE " + COLUMN_ID + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(id)});

        if (cursor.moveToFirst()) {
            int clubId = cursor.getInt(cursor.getColumnIndex(COLUMN_ID));
            String name = cursor.getString(cursor.getColumnIndex(COLUMN_NAME));
            String emailAddress = cursor.getString(cursor.getColumnIndex(COLUMN_EMAIL_ADDRESS));
            String description = cursor.getString(cursor.getColumnIndex(COLUMN_DESCRIPTION));

            club = new ClubModel(clubId, name, emailAddress, description);
        }

        cursor.close();
        db.close();

        return club;
    }

    @SuppressLint("Range")
    public EventModel getEventById(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        EventModel event = null;

        String query = "SELECT * FROM " + EVENT_TABLE +
                " WHERE " + COLUMN_ID + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(id)});

        if (cursor.moveToFirst()) {
            int eventId = cursor.getInt(cursor.getColumnIndex(COLUMN_ID));
            int clubId = cursor.getInt(cursor.getColumnIndex(COLUMN_CLUB_ID));
            String name = cursor.getString(cursor.getColumnIndex(COLUMN_NAME));
            String description = cursor.getString(cursor.getColumnIndex(COLUMN_DESCRIPTION));
            String date = cursor.getString(cursor.getColumnIndex(COLUMN_DATE));
            String location = cursor.getString(cursor.getColumnIndex(COLUMN_LOCATION));
            int capacity = cursor.getInt(cursor.getColumnIndex(COLUMN_CAPACITY));

            event = new EventModel(eventId, clubId, name, description, date, location, capacity);
        }

        cursor.close();
        db.close();

        return event;
    }

    @SuppressLint("Range")
    public SubscriptionModel getSubscriptionByClubAndUserId(int clubId, int userId) {
        SQLiteDatabase db = this.getReadableDatabase();
        SubscriptionModel subscription = null;

        String query = "SELECT * FROM " + SUBSCRIPTION_TABLE +
                " WHERE " + COLUMN_CLUB_ID + " = ? AND " + COLUMN_USER_ID + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(clubId), String.valueOf(userId)});

        if(cursor.moveToFirst()) {
            int id = cursor.getInt(cursor.getColumnIndex(COLUMN_ID));
            int nature = cursor.getInt(cursor.getColumnIndex(COLUMN_NATURE));

            subscription = new SubscriptionModel(id, userId, clubId, nature);
        }

        cursor.close();
        db.close();

        return subscription;
    }

    @SuppressLint("Range")
    public AttendanceModel getAttendanceByEventId(int eventId) {
        SQLiteDatabase db = this.getReadableDatabase();
        AttendanceModel attendanceModel = null;

        String query = "SELECT * FROM " + ATTENDANCE_TABLE +
                " WHERE " + COLUMN_EVENT_ID + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(eventId)});

        if(cursor.moveToFirst()) {
            int id = cursor.getInt(cursor.getColumnIndex(COLUMN_ID));
            int userId = cursor.getInt(cursor.getColumnIndex(COLUMN_USER_ID));
            int status = cursor.getInt(cursor.getColumnIndex(COLUMN_STATUS));

            attendanceModel = new AttendanceModel(id, userId, eventId, status);
        }

        cursor.close();
        db.close();

        return attendanceModel;
    }

    public void deleteUserRow(String id) {
        SQLiteDatabase database = this.getWritableDatabase();
        long result = database.delete(USER_TABLE, " id=?", new String[]{id});
    }
    public void deleteClubRow(String id) {
        SQLiteDatabase database = this.getWritableDatabase();
        long result = database.delete(CLUB_TABLE, " id=?", new String[]{id});
    }
    public void deleteEventRow(String id) {
        SQLiteDatabase database = this.getWritableDatabase();
        long result = database.delete(EVENT_TABLE, " id=?", new String[]{id});
    }

    public void deleteSubscriptionRow(String id) {
        SQLiteDatabase database = this.getWritableDatabase();
        long result = database.delete(SUBSCRIPTION_TABLE, " id=?", new String[]{id});
    }

    public void deleteAttendanceRow(String id) {
        SQLiteDatabase database = this.getWritableDatabase();
        long result = database.delete(ATTENDANCE_TABLE, " id=?", new String[]{id});
    }
}
