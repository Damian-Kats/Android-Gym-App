package com.example.gymapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class PastWorkouts extends AppCompatActivity {

    int entries = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_past_workouts);
        TextView text = (TextView) findViewById(R.id.saved_values);
        text.setText(getIntent().getStringExtra("string"));
    }
}
