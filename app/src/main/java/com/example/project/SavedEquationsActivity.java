package com.example.project;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.project.database.Equation;
import com.example.project.database.EquationViewModel;

import java.util.List;

public class SavedEquationsActivity extends AppCompatActivity {

    private EquationViewModel equationViewModel;
    private TextView savedEquationsTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_equations);

        savedEquationsTextView = findViewById(R.id.saved_equations_textview);

        equationViewModel = new ViewModelProvider(this).get(EquationViewModel.class);

        // Fetch saved equations from the database
        equationViewModel.getAllEquations().observe(this, new Observer<List<Equation>>() {
            @Override
            public void onChanged(List<Equation> equations) {
                StringBuilder displayText = new StringBuilder();
                for (Equation equation : equations) {
                    displayText.append("Equation: ").append(equation.getEquation()).append("\n")
                            .append("Solution: ").append(equation.getSolution()).append("\n\n");
                }
                savedEquationsTextView.setText(displayText.toString());
            }
        });
    }
}
