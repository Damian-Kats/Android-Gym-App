package com.example.gymapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBHelper extends SQLiteOpenHelper {

    // Table Names
    public static final String TABLE_NAME_WORKOUTS = "WORKOUTS";
    public static final String TABLE_NAME_EXERCISES = "EXERCISES";

    // Table columns

    // Workout table
    public static final String _ID = "_id";
    public static final String DATE = "date";
    public static final String DESC = "description";

    // Exercise table
    public static final String EXERCISE_ID = "exercise_id";
    public static final String WORKOUT_ID = "workout_id";
    public static final String EXERCISE = "exercise";
    public static final String SETS = "sets";
    public static final String WEIGHT = "weight";
    public static final String REPS = "reps";

    // Database Information
    static final String DB_NAME = "GYMAPP.DB";

    // database version
    static final int DB_VERSION = 1;

    // Creating table query
    private static final String CREATE_WORKOUT_TABLE = "create table " + TABLE_NAME_WORKOUTS + "(" + _ID
            + " INTEGER PRIMARY KEY AUTOINCREMENT, " + DATE + " INTEGER NOT NULL, " + DESC + " TEXT);";

    private static final String CREATE_EXERCISE_TABLE = "create table " + TABLE_NAME_EXERCISES + "(" + EXERCISE_ID
            + " INTEGER PRIMARY KEY AUTOINCREMENT, " + WORKOUT_ID + " INTEGER NOT NULL, " + EXERCISE +
            "TEXT," + SETS + "INTEGER NOT NULL," + WEIGHT + "INTEGER NOT NULL," + REPS + "INTEGER NOT NULL);";

    public DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_WORKOUT_TABLE);
        db.execSQL(CREATE_EXERCISE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w("GYMAPP_DB", "Upgrading database from version " + oldVersion
                + " to " + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_WORKOUTS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_EXERCISES);
        onCreate(db);
    }
}