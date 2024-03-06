/**
 * CreateTransactionActivity.java
 *
 * COMP3350 SECTION A02
 *
 * @author Jillian Friesen, 7889402
 * @date Tuesday, February 7, 2024
 *
 * PURPOSE:
 *  This file contains all the event handlers and UI management for the Create Transaction
 *  activity where users can create and save a new transaction.
 **/


package com.spenditure.presentation.transaction;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatToggleButton;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.spenditure.logic.TransactionHandler;
import com.spenditure.logic.CategoryHandler;
import com.spenditure.logic.UserManager;
import com.spenditure.logic.exceptions.InvalidTransactionException;
import com.spenditure.object.DateTime;
import com.spenditure.object.Transaction;

import com.example.spenditure.R;
import com.spenditure.presentation.BottomNavigationHandler;
import com.spenditure.presentation.ImageCaptureActivity;
import com.spenditure.presentation.LoginActivity;
import com.spenditure.presentation.category.CustomCategorySpinnerAdapter;
import com.spenditure.presentation.category.ViewCategoryActivity;
import com.spenditure.presentation.report.ViewReportActivity;

public class CreateTransactionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_transaction);

        // Set up click event for the Create Transaction Button
        Button button = (Button) findViewById(R.id.button_create_transaction);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Call helper method
                Transaction newTransaction = createTransaction();

                try {
                    TransactionHandler handler = new TransactionHandler(true);
                    handler.addTransaction(newTransaction);

                    // Return to the main window
                    startActivity(new Intent(getApplicationContext(), ViewReportActivity.class));
                } catch (InvalidTransactionException e) {
                    Toast.makeText(CreateTransactionActivity.this, e.getMessage(),Toast.LENGTH_LONG).show();
                }
            }
        });

        navBarHandling();
    }

    // Set up the image capture button
    private void setUpImageButton() {
        Button button = (Button) findViewById(R.id.button_take_image);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // TODO: open ImageCaptureActivity, should return a byte array to be passed off to the new transaction
                startActivity(new Intent(getApplicationContext(), ImageCaptureActivity.class));
            }
        });
    }

    // Handle the bottom navigation bar
    private void navBarHandling(){
        BottomNavigationView navView = findViewById(R.id.nav_view);
        navView.setSelectedItemId(R.id.navigation_create_transaction);

        navView.setOnItemSelectedListener((item -> {
            if (item.getItemId() == R.id.navigation_home) {
                startActivity(new Intent(getApplicationContext(), ViewReportActivity.class));
                return true;
            } else if (item.getItemId() == R.id.navigation_create_transaction) {
                return true;
            } else if (item.getItemId() == R.id.navigation_view_transactions) {
                startActivity(new Intent(getApplicationContext(), ViewTransactionsActivity.class));
                return true;
            }else if(item.getItemId() == R.id.navigation_category){
                startActivity(new Intent(getApplicationContext(), ViewCategoryActivity.class));
                return true;
            } else {
                return false;
            }
        }));
    }

    // Helper method: create and return new Transaction object made from user-entered info
    private Transaction createTransaction() {
        // Parse all the user fields
        EditText whatTheHeck = (EditText) findViewById(R.id.edittext_what_the_heck);
        DateTime date = new DateTime(2023,1,1,1,1,0); // Set default date for now
        EditText place = (EditText) findViewById(R.id.edittext_place);
        EditText amount = (EditText) findViewById(R.id.edittext_amount);
        EditText comments = (EditText) findViewById(R.id.edittext_comments);
        AppCompatToggleButton type = (AppCompatToggleButton) findViewById(R.id.togglebutton_type);

        // Create the new transaction object
        Transaction newTransaction = new Transaction(UserManager.getUserID(), whatTheHeck.getText().toString(), date, place.getText().toString(), Double.parseDouble(amount.getText().toString()), comments.getText().toString(), type.isChecked());

        return newTransaction;
    };
}