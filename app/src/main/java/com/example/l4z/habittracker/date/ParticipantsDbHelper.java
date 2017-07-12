package com.example.l4z.habittracker.date;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static com.example.l4z.habittracker.date.ParticipantsContract.ParticipantsEntry.COLUMN_AGE;
import static com.example.l4z.habittracker.date.ParticipantsContract.ParticipantsEntry.COLUMN_NAME;
import static com.example.l4z.habittracker.date.ParticipantsContract.ParticipantsEntry.COLUMN_RANK;
import static com.example.l4z.habittracker.date.ParticipantsContract.ParticipantsEntry.TABLE_NAME;
import static com.example.l4z.habittracker.date.ParticipantsContract.ParticipantsEntry._ID;

/**
 * Created by l4z on 11.07.2017.
 */

public class ParticipantsDbHelper extends SQLiteOpenHelper {

    public static final String LOG_TAG = ParticipantsDbHelper.class.getSimpleName();

    /**
     * Name of the database file
     */
    private static final String DATABASE_NAME = "participants.db";

    /**
     * Database version. If you change the database schema, you must increment the database version.
     */
    private static final int DATABASE_VERSION = 1;

    public ParticipantsDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String SQL_CREATE_PARTICIPANT_TABLE = "CREATE TABLE " + TABLE_NAME
                + " (" + _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_NAME + " TEXT NOT NULL, "
                + COLUMN_AGE + " INTEGER NOT NULL, "
                + COLUMN_RANK + " INTEGER NOT NULL DEFAULT 500);";

        db.execSQL(SQL_CREATE_PARTICIPANT_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // The database is still at version 1, so there's nothing to do be done here.
    }
}
