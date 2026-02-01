package com.example.project.database;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class EquationRepository {
    private EquationDao equationDao;
    private LiveData<List<Equation>> allEquations;

    public EquationRepository(Application application) {
        EquationDatabase database = EquationDatabase.getInstance(application);
        equationDao = database.equationDao();
        allEquations = equationDao.getAllEquations();
    }

    public void insert(Equation equation) {
        new InsertEquationAsyncTask(equationDao).execute(equation);
    }

    public LiveData<List<Equation>> getAllEquations() {
        return allEquations;
    }

    private static class InsertEquationAsyncTask extends AsyncTask<Equation, Void, Void> {
        private EquationDao equationDao;

        private InsertEquationAsyncTask(EquationDao equationDao) {
            this.equationDao = equationDao;
        }

        @Override
        protected Void doInBackground(Equation... equations) {
            equationDao.insert(equations[0]);
            return null;
        }
    }
}

