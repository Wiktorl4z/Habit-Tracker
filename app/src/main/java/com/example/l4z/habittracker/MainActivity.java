package com.example.l4z.habittracker;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import com.example.l4z.habittracker.date.ParticipantsContract;
import com.example.l4z.habittracker.date.ParticipantsDbHelper;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.editText_userName)
    EditText mUserName;
    @BindView(R.id.editText_age)
    EditText mUserAge;
    @BindView(R.id.checkbox_five)
    CheckBox mCheckBox5;
    @BindView(R.id.checkbox_ten)
    CheckBox mCheckBox10;
    @BindView(R.id.checkbox_fifteen)
    CheckBox mCheckBox15;
    @BindView(R.id.checkbox_twenty)
    CheckBox mCheckBox20;
    @BindView(R.id.button_quit)
    Button mButtonQuit;

    @BindView(R.id.button_sign_up)
    Button mButtonSignUp;
    private ParticipantsDbHelper mDbHelper;
    private SQLiteDatabase db;
    private String userRank;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        ButterKnife.bind(this);
        mDbHelper = new ParticipantsDbHelper(this);
        mButtonQuit.setOnClickListener(v -> {
            finish();
            System.exit(0);
        });

        mButtonSignUp.setOnClickListener(v -> {
            insertParticipant();
            startActivity(new Intent(MainActivity.this, ParticipantsActivity.class));
        });

        mCheckBox5.setOnCheckedChangeListener(getOnCheckedChangeListener(ParticipantsContract.ParticipantsEntry.COLUMN_RANK_500));
        mCheckBox10.setOnCheckedChangeListener(getOnCheckedChangeListener(ParticipantsContract.ParticipantsEntry.COLUMN_RANK_1000));
        mCheckBox15.setOnCheckedChangeListener(getOnCheckedChangeListener(ParticipantsContract.ParticipantsEntry.COLUMN_RANK_1500));
        mCheckBox20.setOnCheckedChangeListener(getOnCheckedChangeListener(ParticipantsContract.ParticipantsEntry.COLUMN_RANK_2000));
    }

    @NonNull
    private CompoundButton.OnCheckedChangeListener getOnCheckedChangeListener(String ur) {
        return (buttonView, isChecked) -> {
            disableCheckBoxes();
            if (isChecked) {
                userRank = ur;
                buttonView.setEnabled(true);
            } else {
                enableCheckBoxes();
            }
        };
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
    }

    private void insertParticipant() {
        String userString = mUserName.getText().toString().trim();
        String userAge = mUserAge.getText().toString().trim();

        ContentValues values = new ContentValues();
        values.put(ParticipantsContract.ParticipantsEntry.COLUMN_NAME, userString);
        values.put(ParticipantsContract.ParticipantsEntry.COLUMN_AGE, userAge);
        values.put(ParticipantsContract.ParticipantsEntry.COLUMN_RANK, userRank);

        db = mDbHelper.getWritableDatabase();
        long newRowID = db.insert(ParticipantsContract.ParticipantsEntry.TABLE_NAME, null, values);
        Log.v("MainActivity", "New row ID " + newRowID);

        if (newRowID == -1) {
            Toast.makeText(this, "Error with saving ", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Saved with row id: " + newRowID, Toast.LENGTH_SHORT).show();
        }
    }

    private void disableCheckBoxes() {
        mCheckBox5.setEnabled(false);
        mCheckBox10.setEnabled(false);
        mCheckBox15.setEnabled(false);
        mCheckBox20.setEnabled(false);
    }

    private void enableCheckBoxes() {
        mCheckBox5.setEnabled(true);
        mCheckBox10.setEnabled(true);
        mCheckBox15.setEnabled(true);
        mCheckBox20.setEnabled(true);
    }
}
