/**
 * RegistrationActivity.java
 * <p>
 * COMP3350 SECTION A02
 *
 * @author Jillian Friesen, 7889402
 * @date Monday, March 18, 2024
 * <p>
 * PURPOSE:
 * This file contains all the event handlers and UI management for the Registration
 * activity where users can create a new account.
 **/

package com.spenditure.presentation;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.spenditure.R;
import com.spenditure.application.Services;
import com.spenditure.logic.IUserManager;

import com.spenditure.logic.SecurityQuestionHandler;
import com.spenditure.logic.UserManager;
import com.spenditure.logic.exceptions.InvalidUserInformationException;
import com.spenditure.object.SecurityQuestion;

import java.security.NoSuchAlgorithmException;

public class RegistrationActivity extends AppCompatActivity {
    private IUserManager userManager;
    private SecurityQuestionHandler securityQuestionHandler;
    private FloatingActionButton backButton;
    private Button registerButton;
    private CustomSecurityQuestionSpinnerActivity adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        userManager = new UserManager(Services.DEVELOPING_STATUS);
        securityQuestionHandler = new SecurityQuestionHandler(Services.DEVELOPING_STATUS);

        setupSecurityQuestions();
        setupRegisterButton();
        setupBackButton();
    }

    private void setupSecurityQuestions() {
        Spinner questions = findViewById(R.id.spinner_security_question);

        // Create adapter to display the categories
        adapter = new CustomSecurityQuestionSpinnerActivity(securityQuestionHandler.getAllSecurityQuestions(), this);
        questions.setAdapter(adapter);
    }

    // Set up the Registration button
    private void setupRegisterButton() {
        registerButton = findViewById(R.id.button_register);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Register the new user
                registerNewUser();

                // Return to the calling activity
                finish();
            }
        });
    }

    private void setupBackButton() {
        backButton = findViewById(R.id.floatingActionButton_back);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Return to the calling activity
                finish();
            }
        });
    }

    // Collect all the new user information and pass it to the user handler
    private void registerNewUser() {
        EditText username = findViewById(R.id.edittext_username);
        EditText password = findViewById(R.id.edittext_current_password);
        EditText email = findViewById(R.id.edittext_email);
        Spinner question = findViewById(R.id.spinner_security_question);
        SecurityQuestion securityQuestion = adapter.getItem(question.getSelectedItemPosition());
        EditText questionAnswer = findViewById(R.id.edittext_question_answer);

        try {
            userManager.register(
                    username.getText().toString(),
                    password.getText().toString(),
                    email.getText().toString(),
                    questionAnswer.getText().toString(),
                    securityQuestion.getSid());
        } catch (InvalidUserInformationException e) {
            Toast.makeText(RegistrationActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
        } catch (NoSuchAlgorithmException e) {
            Toast.makeText(RegistrationActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
}