package com.example.workouttracker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public class MainActivity extends AppCompatActivity {
    private int routineCount = 1;
    LinearLayout routineList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        routineList = (LinearLayout)findViewById(R.id.routineList);
    }

    public void addRoutine(View v){
        Button newRoutine = new Button(MainActivity.this);
        newRoutine.setText((CharSequence)("Routine " + routineCount));
        routineCount++;
        routineList.addView(newRoutine);
    }
    public void clearRoutines(View v){
        routineList.removeAllViews();
        routineCount = 1;
    }
}