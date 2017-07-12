package com.example.l4z.habittracker;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.l4z.habittracker.date.ParticipantsContract;
import com.example.l4z.habittracker.date.ParticipantsDbHelper;

public class MainActivity extends AppCompatActivity {

    /**
     * EditText field to enter the Username
     */
    EditText mUserName;

    /**
     * EditText field to enter the age
     */
    EditText mUserAge;

    /**
     * CheckBox field to set user rank 500
     */
    CheckBox mCheckBox5;

    /**
     * CheckBox field to set user rank 1000
     */
    CheckBox mCheckBox10;

    /**
     * CheckBox field to set user rank 1500
     */
    CheckBox mCheckBox15;

    /**
     * CheckBox field to set user rank 2000
     */
    CheckBox mCheckBox20;

    /**
     * Button field to quit application
     */
    Button mButtonQuit;

    private ParticipantsDbHelper mDbHelper;

    /**
     * Button field to sign up
     */
    Button mButtonSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);


        mDbHelper = new ParticipantsDbHelper(this);

        mUserName = (EditText) findViewById(R.id.editText_userName);
        mUserAge = (EditText) findViewById(R.id.editText_age);

        mCheckBox5 = (CheckBox) findViewById(R.id.checkbox_five);
        mCheckBox10 = (CheckBox) findViewById(R.id.checkbox_ten);
        mCheckBox15 = (CheckBox) findViewById(R.id.checkbox_fifteen);
        mCheckBox20 = (CheckBox) findViewById(R.id.checkbox_twenty);

        mButtonQuit = (Button) findViewById(R.id.button_quit);
        mButtonQuit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                System.exit(0);
            }
        });

        mButtonSignUp = (Button) findViewById(R.id.button_sign_up);
        mButtonSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertParticipant();
                startActivity(new Intent(MainActivity.this,CatalogActivity.class));
            }
        });

    }

    private void insertParticipant(){

        String userString = mUserName.getText().toString().trim();
        String userAge = mUserAge.getText().toString().trim();

        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(ParticipantsContract.ParticipantsEntry.COLUMN_NAME, userString);
        values.put(ParticipantsContract.ParticipantsEntry.COLUMN_AGE, userAge);

        long newRowID = db.insert(ParticipantsContract.ParticipantsEntry.TABLE_NAME, null, values);
        Log.v("MainActivity", "New row ID " + newRowID);

        if (newRowID == -1){
            Toast.makeText(this,"Error with saving ", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this,"Saved with row id: " + newRowID, Toast.LENGTH_SHORT).show();
        }
    }
}
