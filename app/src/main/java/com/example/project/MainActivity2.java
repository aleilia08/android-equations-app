package com.example.project;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import com.example.project.database.Equation;
import com.example.project.database.EquationViewModel;

public class MainActivity2 extends AppCompatActivity {

    private EditText coefficientA, coefficientB, coefficientC, coefficientD;
    private Button solveButton, openWikiButton, helpButton, saveButton, viewSavedButton;
    private TextView solutionText, instruction;
    private EquationViewModel equationViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        equationViewModel = new ViewModelProvider(this).get(EquationViewModel.class);

        coefficientA = findViewById(R.id.coefficient_a);
        coefficientB = findViewById(R.id.coefficient_b);
        coefficientC = findViewById(R.id.coefficient_c);
        coefficientD = findViewById(R.id.coefficient_d);
        solveButton = findViewById(R.id.solve_button);
        openWikiButton = findViewById(R.id.open_wiki_button);
        solutionText = findViewById(R.id.solution_text);
        instruction = findViewById(R.id.instruction);
        helpButton = findViewById(R.id.help_button);
        saveButton = findViewById(R.id.save_button);
        viewSavedButton = findViewById(R.id.view_saved_button);

        solveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    // Get coefficients entered by the user
                    double a = Double.parseDouble(coefficientA.getText().toString());
                    double b = Double.parseDouble(coefficientB.getText().toString());
                    double c = Double.parseDouble(coefficientC.getText().toString());
                    double d = Double.parseDouble(coefficientD.getText().toString());

                    // Solve the cubic equation
                    String result = solveCubicEquation(a, b, c, d);
                    solutionText.setText(result);
                    instruction.setText(getInstructionText(a, b, c, d));
                } catch (NumberFormatException e) {
                    Toast.makeText(MainActivity2.this, "Please enter valid coefficients", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Set onClickListener for the openWikiButton
        openWikiButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open the Wikipedia page for cubic equations
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://en.wikipedia.org/wiki/Cubic_function"));
                startActivity(browserIntent);
            }
        });

        // Set onClickListener for the help button
        helpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to the HelpPage
                Intent helpIntent = new Intent(MainActivity2.this, HelpPageActivity.class);
                startActivity(helpIntent);
            }
        });

        // Set onClickListener for the save button
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Save equation and solution to database
                String equation = coefficientA.getText().toString() + "x^3 + " + coefficientB.getText().toString() + "x^2 + " + coefficientC.getText().toString() + "x + " + coefficientD.getText().toString() + " = 0";
                String solution = solutionText.getText().toString();
                Equation newEquation = new Equation(equation, solution);
                equationViewModel.insert(newEquation);
                Toast.makeText(MainActivity2.this, "Equation saved", Toast.LENGTH_SHORT).show();
            }
        });

        // Set onClickListener for the viewSaved button
        viewSavedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to the SavedEquationsActivity
                Intent viewSavedIntent = new Intent(MainActivity2.this, SavedEquationsActivity.class);
                startActivity(viewSavedIntent);
            }
        });
    }

    private String solveCubicEquation(double a, double b, double c, double d) {
        if (a == 0) {
            return solveQuadraticEquation(b, c, d);
        }

        // Normalize the coefficients
        b /= a;
        c /= a;
        d /= a;

        double delta0 = b * b - 3 * c;
        double delta1 = 2 * b * b * b - 9 * b * c + 27 * d;
        double discriminant = delta1 * delta1 - 4 * delta0 * delta0 * delta0;

        if (discriminant >= 0) {
            double C = Math.cbrt((delta1 + Math.sqrt(discriminant)) / 2);
            double root1 = -1 / 3.0 * b + C + delta0 / C;
            double root2 = -1 / 3.0 * b - (C + delta0 / C) / 2;
            double root3 = root2;

            if (discriminant == 0) {
                root3 = -1 / 3.0 * b - (C + delta0 / C) / 2;
                return "One real root: " + root1 + "\nTwo equal real roots: " + root2 + ", " + root3;
            } else {
                return "Three real roots: " + root1 + ", " + root2 + ", " + root3;
            }
        } else {
            double C = Math.cbrt((delta1 + Math.sqrt(discriminant)) / 2);
            double realPart = -1 / 3.0 * b + C;
            double imaginaryPart = Math.sqrt(3) * (C - delta0 / C) / 2;

            return "One real root: " + realPart + "\nTwo complex roots: " +
                    (-1 / 3.0 * b - C / 2) + " + " + imaginaryPart + "i, " +
                    (-1 / 3.0 * b - C / 2) + " - " + imaginaryPart + "i";
        }
    }

    private String solveQuadraticEquation(double a, double b, double c) {
        if (a == 0) {
            if (b == 0) {
                return c == 0 ? "Infinite solutions" : "No solution";
            } else {
                double solution = -c / b;
                return "Linear solution: " + solution;
            }
        } else {
            double discriminant = b * b - 4 * a * c;
            if (discriminant < 0) {
                return "No real solutions\nComplex roots: " +
                        (-b / (2 * a)) + " + " + (Math.sqrt(-discriminant) / (2 * a)) + "i, " +
                        (-b / (2 * a)) + " - " + (Math.sqrt(-discriminant) / (2 * a)) + "i";
            } else if (discriminant == 0) {
                double solution = -b / (2 * a);
                return "Single real solution: " + solution;
            } else {
                double root1 = (-b + Math.sqrt(discriminant)) / (2 * a);
                double root2 = (-b - Math.sqrt(discriminant)) / (2 * a);
                return "Two real solutions: " + root1 + " and " + root2;
            }
        }
    }

    private String getInstructionText(double a, double b, double c, double d) {
        return "Steps to solve the cubic equation will be displayed here.";
    }
}
