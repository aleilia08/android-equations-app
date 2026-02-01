package com.example.project.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface EquationDao {

    @Insert
    void insert(Equation equation);

    @Query("SELECT * FROM equation_table")
    LiveData<List<Equation>> getAllEquations();
}

