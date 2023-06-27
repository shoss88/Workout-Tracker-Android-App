package com.example.workouttracker;

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

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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
                List<Routine> previousRoutines =
                        RoutineRoomDatabase.getInstance(MainActivity.this).routineDao().getRoutines();
                
            }
        });
    }

    public void addRoutine(View v){
        LayoutInflater li = getLayoutInflater();
        View routineBox = li.inflate(R.layout.general_box, routineList, false);
        TextView routineName = routineBox.findViewById(R.id.boxName);
        ImageButton routineEdit = routineBox.findViewById(R.id.boxEdit);
        ImageButton routineDelete = routineBox.findViewById(R.id.boxDelete);

        routineName.setText((CharSequence) ("Routine"));
        routineName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchWorkoutActivity(v);
            }
        });
        routineEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editRoutineNameDialog(v);
            }
        });
        routineDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                areYouSureDialog(v);
            }
        });
        routineBox.setId(View.generateViewId());
        routineList.addView(routineBox);
        ExecutorService service = Executors.newSingleThreadExecutor();
        service.execute(new Runnable() {
            @Override
            public void run() {
                RoutineRoomDatabase.getInstance(MainActivity.this).routineDao().insertRoutine(new Routine(routineBox.getId(), routineName.getText().toString()));
            }
        });
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
            public void onClick(View view) {
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
            public void onClick(View view) {
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
            public void onClick(View view) {
                RelativeLayout parent = (RelativeLayout)v.getParent();
                View clickedButton = parent.findViewById(v.getId());
                if (parent.getId() == R.id.buttonArea){
                    routineList.removeAllViews();
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
    public void launchWorkoutActivity(View v){
        Intent workoutIntent = new Intent(MainActivity.this, WorkoutActivity.class);
        TextView routineName = (TextView)v;
        workoutIntent.putExtra("RoutineName", routineName.getText());
        startActivity(workoutIntent);
    }
}