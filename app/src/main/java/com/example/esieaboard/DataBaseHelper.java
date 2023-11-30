package com.example.esieaboard;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.media.metrics.Event;
import androidx.annotation.Nullable;

public class DataBaseHelper extends SQLiteOpenHelper {

    public static final String USER_TABLE = "USERS";
    public static final String COLUMN_NAME = "NAME";
    public static final String COLUMN_FIRST_NAME = "FIRST_NAME";
    public static final String COLUMN_LAST_NAME = "LAST_NAME";
    public static final String COLUMN_EMAIL_ADDRESS = "EMAIL_ADRESS";
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
        super(context, "database.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String createUserTable = "CREATE TABLE " + USER_TABLE +
                " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                COLUMN_FIRST_NAME + " VARCHAR(128) NOT NULL," +
                COLUMN_LAST_NAME + " VARCHAR(128) NOT NULL," +
                COLUMN_EMAIL_ADDRESS + " VARCHAR(128) NOT NULL," +
                COLUMN_PASSWORD_HASH + " VARCHAR(128) NOT NULL);\n";

        String createClubTable = "CREATE TABLE " + CLUB_TABLE +
                " ("+ COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                COLUMN_NAME + " VARCHAR(128) NOT NULL, " +
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
}
