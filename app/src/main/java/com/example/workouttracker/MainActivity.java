package com.example.workouttracker;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

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
        View routineBox = li.inflate(R.layout.general_box, routineList, false);
        TextView routineName = (TextView) routineBox.findViewById(R.id.boxName);
        ImageButton routineEdit = (ImageButton) routineBox.findViewById(R.id.boxEdit);
        ImageButton routineDelete = (ImageButton) routineBox.findViewById(R.id.boxDelete);

        routineName.setText((CharSequence) ("Routine " + routineCount));
        routineName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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

            }
        });
        routineList.addView(routineBox);
        routineCount++;
    }
    public void clearRoutines(View v){
        routineList.removeAllViews();
        routineCount = 1;
    }
    public void editRoutineNameDialog(View v){
        TextView routineName = ((RelativeLayout)v.getParent()).findViewById(R.id.boxName);
        Dialog dialog = new Dialog(MainActivity.this);
        dialog.setContentView(R.layout.edit_routine_name_dialog);
        Button submit = dialog.findViewById(R.id.ERSubmitButton);
        Button cancel = dialog.findViewById(R.id.ERCancelButton);
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
}