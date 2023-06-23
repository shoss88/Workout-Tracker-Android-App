package com.example.workouttracker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
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

public class WorkoutActivity extends AppCompatActivity {
    LinearLayout workoutList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout);
        workoutList = (LinearLayout) findViewById(R.id.workoutList);
    }
    public void addWorkout(View v){
        LayoutInflater li = getLayoutInflater();
        View workoutBox = li.inflate(R.layout.workout_box, workoutList, false);
        TextView workoutName = (TextView) workoutBox.findViewById(R.id.workoutBoxName);
        ImageButton workoutEdit = (ImageButton) workoutBox.findViewById(R.id.workoutBoxEdit);
        ImageButton workoutDelete = (ImageButton) workoutBox.findViewById(R.id.workoutBoxDelete);
        ImageButton workoutAdd = (ImageButton) workoutBox.findViewById(R.id.workoutBoxAdd);
        LinearLayout exerciseList = (LinearLayout) workoutBox.findViewById(R.id.exerciseList);

        workoutName.setText((CharSequence) "Workout");

        workoutEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editWorkoutNameDialog(v);
            }
        });
        workoutDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                areYouSureDialog(v);
            }
        });
        workoutAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addExercise(v, exerciseList);
            }
        });
        workoutList.addView(workoutBox);
    }
    public void editWorkoutNameDialog(View v){
        TextView workoutName = ((RelativeLayout)v.getParent()).findViewById(R.id.workoutBoxName);
        Dialog dialog = new Dialog(WorkoutActivity.this);
        dialog.setContentView(R.layout.edit_name_dialog);
        Button submit = dialog.findViewById(R.id.ESubmitButton);
        Button cancel = dialog.findViewById(R.id.ECancelButton);
        TextView textBox = ((TextView)dialog.findViewById(R.id.editTextBox));
        textBox.setText(workoutName.getText());
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }
    public void areYouSureDialog(View v){
        Dialog dialog = new Dialog(WorkoutActivity.this);
        dialog.setContentView(R.layout.sure_dialog);
        Button yes = dialog.findViewById(R.id.yesButton);
        Button no = dialog.findViewById(R.id.noButton);
        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RelativeLayout parent = (RelativeLayout)v.getParent();
                View clickedButton = parent.findViewById(v.getId());
                if (parent.getId() == R.id.buttonArea){
                    workoutList.removeAllViews();
                }
                else if (parent.getId() == R.id.workoutBoxMain){
                    ((ConstraintLayout)parent.getParent().getParent()).removeView((RelativeLayout)parent.getParent());
                }
                else {
                    ((ConstraintLayout)parent.getParent()).removeView(parent);
                }
                dialog.dismiss();
            }
        });
        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }
    public void addExercise(View v, LinearLayout exerciseList){
        LayoutInflater li = getLayoutInflater();
        View exerciseBox = li.inflate(R.layout.exercise_box, exerciseList, false);
        ImageButton exerciseDelete = (ImageButton) exerciseBox.findViewById(R.id.exerciseBoxDelete);
        EditText exerciseName = (EditText) exerciseBox.findViewById(R.id.exerciseName);
        EditText exerciseWeight = (EditText) exerciseBox.findViewById(R.id.exerciseWeight);
        exerciseName.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int key, KeyEvent keyEvent) {
                return onKeyHelper(view, key, keyEvent, exerciseName);
            }
        });
        exerciseWeight.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int key, KeyEvent keyEvent) {
                return onKeyHelper(view, key, keyEvent, exerciseWeight);
            }
        });
        exerciseDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                areYouSureDialog(v);
            }
        });
        exerciseList.addView(exerciseBox);
    }
    public boolean onKeyHelper(View view, int key, KeyEvent keyEvent, View exerciseElement){
        if (key == KeyEvent.KEYCODE_ENTER){
            InputMethodManager keyboard = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            keyboard.hideSoftInputFromWindow(getWindow().getDecorView().getRootView().getWindowToken(), 0);
            exerciseElement.setFocusable(false);
            exerciseElement.setFocusableInTouchMode(true);
            return true;
        } else {
            return false;
        }
    }
}