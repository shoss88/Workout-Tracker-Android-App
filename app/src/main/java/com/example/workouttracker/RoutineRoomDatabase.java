package com.example.workouttracker;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(version = 1, entities = {Routine.class})
public abstract class RoutineRoomDatabase extends RoomDatabase {
    public abstract RoutineDao routineDao();
    private static volatile RoutineRoomDatabase INSTANCE;

    static RoutineRoomDatabase getInstance(Context context){
        if (INSTANCE == null){
            synchronized (RoutineRoomDatabase.class){
                if (INSTANCE == null){
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), RoutineRoomDatabase.class, "ROUTINE_DB").build();
                }
            }
        }
        return INSTANCE;
    }
}
