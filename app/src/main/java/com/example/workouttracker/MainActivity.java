package com.example.workouttracker;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    int routineCount = 1;
    LinearLayout routineList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        routineList = (LinearLayout)findViewById(R.id.routineList);
    }

    public void addRoutine(View v){
        LayoutInflater li = getLayoutInflater();
        View routineBox = li.inflate(R.layout.routine_box, routineList, false);
        TextView routineName = (TextView) routineBox.findViewById(R.id.routineName);
        routineName.setText((CharSequence) ("Routine " + routineCount));
        routineCount++;
        routineList.addView(routineBox);
    }
    public void clearRoutines(View v){
        routineList.removeAllViews();
        routineCount = 1;
    }
}