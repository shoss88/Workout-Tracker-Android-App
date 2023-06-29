package com.example.workouttracker;

import androidx.annotation.MainThread;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;


public class MainActivity extends AppCompatActivity {
    LinearLayout routineList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        routineList = findViewById(R.id.routineList);
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                RoutineRoomDatabase routineDB = RoutineRoomDatabase.getInstance(MainActivity.this);
                RoutineDao routineDao = routineDB.routineDao();
                List<Routine> previousRoutines = routineDao.getRoutines();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        for (int i = 0; i < previousRoutines.size(); i++){
                            Routine routine = previousRoutines.get(i);
                            addRoutine(routine.getName());
                        }
                    }
                });
            }
        });
        thread.start();
    }

    public void addRoutine(View v){
        addRoutine("Routine");
    }
    public void addRoutine(String name){
        LayoutInflater li = getLayoutInflater();
        View routineBox = li.inflate(R.layout.general_box, routineList, false);
        TextView routineName = routineBox.findViewById(R.id.boxName);
        ImageButton routineEdit = routineBox.findViewById(R.id.boxEdit);
        ImageButton routineDelete = routineBox.findViewById(R.id.boxDelete);

        routineName.setText((CharSequence) name);
        routineName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View clicked) {
                launchWorkoutActivity(clicked);
            }
        });
        routineEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View clicked) {
                editRoutineNameDialog(clicked);
            }
        });
        routineDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View clicked) {
                areYouSureDialog(clicked);
            }
        });

        routineList.addView(routineBox);
    }
    public void editRoutineNameDialog(View v){
        TextView routineName = ((RelativeLayout)v.getParent()).findViewById(R.id.boxName);
        Dialog dialog = new Dialog(MainActivity.this);
        dialog.setContentView(R.layout.edit_name_dialog);
        Button submit = dialog.findViewById(R.id.ESubmitButton);
        Button cancel = dialog.findViewById(R.id.ECancelButton);
        TextView textBox = dialog.findViewById(R.id.editTextBox);
        textBox.setText(routineName.getText());
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
                    routineName.setText((CharSequence) givenName);
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
    public void areYouSureDialog(View v){
        Dialog dialog = new Dialog(MainActivity.this);
        dialog.setContentView(R.layout.sure_dialog);
        Button yes = dialog.findViewById(R.id.yesButton);
        Button no = dialog.findViewById(R.id.noButton);
        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View clicked) {
                RelativeLayout parent = (RelativeLayout)v.getParent();
                if (parent.getId() == R.id.buttonArea){
                    routineList.removeAllViews();
                }
                else {
                    routineList.removeView((View)parent.getParent());
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
    public void launchWorkoutActivity(View v){
        Intent workoutIntent = new Intent(MainActivity.this, WorkoutActivity.class);
        TextView routineName = (TextView)v;
        workoutIntent.putExtra("RoutineName", routineName.getText());
        startActivity(workoutIntent);
    }

    @Override
    protected void onStop() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                RoutineRoomDatabase routineDB = RoutineRoomDatabase.getInstance(MainActivity.this);
                RoutineDao routineDao = routineDB.routineDao();
                routineDao.deleteAllRoutines();
                routineDao.clearPrimaryKey();
                Log.i("size", "Routinelist size: " + routineList.getChildCount());
                for (int i = 0; i < routineList.getChildCount(); i++) {
                    View routineBox = routineList.getChildAt(i);
                    String name = ((TextView) routineBox.findViewById(R.id.boxName)).getText().toString();
                    Routine newRoutine = new Routine(name);
                    routineDao.insertRoutine(newRoutine);
                }
            }
        });
        thread.start();
        super.onStop();
    }
}