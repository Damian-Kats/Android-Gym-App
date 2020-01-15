package com.example.gymapp;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TimePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.view.ViewGroup.LayoutParams;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class WorkoutForm extends AppCompatActivity {

    private DBManager dbManager;

    EditText textTitle, textDates, textTimes;

    int counter = 0;
    private int mYear, mMonth, mDay, mHour, mMinute;
    private String mDayWeek, mMonthName, am_pm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout_form);

        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        textTitle = findViewById(R.id.title);
        textDates = findViewById(R.id.dates);
        textTimes = findViewById(R.id.times);

        // set current date
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mMonthName = c.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault());
        mDay = c.get(Calendar.DAY_OF_MONTH);
        mDayWeek = c.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.getDefault());
        textDates.setText(mDay + "/" + (mMonth + 1) + "/" + mYear);

        // set current time
        mHour = c.get(Calendar.HOUR);
        mMinute = c.get(Calendar.MINUTE);
        am_pm = c.getDisplayName(Calendar.AM_PM, Calendar.LONG, Locale.getDefault());
        textTimes.setText(mHour + ":" + mMinute + am_pm);

        // set default title
        textTitle.setText(mDayWeek + " " + mDay + "th " + mMonthName + "'s Workout");
    }

    public void onClick(View view) {
        if (view == textDates) {
            textDates.setText("");
        }

        if (view == textTimes) {
            textTimes.setText("");
        }
    }

    public void createRecord(View view) {
        counter += 1;

        /* Get parent RelativeLayout */
        RelativeLayout parent = findViewById(R.id.parent);

        /* Create new LinearLayout for nested spinners */
        LinearLayout line = new LinearLayout(this);
        LinearLayout.LayoutParams line_params = new LinearLayout.LayoutParams(
                LayoutParams.MATCH_PARENT,
                35
        );
        final float scale = getResources().getDisplayMetrics().density;
        int margin = (int) (5 * scale + 0.5f);
        line_params.setMargins(0, 0, 0, margin);
        line.setOrientation(LinearLayout.HORIZONTAL);
        line.setPadding(0, 0, 0, 0);
        line.setId(counter);
        line.setLayoutParams(line_params);

        /* Exercise spinner, currently filled with exercises from resources */
        Spinner exercise = new Spinner(this);
        exercise.setLayoutParams(new LinearLayout.LayoutParams(0, LayoutParams.WRAP_CONTENT, 0.50f));
        exercise.setId(10 * counter + 1);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.arm_exercises, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        exercise.setAdapter(adapter);

        /* Weight spinner */
        Spinner weight = new Spinner(this);
        weight.setLayoutParams(new LinearLayout.LayoutParams(0, LayoutParams.WRAP_CONTENT, 0.25f));
        weight.setId(10 * counter + 2);
        List weight_ints = new ArrayList<Integer>();
        for (int i = 1; i < 30; i++) {
            weight_ints.add(Integer.toString(i));
        }
        ArrayAdapter<Integer> spinner_w = new ArrayAdapter<Integer>(
                this, android.R.layout.simple_spinner_item, weight_ints);
        spinner_w.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        weight.setAdapter(spinner_w);

        /* Rep spinner */
        Spinner reps = new Spinner(this);
        reps.setLayoutParams(new LinearLayout.LayoutParams(0, LayoutParams.WRAP_CONTENT, 0.25f));
        reps.setId(10*counter+3);
        List rep_ints = new ArrayList<Integer>();
        for (int i = 1; i < 20; i++) {
            rep_ints.add(Integer.toString(i));
        }
        ArrayAdapter<Integer> spinner_r = new ArrayAdapter<Integer>(
                this, android.R.layout.simple_spinner_item, rep_ints);
        spinner_r.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        reps.setAdapter(spinner_r);

        /* Give new LinearLayout layout_below property */
        RelativeLayout.LayoutParams params= new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        if (counter == 1) {
            params.addRule(RelativeLayout.BELOW, R.id.exerciseLabel);
        }
        else {
            params.addRule(RelativeLayout.BELOW, findViewById(counter-1).getId());
        }
        params.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        line.setLayoutParams(params);

        /* Button to delete line */
        final Button X = new Button(this);
        /* Convert dps into pixels */
        int pixels = (int) (40 * scale + 0.5f);
        X.setLayoutParams(new LinearLayout.LayoutParams(pixels, pixels));
        X.setText("X");
        X.setId(-counter);
        X.setTag(counter);
        X.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteLine(X);
            }
        });

        /* Add spinners to LinearLayout */
        line.addView(exercise);
        line.addView(weight);
        line.addView(reps);
        line.addView(X);

        /* Add LinearLayout to parent RelativeLayout */
        parent.addView(line);

        Button button = findViewById(R.id.new_exercise);
        RelativeLayout.LayoutParams params2= new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        params2.addRule(RelativeLayout.BELOW, findViewById(counter).getId());
        params2.addRule(RelativeLayout.CENTER_HORIZONTAL);
        button.setLayoutParams(params2);

        TextView line_count = (TextView) findViewById(R.id.textCounter);
        line_count.setText(Integer.toString(counter));
        Log.d("LINE COUNT", "Counter: " + counter);
    }

    public void deleteLine(View view) {
        /* Delete the corresponding line */
        String tag = view.getTag().toString();
        int num = Integer.parseInt(tag);
        View line_to_remove = findViewById(num);
        ((ViewGroup)line_to_remove.getParent()).removeView(line_to_remove);

        /* Initialise layout params for line and button */
        Button button = findViewById(R.id.new_exercise);
        RelativeLayout.LayoutParams button_params = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        RelativeLayout.LayoutParams line_params = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        button_params.addRule(RelativeLayout.CENTER_HORIZONTAL);

        /* Loop through all lines after the deleted line and update */
        for (int i = num; i <= counter; i++) {
            /* If deleted line is line 1 */
            if (i == 1) {
                /* If no other lines then move button below dates */
                if (counter == 1) {
                    button_params.addRule(RelativeLayout.BELOW, findViewById(R.id.exerciseLabel).getId());
                    Log.d("PATH1", "Line 1 deleted, no lines left so move button below exercise label.");
                }
                /* If at least one other line, move second line below dates */
                else {
                    View line = findViewById(i + 1);
                    line.setId(i);
                    line_params.addRule(RelativeLayout.BELOW, findViewById(R.id.exerciseLabel).getId());
                    line.setLayoutParams(line_params);

                    View deleteButton = findViewById(-(i + 1));
                    deleteButton.setId(-i);
                    deleteButton.setTag(i);
                    Log.d("PATH2", "Line 1 deleted, at least one line left so move line 2 below exercise label.");
                }
            }
            else if (i == counter) {
                button_params.addRule(RelativeLayout.BELOW, findViewById(i-1).getId());
                Log.d("PATH3", "Last line deleted, move button below most recent line.");
            }
            else {
                View line = findViewById(i+1);
                line.setId(i);
                line_params.addRule(RelativeLayout.BELOW, findViewById(i-1).getId());
                line.setLayoutParams(line_params);

                View deleteButton = findViewById(-(i + 1));
                deleteButton.setId(-i);
                deleteButton.setTag(i);
                Log.d("PATH4", "A line between 1 and Counter deleted, shuffle all other lines up.");
            }
        }
        Log.d("SPACE", " ");
        button.setLayoutParams(button_params);
        counter -= 1;

        TextView line_count = (TextView) findViewById(R.id.textCounter);
        line_count.setText(Integer.toString(counter));
    }

    public void saveWorkout(View view) {
        if (counter > 0) {
            String endstring = textTitle.getText().toString() + "\n" + textDates.getText().toString()
                    + " " + textTimes.getText().toString() + "\n";
            int reps, weight;
            for (int i = 1; i <= counter; i++) {
                Spinner exercise_s = (Spinner) findViewById(10*i+1);
                String value_s = exercise_s.getSelectedItem().toString();

                Spinner exercise_w = (Spinner) findViewById(10*i+2);
                String value_w = exercise_w.getSelectedItem().toString();

                Spinner exercise_r = (Spinner) findViewById(10*i+3);
                String value_r = exercise_r.getSelectedItem().toString();

                endstring += "\nExercise: " + value_s + "\n" + "Weight: " + value_w + "   " + "Reps: "
                        + value_r + "\n\n";
            }

            Intent intent = new Intent(this, PastWorkouts.class);
            intent.putExtra("string", endstring);
            startActivity(intent);
        }
    }
}
