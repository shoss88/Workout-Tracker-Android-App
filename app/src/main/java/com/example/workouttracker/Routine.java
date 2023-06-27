package com.example.workouttracker;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Routine {
    @PrimaryKey
    private int id;

    private String name;

    public Routine(int id, String name){
        this.id = id;
        this.name = name;
    }

    public int getId(){
        return this.id;
    }

    public String getName(){
        return this.name;
    }
}
