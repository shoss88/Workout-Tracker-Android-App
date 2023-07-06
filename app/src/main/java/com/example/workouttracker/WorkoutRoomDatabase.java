package com.example.workouttracker;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(version = 1, entities = {Workout.class})
public abstract class WorkoutRoomDatabase extends RoomDatabase {
    public abstract WorkoutDao workoutDao();
    private static volatile WorkoutRoomDatabase INSTANCE;

    /**
     * Lazy instantiation of a WorkoutRoomDatabase instance.
     * @param context
     *     The application environment.
     * @return
     *     The database object for the workout.
     */
    public static WorkoutRoomDatabase getInstance(Context context){
        if (INSTANCE == null){
            synchronized (WorkoutRoomDatabase.class){
                if (INSTANCE == null){
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), WorkoutRoomDatabase.class, "WORKOUT_DB").build();
                }
            }
        }
        return INSTANCE;
    }
}
