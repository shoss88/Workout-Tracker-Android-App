package com.example.workouttracker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    LinearLayout routineList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        routineList = (LinearLayout)findViewById(R.id.routineList);
    }

    public void addRoutine(View v){
        LayoutInflater li = getLayoutInflater();
        View routineBox = li.inflate(R.layout.general_box, routineList, false);
        TextView routineName = (TextView) routineBox.findViewById(R.id.boxName);
        ImageButton routineEdit = (ImageButton) routineBox.findViewById(R.id.boxEdit);
        ImageButton routineDelete = (ImageButton) routineBox.findViewById(R.id.boxDelete);

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
        routineList.addView(routineBox);
    }
    public void editRoutineNameDialog(View v){
        TextView routineName = ((RelativeLayout)v.getParent()).findViewById(R.id.boxName);
        Dialog dialog = new Dialog(MainActivity.this);
        dialog.setContentView(R.layout.edit_name_dialog);
        Button submit = dialog.findViewById(R.id.ESubmitButton);
        Button cancel = dialog.findViewById(R.id.ECancelButton);
        TextView textBox = ((TextView)dialog.findViewById(R.id.editTextBox));
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
        startActivity(workoutIntent);
    }
}