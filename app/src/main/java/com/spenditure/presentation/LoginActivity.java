/**
 * LoginActivity.java
 * <p>
 * COMP3350 SECTION A02
 *
 * @author Jillian Friesen, 7889402
 * @date Tuesday, March 5, 2024
 * <p>
 * PURPOSE:
 * This file handles the Login screen and allows users to enter their credentials to access the rest of the app.
 **/

package com.spenditure.presentation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.spenditure.R;
import com.spenditure.application.Services;
import com.spenditure.logic.UserManager;
import com.spenditure.logic.exceptions.InvalidUserInformationException;
import com.spenditure.presentation.report.ViewReportActivity;

public class LoginActivity extends AppCompatActivity {

    // Instance variables
    private String username;
    private String password;
    private UserManager userManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        userManager = new UserManager(Services.DEVELOPING_STATUS);
        setContentView(R.layout.activity_login);

        setUpLoginButton();
    }

    // Set up the login button
    private void setUpLoginButton() {
        Button button = findViewById(R.id.button_login);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                EditText user = findViewById(R.id.edittext_username);
                username = user.getText().toString();

                EditText pass = findViewById(R.id.edittext_current_password);
                password = pass.getText().toString();

                try {
                    int userID = userManager.login(username, password);

                    if (userID != 0){
                        // Login and open the report activity
                        Intent newIntent = new Intent(getApplicationContext(), ViewReportActivity.class);
                        startActivity(newIntent);
                    }
                } catch (InvalidUserInformationException e){
                    Toast.makeText(LoginActivity.this, "Unable to log in: " + e.getMessage(),Toast.LENGTH_LONG).show();
                    user.setText("");
                    pass.setText("");
                }
            }
        });
    }
}