package com.example.l4z.habittracker;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.l4z.habittracker.date.ParticipantsContract;
import com.example.l4z.habittracker.date.ParticipantsDbHelper;

import static com.example.l4z.habittracker.date.ParticipantsContract.ParticipantsEntry.COLUMN_AGE;
import static com.example.l4z.habittracker.date.ParticipantsContract.ParticipantsEntry.COLUMN_NAME;
import static com.example.l4z.habittracker.date.ParticipantsContract.ParticipantsEntry.COLUMN_RANK;
import static com.example.l4z.habittracker.date.ParticipantsContract.ParticipantsEntry.TABLE_NAME;
import static com.example.l4z.habittracker.date.ParticipantsContract.ParticipantsEntry._ID;

/**
 * Created by l4z on 11.07.2017.
 */

public class CatalogActivity extends AppCompatActivity {

    private ParticipantsDbHelper mDbHelper;

    TextView mTextViewTable;
    Button mButtonChecker;
    Button mButtonDummy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.table_activity);

        mTextViewTable = (TextView) findViewById(R.id.text_view_table);
        mButtonChecker = (Button) findViewById(R.id.button_checker);
        mButtonChecker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayDatabaseInfo();
            }
        });

        mButtonDummy = (Button) findViewById(R.id.button_dummy);
        mButtonDummy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertDummy();
                displayDatabaseInfo();
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        displayDatabaseInfo();
    }

    private void displayDatabaseInfo() {
        // To access our database, we instantiate our subclass of SQLiteOpenHelper
        // and pass the context, which is the current activity.

        // TODO our context information are not in CatalogActivity!
        mDbHelper = new ParticipantsDbHelper(this);

        // Create and/or open a database to read from it
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        String[] projection = {
                _ID,
                COLUMN_NAME,
                COLUMN_AGE,
                COLUMN_RANK,
        };

        Cursor cursor = db.query(TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                null);


        // Display the number of rows in the Cursor (which reflects the number of rows in the
        // pets table in the database).
        mTextViewTable.setText("Tabelka " + cursor.getCount() + " uczestnicy. \n\n");
        mTextViewTable.append(_ID
                + " - " + COLUMN_NAME
                + " - " + COLUMN_AGE
                + " - " + COLUMN_RANK + "\n");

        try {
            int idColumnIndex = cursor.getColumnIndex(ParticipantsContract.ParticipantsEntry._ID);
            int nameColumnIndex = cursor.getColumnIndex(ParticipantsContract.ParticipantsEntry.COLUMN_NAME);
            int ageColumnIndex = cursor.getColumnIndex(ParticipantsContract.ParticipantsEntry.COLUMN_AGE);
            int rankColumnIndex = cursor.getColumnIndex(ParticipantsContract.ParticipantsEntry.COLUMN_RANK);

            while (cursor.moveToNext()) {
                int currentID = cursor.getInt(idColumnIndex);
                String currentName = cursor.getString(nameColumnIndex);
                int currentAge = cursor.getInt(ageColumnIndex);
                int currentRank = cursor.getInt(rankColumnIndex);

                mTextViewTable.append(("\n" + currentID
                        + " - " + currentName
                        + " - " + currentAge
                        + " - " + currentRank));
            }

        } finally {
            // Always close the cursor when you're done reading from it. This releases all its
            // resources and makes it invalid.
            cursor.close();
        }
    }

    private void insertDummy() {
        ParticipantsDbHelper mDbHelper = new ParticipantsDbHelper(this);

        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        // New value for one column
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, "Wiktor");
        values.put(COLUMN_AGE, "5");
        values.put(COLUMN_RANK, "500");

        long newRowId = db.insert(TABLE_NAME, null, values);
        Log.v("CatalogActivity", "New row ID " + newRowId);

        if (newRowId == -1) {
            Toast.makeText(this, "Error with saving", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "saved with row id: " + newRowId, Toast.LENGTH_SHORT).show();
        }
    }
}
