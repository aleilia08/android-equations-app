package com.example.project.database;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class EquationViewModel extends AndroidViewModel {

    private EquationRepository repository;
    private LiveData<List<Equation>> allEquations;

    public EquationViewModel(@NonNull Application application) {
        super(application);
        repository = new EquationRepository(application);
        allEquations = repository.getAllEquations();
    }

    public void insert(Equation equation) {
        repository.insert(equation);
    }

    public LiveData<List<Equation>> getAllEquations() {
        return allEquations;
    }
}
