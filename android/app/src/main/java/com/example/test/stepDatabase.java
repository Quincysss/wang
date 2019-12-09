package com.example.test;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

@Database(entities = {stepsData.class}, version = 2, exportSchema = false)
public abstract class stepDatabase extends RoomDatabase {
    public abstract StepDao stepDao();
    private static volatile stepDatabase INSTANCE;
    static stepDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (stepDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE =
                            Room.databaseBuilder(context.getApplicationContext(),
                                    stepDatabase.class, "step_database")
                                    .build();
                }
            }
        }
        return INSTANCE;
    }
}