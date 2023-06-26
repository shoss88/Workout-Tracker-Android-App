package com.example.workouttracker;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Routine {
    @PrimaryKey
    public int id;

    public String name;

    public Routine(int id, String name){
        this.id = id;
        this.name = name;
    }
}
