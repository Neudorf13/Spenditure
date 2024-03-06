package com.spenditure.presentation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.spenditure.R;
import com.spenditure.logic.UserManager;
import com.spenditure.logic.exceptions.InvalidUserInformationException;
import com.spenditure.presentation.report.ViewReportActivity;

public class LoginActivity extends AppCompatActivity {

    private String username;
    private String password;
    private UserManager userManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        userManager = new UserManager(true);
        setContentView(R.layout.activity_login);

        setUpLoginButton();
    }

    private void setUpLoginButton() {
        Button button = (Button) findViewById(R.id.button_login);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                EditText user = (EditText) findViewById(R.id.edittext_username);
                username = user.getText().toString();

                EditText pass = (EditText) findViewById(R.id.edittext_current_password);
                password = pass.getText().toString();

                try {
                    int userID = userManager.login(username, password);

                    if (userID != 0){
                        // Login
                        Intent newIntent = new Intent(getApplicationContext(), ViewReportActivity.class);
                        startActivity(newIntent);
                    }
                } catch (InvalidUserInformationException e){
                    Toast.makeText(LoginActivity.this, e.getMessage(),Toast.LENGTH_LONG).show();
                    user.setText("");
                    pass.setText("");
                }
            }
        });
    }
}