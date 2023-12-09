package com.example.esieaboard;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.media.metrics.Event;
import android.widget.Toast;
import androidx.annotation.Nullable;
import com.example.esieaboard.models.*;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;

public class DataBaseHelper extends SQLiteOpenHelper {

    public static final String USER_TABLE = "USERS";
    public static final String COLUMN_NAME = "NAME";
    public static final String COLUMN_FIRST_NAME = "FIRST_NAME";
    public static final String COLUMN_LAST_NAME = "LAST_NAME";
    public static final String COLUMN_EMAIL_ADDRESS = "EMAIL_ADDRESS";
    public static final String COLUMN_PASSWORD_HASH = "PASSWORD_HASH";
    public static final String COLUMN_ID = "ID";
    public static final String CLUB_TABLE = "CLUBS";
    public static final String COLUMN_DESCRIPTION = "DESCRIPTION";
    public static final String COLUMN_CLUB_ID = "CLUB_ID";
    public static final String COLUMN_DATE = "DATE";
    public static final String COLUMN_LOCATION = "LOCATION";
    public static final String COLUMN_CAPACITY = "CAPACITY";
    public static final String COLUMN_USER_ID = "USER_ID";
    public static final String COLUMN_RIGHTS = "RIGHTS";
    public static final String SUBSCRIPTION_TABLE = "SUBSCRIPTIONS";
    public static final String ADMINISTRATOR_TABLE = "ADMINISTRATORS";
    public static final String EVENT_TABLE = "EVENTS";
    public static final String COLUMN_NATURE = "NATURE";
    private static final String ATTENDANCE_TABLE = "ATTENDANCE";
    private static final String COLUMN_EVENT_ID = "EVENT_ID";
    private static final String COLUMN_STATUS = "STATUS";

    public DataBaseHelper(@Nullable Context context) {
        super(context, "esieaboard.db", null, 4);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String createUserTable = "CREATE TABLE " + USER_TABLE +
                " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                COLUMN_FIRST_NAME + " VARCHAR(128)," +
                COLUMN_LAST_NAME + " VARCHAR(128)," +
                COLUMN_EMAIL_ADDRESS + " VARCHAR(128) NOT NULL," +
                COLUMN_PASSWORD_HASH + " VARCHAR(128) NOT NULL," +
                COLUMN_DESCRIPTION + " TEXT);\n";

        String createClubTable = "CREATE TABLE " + CLUB_TABLE +
                " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                COLUMN_NAME + " VARCHAR(128) NOT NULL, " +
                COLUMN_EMAIL_ADDRESS + " VARCHAR(128) NOT NULL," +
                COLUMN_DESCRIPTION + " TEXT NOT NULL);\n";

        String createEventTable = "CREATE TABLE " + EVENT_TABLE +
                " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                COLUMN_CLUB_ID + " INT NOT NULL, " +
                COLUMN_NAME + " VARCHAR(128) NOT NULL, " +
                COLUMN_DESCRIPTION + " TEXT NOT NULL, " +
                COLUMN_DATE + " DATETIME NOT NULL, " +
                COLUMN_LOCATION + " VARCHAR(128) NOT NULL, " +
                COLUMN_CAPACITY + " INT, " +
                "FOREIGN KEY (" + COLUMN_CLUB_ID + ")" +
                "REFERENCES " + CLUB_TABLE + " (" + COLUMN_ID + "));\n";

        String createAdministratorTable = "CREATE TABLE " + ADMINISTRATOR_TABLE +
                " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                COLUMN_USER_ID + " INT NOT NULL, " +
                COLUMN_CLUB_ID + " INT NOT NULL, " +
                COLUMN_RIGHTS + " INT NOT NULL, " +
                "FOREIGN KEY (" + COLUMN_USER_ID + ") " +
                "REFERENCES " + USER_TABLE + " (" + COLUMN_ID + "), " +
                "FOREIGN KEY (" + COLUMN_CLUB_ID + ") " +
                "REFERENCES " + CLUB_TABLE + " (" + COLUMN_ID + ")" +
                ");";

        String createSubscriptionTable = "CREATE TABLE " + SUBSCRIPTION_TABLE +
                "(" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                COLUMN_USER_ID + " INT NOT NULL, " +
                COLUMN_CLUB_ID + " INT NOT NULL, " +
                COLUMN_NATURE + " INT NOT NULL, " +
                "FOREIGN KEY (" + COLUMN_USER_ID + ") " +
                "REFERENCES " + USER_TABLE + " (" + COLUMN_ID + "), " +
                "FOREIGN KEY (" + COLUMN_CLUB_ID + ") " +
                "REFERENCES " + CLUB_TABLE + " (" + COLUMN_ID + ") " +
                ");";

        String createAttendanceTable = "CREATE TABLE " + ATTENDANCE_TABLE +
                "(" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                COLUMN_USER_ID + " INT NOT NULL, " +
                COLUMN_EVENT_ID + " INT NOT NULL, " +
                COLUMN_STATUS + " INT NOT NULL, " +
                "FOREIGN KEY (" + COLUMN_USER_ID + ") " +
                "REFERENCES " + USER_TABLE + " (" + COLUMN_ID + "), " +
                "FOREIGN KEY (" + COLUMN_EVENT_ID + ") " +
                "REFERENCES " + EVENT_TABLE + " (" + COLUMN_ID + ") " +
                ");";

        sqLiteDatabase.execSQL(createUserTable);
        sqLiteDatabase.execSQL(createClubTable);
        sqLiteDatabase.execSQL(createEventTable);
        sqLiteDatabase.execSQL(createAdministratorTable);
        sqLiteDatabase.execSQL(createSubscriptionTable);
        sqLiteDatabase.execSQL(createAttendanceTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + USER_TABLE);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + CLUB_TABLE);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + EVENT_TABLE);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + ADMINISTRATOR_TABLE);
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

    public boolean addAdministrator(AdministratorModel administratorModel) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_CLUB_ID, administratorModel.getClubId());
        cv.put(COLUMN_USER_ID, administratorModel.getUserId());
        cv.put(COLUMN_RIGHTS, administratorModel.getRights());

        long insert = sqLiteDatabase.insert(ADMINISTRATOR_TABLE, null, cv);

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

            user = new UserModel(userId, firstName, lastName, emailAddress, userPassword, userDescription);
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

            user = new UserModel(userId, firstName, lastName, emailAddress, userPassword, userDescription);
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
    public SubscriptionModel getSubscriptionByClubId(int clubId) {
        SQLiteDatabase db = this.getReadableDatabase();
        SubscriptionModel subscription = null;

        String query = "SELECT * FROM " + SUBSCRIPTION_TABLE +
                " WHERE " + COLUMN_CLUB_ID + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(clubId)});

        if(cursor.moveToFirst()) {
            int id = cursor.getInt(cursor.getColumnIndex(COLUMN_ID));
            int userId = cursor.getInt(cursor.getColumnIndex(COLUMN_USER_ID));
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
