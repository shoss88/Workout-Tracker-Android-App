package com.example.workouttracker;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;


public class MainActivity extends AppCompatActivity {
    LinearLayout workoutList;

    /**
     * Called when the activity is created.
     * @param savedInstanceState
     *     The previous state that was saved.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        workoutList = findViewById(R.id.workoutList);
        Button clearWorkouts = findViewById(R.id.clearWorkoutsButton);
        clearWorkouts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View clicked) {
                areYouSureDialog(clicked, null);
            }
        });
        // Separate thread to retrieve data from the database and add dynamically added views back to the main UI thread.
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                WorkoutRoomDatabase workoutDB = WorkoutRoomDatabase.getInstance(MainActivity.this);
                WorkoutDao workoutDao = workoutDB.workoutDao();
                List<Workout> previousWorkouts = workoutDao.getWorkouts();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        for (int i = 0; i < previousWorkouts.size(); i++){
                            Workout workout = previousWorkouts.get(i);
                            View workoutBox = addWorkout(workout.getName());
                            String[] exerciseList = workout.getExercises().split("\n");
                            if (!exerciseList[0].equals("")){
                                for (String exerciseString : exerciseList) {
                                    int colonIndex = exerciseString.indexOf(":");
                                    String exerciseName = exerciseString.substring(0, colonIndex);
                                    String exerciseWeight = exerciseString.substring(colonIndex + 1);
                                    addExercise(workoutBox, exerciseName, exerciseWeight);
                                }
                            }
                        }
                    }
                });
            }
        });
        thread.start();
    }

    /**
     * Adds a workout to the workout list.
     * @param v
     *     The view that was clicked
     */
    public void addWorkout(View v){
        addWorkout("Workout");
    }

    /**
     * Adds a workout with a specific name to the workout list and returns this workout.
     * @param name
     *     A given name for the workout
     * @return
     *     Returns the created workout.
     */
    public View addWorkout(String name){
        LayoutInflater li = getLayoutInflater();
        View workoutBox = li.inflate(R.layout.workout_box, workoutList, false);
        TextView workoutName = workoutBox.findViewById(R.id.workoutBoxName);
        ImageButton workoutEdit = workoutBox.findViewById(R.id.workoutBoxEdit);
        ImageButton workoutDelete = workoutBox.findViewById(R.id.workoutBoxDelete);
        ImageButton workoutAdd = workoutBox.findViewById(R.id.workoutBoxAdd);
        LinearLayout exerciseList = workoutBox.findViewById(R.id.exerciseList);

        workoutName.setText((CharSequence) name);

        workoutEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View clicked) {
                editWorkoutNameDialog(clicked);
            }
        });
        workoutDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View clicked) {
                areYouSureDialog(clicked, exerciseList);
            }
        });
        workoutAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View clicked) {
                addExercise(workoutBox, null, null);
            }
        });
        workoutList.addView(workoutBox);
        return workoutBox;
    }

    /**
     * Displays the dialog for editing workout names.
     * @param v
     *     The view that was clicked.
     */
    public void editWorkoutNameDialog(View v){
        TextView workoutName = ((RelativeLayout)v.getParent()).findViewById(R.id.workoutBoxName);
        Dialog dialog = new Dialog(MainActivity.this);
        dialog.setContentView(R.layout.edit_name_dialog);
        Button submit = dialog.findViewById(R.id.ESubmitButton);
        Button cancel = dialog.findViewById(R.id.ECancelButton);
        TextView textBox = dialog.findViewById(R.id.editTextBox);
        textBox.setText(workoutName.getText());
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View clicked) {
                String givenName = textBox.getText().toString();
                if (givenName.length() > 19){
                    TextView submitInfo = dialog.findViewById(R.id.submitInfo);
                    submitInfo.setTextColor(Color.parseColor("#FF0000"));
                    submitInfo.setText((CharSequence) "Too many characters: 19 characters max");
                }
                else{
                    workoutName.setText((CharSequence) givenName);
                    dialog.dismiss();
                }
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View clicked) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    /**
     * Displays the dialog for confirming an action.
     * @param v
     *     The view that was clicked.
     * @param exerciseList
     *     The exercise list of a specific workout.
     */
    public void areYouSureDialog(View v, LinearLayout exerciseList){
        Dialog dialog = new Dialog(MainActivity.this);
        dialog.setContentView(R.layout.sure_dialog);
        Button yes = dialog.findViewById(R.id.yesButton);
        Button no = dialog.findViewById(R.id.noButton);
        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View clicked) {
                RelativeLayout parent = (RelativeLayout)v.getParent();
                if (parent.getId() == R.id.buttonArea){
                    workoutList.removeAllViews();
                }
                else if (parent.getId() == R.id.workoutBoxMain){
                    workoutList.removeView((View)parent.getParent().getParent());
                }
                else {
                    exerciseList.removeView((View)parent.getParent());
                }
                dialog.dismiss();
            }
        });
        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View clicked) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    /**
     * Adds an exercise to the given workout.
     * @param workoutBox
     *     The view that represents the workout for the exercise to be added to.
     * @param name
     *     A given name for the exercise.
     * @param weight
     *     A given weight for the exercise.
     */
    public void addExercise(View workoutBox, String name, String weight){
        LinearLayout exerciseList = workoutBox.findViewById(R.id.exerciseList);
        LayoutInflater li = getLayoutInflater();
        View exerciseBox = li.inflate(R.layout.exercise_box, exerciseList, false);
        ImageButton exerciseDelete = exerciseBox.findViewById(R.id.exerciseBoxDelete);
        EditText exerciseName = exerciseBox.findViewById(R.id.exerciseName);
        EditText exerciseWeight = exerciseBox.findViewById(R.id.exerciseWeight);
        exerciseName.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int key, KeyEvent keyEvent) {
                return onKeyHelper(key, exerciseName);
            }
        });
        exerciseWeight.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int key, KeyEvent keyEvent) {
                return onKeyHelper(key, exerciseWeight);
            }
        });
        exerciseDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View clicked) {
                areYouSureDialog(clicked, exerciseList);
            }
        });

        if (name != null && weight != null){
            exerciseName.setText((CharSequence) name);
            exerciseWeight.setText((CharSequence) weight);
        }
        exerciseList.addView(exerciseBox);
    }

    /**
     * A helper for the onKey function. It forcefully hides the keyboard after the user presses the "DONE" key.
     * @param key
     *     The key pressed by the user.
     * @param exerciseElement
     *     The view that represents an element of the exercise.
     * @return
     *     Returns true if the key pressed was "DONE".
     */
    public boolean onKeyHelper(int key, View exerciseElement){
        if (key == KeyEvent.KEYCODE_ENTER){
            InputMethodManager keyboard = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            keyboard.hideSoftInputFromWindow(MainActivity.this.getCurrentFocus().getWindowToken(), 0);
            exerciseElement.setFocusable(false);
            exerciseElement.setFocusableInTouchMode(true);
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * When the activity is stopped, this method will also create a new thread to save the current workout and exercise data.
     */
    @Override
    protected void onStop() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                WorkoutRoomDatabase workoutDB = WorkoutRoomDatabase.getInstance(MainActivity.this);
                WorkoutDao workoutDao = workoutDB.workoutDao();
                workoutDao.deleteAllWorkouts();
                workoutDao.clearPrimaryKey();
                for (int i = 0; i < workoutList.getChildCount(); i++) {
                    View workoutBox = workoutList.getChildAt(i);
                    String name = ((TextView) workoutBox.findViewById(R.id.workoutBoxName)).getText().toString();
                    LinearLayout exerciseList = workoutBox.findViewById(R.id.exerciseList);
                    StringBuilder exerciseListString = new StringBuilder();
                    int exerciseListSize = exerciseList.getChildCount();
                    for (int j = 0; j < exerciseListSize; j++){
                        View exerciseBox = exerciseList.getChildAt(j);
                        EditText exerciseName = exerciseBox.findViewById(R.id.exerciseName);
                        EditText exerciseWeight = exerciseBox.findViewById(R.id.exerciseWeight);
                        String exerciseString = exerciseName.getText() + ":" + exerciseWeight.getText();
                        if (j < exerciseListSize - 1){
                            exerciseString += "\n";
                        }
                        exerciseListString.append(exerciseString);
                    }
                    Workout newWorkout = new Workout(name, exerciseListString.toString());
                    workoutDao.insertWorkout(newWorkout);
                }
            }
        });
        thread.start();
        super.onStop();
    }
}