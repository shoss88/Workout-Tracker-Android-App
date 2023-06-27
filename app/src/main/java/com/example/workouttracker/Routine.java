package com.example.workouttracker;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "routine_table")
public class Routine {
    @PrimaryKey(autoGenerate = true)
    private int id = 0;

    private String name;

    public Routine(String name){
        this.name = name;
    }

    public int getId(){
        return this.id;
    }

    public String getName(){
        return this.name;
    }

    public void setId(int id){
        this.id = id;
    }

    public void setName(String name){
        this.name = name;
    }
}
