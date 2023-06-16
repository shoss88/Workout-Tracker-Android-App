package com.example.workouttracker;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity {
    private int routineCount = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void createRoutine(View v){
        Button newRoutine = new Button(MainActivity.this);
        newRoutine.setText((CharSequence)("Routine " + routineCount));
        this.routineCount++;
        LinearLayout routineList = (LinearLayout)findViewById(R.id.routineList);
        routineList.addView(newRoutine);
    }
}