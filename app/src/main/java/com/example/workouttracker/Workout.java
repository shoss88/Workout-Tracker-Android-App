package com.example.workouttracker;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "workout_table")
public class Workout {
    @PrimaryKey(autoGenerate = true)
    private int id = 0;

    private String name;

    private String exercises;

    public Workout(String name, String exercises){
        this.name = name;
        this.exercises = exercises;
    }

    public int getId(){
        return this.id;
    }

    public String getName(){
        return this.name;
    }

    public String getExercises() {
        return this.exercises;
    }

    public void setId(int id){
        this.id = id;
    }

    public void setName(String name){
        this.name = name;
    }

    public void setExercises(String exercises) {
        this.exercises = exercises;
    }
}
