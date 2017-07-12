package com.example.l4z.habittracker;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.l4z.habittracker.date.ParticipantsContract;
import com.example.l4z.habittracker.date.ParticipantsDbHelper;

import java.util.ArrayList;

import static com.example.l4z.habittracker.date.ParticipantsContract.ParticipantsEntry.COLUMN_AGE;
import static com.example.l4z.habittracker.date.ParticipantsContract.ParticipantsEntry.COLUMN_NAME;
import static com.example.l4z.habittracker.date.ParticipantsContract.ParticipantsEntry.COLUMN_RANK;
import static com.example.l4z.habittracker.date.ParticipantsContract.ParticipantsEntry.TABLE_NAME;
import static com.example.l4z.habittracker.date.ParticipantsContract.ParticipantsEntry._ID;

/**
 * Created by l4z on 12.07.2017.
 */

public class ParticipantsActivity extends AppCompatActivity {

    private ParticipantsDbHelper mDbHelper;
    private Button mButtonChecker;
    private Button mButtonDummy;
    private Button mButtonClear;
    private SQLiteDatabase db;
    private TableLayout tableLayout;
    private TableRow mTableHeader;
    private TextView mTextViewHeader;
    private ArrayList<TableRow> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.desgin);

        list = new ArrayList<>();
        mButtonChecker = (Button) findViewById(R.id.button_checker);
        mButtonClear = (Button) findViewById(R.id.button_clear_max);
        mTableHeader = (TableRow) findViewById(R.id.table_header_main);
        mTextViewHeader = (TextView) findViewById(R.id.header);
        tableLayout = (TableLayout) findViewById(R.id.tableLayout1);
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

        mButtonClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db = mDbHelper.getWritableDatabase();
                // Deleting data from table
                db.execSQL("DELETE FROM participants");
                // Cleaning memory
                db.execSQL("VACUUM participants");
                // Deleting data from sqlite_sequence
                db.execSQL("DELETE FROM sqlite_sequence WHERE name ='participants' ");
                db.close();
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
        mDbHelper = new ParticipantsDbHelper(this);

        String[] projection = {
                _ID,
                COLUMN_NAME,
                COLUMN_AGE,
                COLUMN_RANK,
        };

        // Create and/or open a database to read from it
        db = mDbHelper.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                null);

        try {
            int idColumnIndex = cursor.getColumnIndex(ParticipantsContract.ParticipantsEntry._ID);
            int nameColumnIndex = cursor.getColumnIndex(ParticipantsContract.ParticipantsEntry.COLUMN_NAME);
            int ageColumnIndex = cursor.getColumnIndex(ParticipantsContract.ParticipantsEntry.COLUMN_AGE);
            int rankColumnIndex = cursor.getColumnIndex(ParticipantsContract.ParticipantsEntry.COLUMN_RANK);

            vacuumTable();

            while (cursor.moveToNext()) {
                int currentID = cursor.getInt(idColumnIndex);
                String currentName = cursor.getString(nameColumnIndex);
                int currentAge = cursor.getInt(ageColumnIndex);
                int currentRank = cursor.getInt(rankColumnIndex);

                TableRow row = createRow(currentID + "", currentName + "", currentAge + "", currentRank + "");
                list.add(row);
                tableLayout.addView(row);
            }
        } finally {
            // Always close the cursor when you're done reading from it. This releases all its
            // resources and makes it invalid.
            cursor.close();
        }
    }

    private void vacuumTable() {
        for (TableRow row : list) {
            tableLayout.removeView(row);
        }
        list.clear();
    }

    private void insertDummy() {
        ParticipantsDbHelper mDbHelper = new ParticipantsDbHelper(this);

        // New value for one column
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, "Grand Master");
        values.put(COLUMN_AGE, "65");
        values.put(COLUMN_RANK, "3000");

        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        long newRowId = db.insert(TABLE_NAME, null, values);
        Log.v("CatalogActivity", "New row ID " + newRowId);

        if (newRowId == -1) {
            Toast.makeText(this, "Error with saving", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "saved with row id: " + newRowId, Toast.LENGTH_SHORT).show();
        }
    }

    public TableRow createRow(String id, String name, String age, String rank) {
        TableRow row = new TableRow(this);
        TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
        row.setLayoutParams(lp);
        TextView idTv = new TextView(this);
        idTv.append(id);
        idTv.setGravity(Gravity.CENTER);
        TextView nameTv = new TextView(this);
        nameTv.append(name);
        nameTv.setGravity(Gravity.CENTER);
        TextView ageTv = new TextView(this);
        ageTv.append(age);
        ageTv.setGravity(Gravity.CENTER);
        TextView rankTv = new TextView(this);
        rankTv.append(rank);
        rankTv.setGravity(Gravity.CENTER);
        row.addView(idTv);
        row.addView(nameTv);
        row.addView(ageTv);
        row.addView(rankTv);
        return row;
    }
}
