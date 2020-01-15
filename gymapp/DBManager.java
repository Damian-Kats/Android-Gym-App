package com.example.gymapp;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.example.gymapp.DBHelper;

public class DBManager {

    private DBHelper dbHelper;

    private Context context;

    private SQLiteDatabase database;

    public DBManager(Context c) {
        context = c;
    }

    public DBManager open() throws SQLException {
        dbHelper = new DBHelper(context);
        database = dbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        dbHelper.close();
    }

    public void insertWorkout(String date, String desc) {
        ContentValues contentValue = new ContentValues();
        contentValue.put(DBHelper.DATE, date);
        contentValue.put(DBHelper.DESC, desc);
        database.insert(DBHelper.TABLE_NAME_WORKOUTS, null, contentValue);
    }

    public void insertExercise(String workout_id, String exercise, String sets, String weight, String reps) {
        ContentValues contentValue = new ContentValues();
        contentValue.put(DBHelper.WORKOUT_ID, workout_id);
        contentValue.put(DBHelper.EXERCISE, exercise);
        contentValue.put(DBHelper.SETS, sets);
        contentValue.put(DBHelper.WEIGHT, weight);
        contentValue.put(DBHelper.REPS, reps);
        database.insert(DBHelper.TABLE_NAME_EXERCISES, null, contentValue);
    }

    /*public Cursor fetch() {
        String[] columns = new String[] { DBHelper._ID, DBHelper.SUBJECT, DBHelper.DESC };
        Cursor cursor = database.query(DBHelper.TABLE_NAME, columns, null, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }*/

    public int updateWorkout(long _id, String date, String desc) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DBHelper.DATE, date);
        contentValues.put(DBHelper.DESC, desc);
        int i = database.update(DBHelper.TABLE_NAME_WORKOUTS, contentValues, DBHelper._ID + " = " + _id, null);
        return i;
    }

    public int updateExercise(long _id, String workout_id, String exercise, String sets, String weight, String reps) {
        ContentValues contentValue = new ContentValues();
        contentValue.put(DBHelper.WORKOUT_ID, workout_id);
        contentValue.put(DBHelper.EXERCISE, exercise);
        contentValue.put(DBHelper.SETS, sets);
        contentValue.put(DBHelper.WEIGHT, weight);
        contentValue.put(DBHelper.REPS, reps);
        int i = database.update(DBHelper.TABLE_NAME_EXERCISES, contentValue, DBHelper.EXERCISE_ID + " = " + _id, null);
        return i;
    }

    public void deleteFromWorkouts(long _id) {
        database.delete(DBHelper.TABLE_NAME_WORKOUTS, DBHelper._ID + "=" + _id, null);
    }

    public void deleteFromExercises(long _id) {
        database.delete(DBHelper.TABLE_NAME_EXERCISES, DBHelper.EXERCISE_ID + "=" + _id, null);
    }
}