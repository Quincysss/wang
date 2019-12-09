package com.example.test;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

    @Database(entities = {userData.class}, version = 2, exportSchema = false)
    public abstract class userDatabase extends RoomDatabase {
        public abstract UserDao userDao();
        private static volatile com.example.test.userDatabase INSTANCE;
        static com.example.test.userDatabase getDatabase(final Context context) {
            if (INSTANCE == null) {
                synchronized (com.example.test.userDatabase.class) {
                    if (INSTANCE == null) {
                        INSTANCE =
                                Room.databaseBuilder(context.getApplicationContext(),
                                        com.example.test.userDatabase.class, "user_database")
                                        .build();
                    }
                }
            }
            return INSTANCE;
        }
}
