package com.example.workouttracker;

import java.util.ArrayList;

public class WorkoutObject {
    private String name;
    private ArrayList<ExerciseObject> exercises;

    WorkoutObject(String name, ArrayList<ExerciseObject> exercises){
        this.name = name;
        this.setExercises(exercises);
    }

    public String getName(){
        return this.name;
    }

    public ArrayList<ExerciseObject> getExercises(){
        return this.exercises;
    }

    public void setName(String name){
        this.name = name;
    }

    public void setExercises(ArrayList<ExerciseObject> exercises){
        this.exercises = new ArrayList<ExerciseObject>();
        for (int i = 0; i < exercises.size(); i++){
            ExerciseObject newEx = new ExerciseObject(exercises.get(i).getName(), exercises.get(i).getWeight());
            this.exercises.add(newEx);
        }
    }
}
