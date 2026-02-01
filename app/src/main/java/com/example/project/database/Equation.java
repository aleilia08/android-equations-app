package com.example.project.database;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "equation_table")
public class Equation {
    @PrimaryKey(autoGenerate = true)
    private int id;

    private String equation;
    private String solution;

    public Equation(String equation, String solution) {
        this.equation = equation;
        this.solution = solution;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEquation() {
        return equation;
    }

    public String getSolution() {
        return solution;
    }
}
