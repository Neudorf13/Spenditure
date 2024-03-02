package com.spenditure.presentation;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.spenditure.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.spenditure.logic.UserManager;
import com.spenditure.logic.exceptions.InvalidUserInformationException;
import com.spenditure.presentation.report.ViewReportActivity;

public class ViewProfileActivity extends AppCompatActivity {

    private String username;
    private String currentPassword;
    private String newPassword;
    private UserManager userManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        userManager = new UserManager(true);
        setContentView(R.layout.activity_view_profile);

        setUpFields();
        setUpSaveButton();
        setUpLogoutButton();

        navBarHandling();
    }

    // Set up the UI fields
    private void setUpFields(){
        EditText usernameField = (EditText) findViewById(R.id.edittext_username);
        usernameField.setText(userManager.getUserName(UserManager.getUserID()));

        EditText currentPasswordField = (EditText) findViewById(R.id.edittext_current_password);
        currentPasswordField.setText("123");
    }

    // Set up the Save Changes button
    private void setUpSaveButton(){
        Button button = (Button) findViewById(R.id.button_save_changes);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                EditText usernameField = (EditText) findViewById(R.id.edittext_username);
                username = usernameField.getText().toString();

                EditText currentPasswordField = (EditText) findViewById(R.id.edittext_current_password);
                currentPassword = currentPasswordField.getText().toString();

                EditText newPasswordField = (EditText) findViewById(R.id.edittext_new_password);
                newPassword = newPasswordField.getText().toString();

                try {
                    // Try to change both values
                    userManager.changeUsername(userManager.getUserID(), username);
                    userManager.changePassword(userManager.getUserID(), currentPassword, newPassword);

                    Toast.makeText(ViewProfileActivity.this, "Changes saved successfully",Toast.LENGTH_LONG).show();
                } catch (InvalidUserInformationException e){
                    Toast.makeText(ViewProfileActivity.this, e.getMessage(),Toast.LENGTH_LONG).show();
                    usernameField.setText("");
                    currentPasswordField.setText("");
                }
            }
        });
    }

    // Set up the logout button
    private void setUpLogoutButton(){
        Button button = (Button) findViewById(R.id.button_logout);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                try {
                    userManager.logout();

                    Intent newIntent = new Intent(getApplicationContext(), LoginActivity.class);
                    startActivity(newIntent);
                } catch (InvalidUserInformationException e){
                    Toast.makeText(ViewProfileActivity.this, e.getMessage(),Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void navBarHandling(){
        BottomNavigationView navView = findViewById(R.id.nav_view);

        BottomNavigationHandler navigationHandler = new BottomNavigationHandler();

        navView.setOnItemSelectedListener((item -> {
            if (item.getItemId() == R.id.navigation_user){
                return false;
            }
            Class<? extends AppCompatActivity> newActivity = navigationHandler.select(item.getItemId());
            if(newActivity != null){
                startActivity(new Intent(getApplicationContext(), newActivity));
                return true;
            }
            return false;
        }));

        // Set the selected item if needed
        navView.setSelectedItemId(R.id.navigation_user);
    }
}
