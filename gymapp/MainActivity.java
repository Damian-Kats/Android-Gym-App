package com.example.gymapp;

import android.content.Intent;
import android.provider.BaseColumns;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import static android.provider.AlarmClock.EXTRA_MESSAGE;

public class MainActivity extends AppCompatActivity {

    private DBManager dbManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbManager = new DBManager(this);
        dbManager.open();
    }

    public void newWorkout(View view) {
        Intent intent = new Intent(this, WorkoutForm.class);
        startActivity(intent);
    }

    public void pastWorkouts(View view) {
        Intent intent = new Intent(this, PastWorkouts.class);
        startActivity(intent);
    }
}


