package com.example.workouttracker;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "workout_table")
public class Workout {
    @PrimaryKey(autoGenerate = true)
    private int id = 0;

    private String name;

    private String exercises;

    /**
     * Constructs an instance of Workout with a given name and string representing exercises.
     * @param name
     *     The name of the workout being stored.
     * @param exercises
     *     A string representation of the exercises this workout contains.
     */
    public Workout(String name, String exercises){
        this.name = name;
        this.exercises = exercises;
    }

    /**
     * Returns the id of this Workout.
     * @return
     *     The id of this Workout.
     */
    public int getId(){
        return this.id;
    }

    /**
     * Returns the name of this Workout.
     * @return
     *     The name of this Workout.
     */
    public String getName(){
        return this.name;
    }

    /**
     * Returns the string representation of this Workout's exercises.
     * @return
     *     The string representation of this Workout's exercises.
     */
    public String getExercises() {
        return this.exercises;
    }

    /**
     * Set the id of this Workout to the given id.
     * @param id
     *     The new id of this Workout.
     */
    public void setId(int id){
        this.id = id;
    }

    /**
     * Set the name of this Workout to the given name.
     * @param name
     *     The new name of this Workout.
     */
    public void setName(String name){
        this.name = name;
    }

    /**
     * Set the exercises of this Workout to the given exercises
     * @param exercises
     *     The new exercises of this Workout.
     */
    public void setExercises(String exercises) {
        this.exercises = exercises;
    }
}
