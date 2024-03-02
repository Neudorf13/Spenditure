package com.spenditure.presentation;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.spenditure.R;

public class LoginActivity extends AppCompatActivity {

    private String username;
    private String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    private void setUpLoginButton() {
        Button button = (Button) findViewById(R.id.button_login);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                EditText user = (EditText) findViewById(R.id.edittext_username);
                username = user.getText().toString();

                EditText pass = (EditText) findViewById(R.id.edittext_password);
                password = pass.getText().toString();

                // TODO: call validation method in the logic layer
            }
        });
    }
}