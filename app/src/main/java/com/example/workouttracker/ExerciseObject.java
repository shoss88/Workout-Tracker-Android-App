package com.example.workouttracker;

public class ExerciseObject {
    private String name;
    private int weight;

    ExerciseObject(String name, int weight){
        this.name = name;
        this.weight = weight;
    }

    public String getName(){
        return this.name;
    }

    public int getWeight(){
        return this.weight;
    }

    public void setName(String name){
        this.name = name;
    }

    public void setWeight(int weight){
        this.weight = weight;
    }
}
