package com.example.project.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Equation.class}, version = 1)
public abstract class EquationDatabase extends RoomDatabase {

    private static EquationDatabase instance;

    public abstract EquationDao equationDao();

    public static synchronized EquationDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                            EquationDatabase.class, "equation_database")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }
}
