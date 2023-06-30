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
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertWorkout(Workout workout);

    @Update
    void updateWorkout(Workout workout);

    @Delete
    void deleteWorkout(Workout workout);

    @Query("SELECT * FROM 'workout_table'")
    List<Workout> getWorkouts();

    @Query("SELECT * FROM 'workout_table' WHERE id LIKE :id ")
    Workout getWorkoutById(int id);

    @Query("DELETE FROM 'workout_table'")
    void deleteAllWorkouts();

    @Query("DELETE FROM sqlite_sequence WHERE name='workout_table'")
    void clearPrimaryKey();

}
