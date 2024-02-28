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

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.spenditure.logic.TransactionHandler;
import com.spenditure.logic.CategoryHandler;
import com.spenditure.object.DateTime;
import com.spenditure.object.MainCategory;
import com.spenditure.object.Transaction;

import com.example.spenditure.R;
import com.spenditure.presentation.category.CustomCategorySpinnerAdapter;
import com.spenditure.presentation.category.ViewCategoryActivity;
import com.spenditure.presentation.report.ViewReportActivity;

public class CreateTransactionActivity extends AppCompatActivity {

    private CustomCategorySpinnerAdapter adapter;

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

                TransactionHandler handler = new TransactionHandler(true);
                handler.addTransaction(newTransaction);

                // Return to the main window
                startActivity(new Intent(getApplicationContext(), ViewReportActivity.class));
            }
        });

        navBarHandling();

        // Populate the Category choices
        CategoryHandler categoryHandler = new CategoryHandler(true);

        Spinner categories = (Spinner) findViewById(R.id.spinner_categories);
        adapter = new CustomCategorySpinnerAdapter(categoryHandler.getAllCategory(), this);
        categories.setAdapter(adapter);
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
        DateTime date = new DateTime(2023,1,1,1,1); // Set default date for now
        EditText place = (EditText) findViewById(R.id.edittext_place);
        EditText amount = (EditText) findViewById(R.id.edittext_amount);
        EditText comments = (EditText) findViewById(R.id.edittext_comments);
        AppCompatToggleButton type = (AppCompatToggleButton) findViewById(R.id.togglebutton_type);
        Spinner category = (Spinner) findViewById(R.id.spinner_categories);

        // Create the new transaction object
        Transaction newTransaction = new Transaction(
                -1,
                whatTheHeck.getText().toString(),
                date,
                place.getText().toString(),
                Double.parseDouble(amount.getText().toString()),
                comments.getText().toString(),
                type.isChecked(),
                (MainCategory) category.getSelectedItem());

        return newTransaction;
    };
}