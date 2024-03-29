package com.example.test;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

    @Database(entities = {StepData.class}, version = 2, exportSchema = false)
    public abstract class StepsDatabase extends RoomDatabase {
        public abstract StepsDao stepsDao();
        private static volatile com.example.test.StepsDatabase INSTANCE;
        static com.example.test.StepsDatabase getDatabase(final Context context) {
            if (INSTANCE == null) {
                synchronized (com.example.test.StepsDatabase.class) {
                    if (INSTANCE == null) {
                        INSTANCE =
                                Room.databaseBuilder(context.getApplicationContext(),
                                        com.example.test.StepsDatabase.class, "step_database")
                                        .build();
                    }
                }
            }
            return INSTANCE;
        }
    }

