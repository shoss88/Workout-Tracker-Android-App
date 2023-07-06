package com.example.workouttracker;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface WorkoutDao {

    /**
     * Inserts a workout into the database.
     * @param workout
     *     The workout to be inserted.
     */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertWorkout(Workout workout);

    /**
     * Returns a list of Workout objects that were stored in the database.
     * @return
     *     A list of Workout objects that were stored in the database.
     */
    @Query("SELECT * FROM 'workout_table'")
    List<Workout> getWorkouts();

    /**
     * Deletes all workouts stored within the database.
     */
    @Query("DELETE FROM 'workout_table'")
    void deleteAllWorkouts();

    /**
     * Resets the primary key generator back to 1.
     */
    @Query("DELETE FROM sqlite_sequence WHERE name='workout_table'")
    void clearPrimaryKey();

}
