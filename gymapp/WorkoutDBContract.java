package com.example.gymapp;

import android.provider.BaseColumns;

public final class WorkoutDBContract {
    // Constructor for SQL table
    private WorkoutDBContract() {}

    // Inner class that defines the table contents
    public static class WorkoutHistory implements BaseColumns {
        public static final String TABLE_NAME = "WORKOUT";
        public static final String COLUMN_NAME_TITLE = "title";
        public static final String COLUMN_NAME_DATE = "date";
        public static final String COLUMN_NAME_TIME = "time";
        public static final String COLUMN_NAME_EXERCISE = "exercise";
        public static final String COLUMN_NAME_WEIGHT = "weight";
        public static final String COLUMN_NAME_REPS = "reps";
        public static final String COLUMN_NAME_ID = "id";

        public static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS " +
                TABLE_NAME + " {" +
                _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_NAME_TITLE + " TEXT, " +
                COLUMN_NAME_DATE + " INTEGER, " +
                COLUMN_NAME_TIME + " INTEGER, " +
                COLUMN_NAME_EXERCISE + " TEXT, " +
                COLUMN_NAME_WEIGHT + " INTEGER, " +
                COLUMN_NAME_REPS + " INTEGER, " +
                COLUMN_NAME_ID + " INTEGER" + ")";
    }
}
