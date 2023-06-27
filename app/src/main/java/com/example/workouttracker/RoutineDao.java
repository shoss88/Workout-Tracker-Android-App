package com.example.workouttracker;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface RoutineDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertRoutine(Routine routine);

    @Update
    void updateRoutine(Routine routine);

    @Delete
    void deleteRoutine(Routine routine);

    @Query("SELECT * FROM routine_table")
    List<Routine> getRoutines();

    @Query("SELECT * FROM routine_table WHERE id LIKE :id ")
    Routine getRoutineById(int id);

    @Query("DELETE FROM routine_table")
    void deleteAllRoutines();
}
