package com.example.unitconverter.RoomDB;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Results.class}, version = 1)


public abstract class AppDatabase extends RoomDatabase {
    private static AppDatabase db;

    public abstract ResultsDAO resultsDAO();

    public static AppDatabase getDatabase(final Context context) {
        if (db == null) {
            db = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, "unitconverter-database").build();
        }
        return db;
    }

}

