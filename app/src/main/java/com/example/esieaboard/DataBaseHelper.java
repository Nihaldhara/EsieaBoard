package com.example.esieaboard;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;
import androidx.annotation.Nullable;
import com.example.esieaboard.models.*;

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

    public DataBaseHelper(@Nullable Context context) {
        super(context, "esieaboard.db", null, 3);
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

        sqLiteDatabase.execSQL(createUserTable);
        sqLiteDatabase.execSQL(createClubTable);
        sqLiteDatabase.execSQL(createEventTable);
        sqLiteDatabase.execSQL(createAdministratorTable);
        sqLiteDatabase.execSQL(createSubscriptionTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + USER_TABLE);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + CLUB_TABLE);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + EVENT_TABLE);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + ADMINISTRATOR_TABLE);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + SUBSCRIPTION_TABLE);

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

    Cursor readClubData() {
        String query = "SELECT * FROM " + CLUB_TABLE;
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();

        Cursor cursor = null;
        if (sqLiteDatabase != null) {
            cursor = sqLiteDatabase.rawQuery(query, null);
        }
        return cursor;
    }

    Cursor readEventData() {
        String query = "SELECT * FROM " + EVENT_TABLE;
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();

        Cursor cursor = null;
        if (sqLiteDatabase != null) {
            cursor = sqLiteDatabase.rawQuery(query, null);
        }
        return cursor;
    }

    void updateClub(String row_id, String name, String description) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_NAME, name);
        cv.put(COLUMN_DESCRIPTION, description);

        sqLiteDatabase.update(CLUB_TABLE, cv, " id=?", new String[]{row_id});
    }

    void updateUser(String row_id, String name, String email, String password, String description) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_FIRST_NAME, name);
        cv.put(COLUMN_EMAIL_ADDRESS, email);
        cv.put(COLUMN_PASSWORD_HASH, password);
        cv.put(COLUMN_DESCRIPTION, description);

        sqLiteDatabase.update(USER_TABLE, cv, " id=?", new String[]{row_id});
    }

    void updateEvent(String row_id, String name, String date, String location, String description, int capacity) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_NAME, name);
        cv.put(COLUMN_DATE, date);
        cv.put(COLUMN_LOCATION, location);
        cv.put(COLUMN_DESCRIPTION, description);
        cv.put(COLUMN_CAPACITY, String.valueOf(capacity));

        sqLiteDatabase.update(EVENT_TABLE, cv, " id=?", new String[]{row_id});
    }

    boolean checkEmail(String email) {
        SQLiteDatabase dataBase = this.getWritableDatabase();
        Cursor cursor = dataBase.rawQuery("Select * from " + USER_TABLE + " where email_adress = ?", new String[]{email});
        if (cursor.getCount() > 0) {
            return true;
        } else {
            return false;
        }
    }

    boolean checkEmailPassword(String email, String password) {
        SQLiteDatabase dataBase = this.getWritableDatabase();
        Cursor cursor = dataBase.rawQuery("Select * from " + USER_TABLE + " where " +
                COLUMN_EMAIL_ADDRESS + " = ? and " + COLUMN_PASSWORD_HASH + " = ?", new String[]{email, password});
        if (cursor.getCount() > 0) {
            return true;
        } else {
            return false;
        }
    }

    // Inside DataBaseHelper.java

    @SuppressLint("Range")
    // Inside DataBaseHelper.java

    public UserModel getUserByEmailAndPassword(String email, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        UserModel user = null;

        // Your SELECT query to retrieve all columns based on email and password
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


}
