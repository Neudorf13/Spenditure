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

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
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

import java.time.LocalDateTime;

public class CreateTransactionActivity extends AppCompatActivity {

    private CustomCategorySpinnerAdapter adapter;
    private DateTime selectedDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_transaction);

        setUpCreateButton();
        setUpCategories();
        setUpDatePicker();
        navBarHandling();
    }

    // Set up the category drop down menu
    private void setUpCategories() {
        CategoryHandler categoryHandler = new CategoryHandler(true);

        Spinner categories = (Spinner) findViewById(R.id.spinner_categories);

        // Create adapter to display the categories
        adapter = new CustomCategorySpinnerAdapter(categoryHandler.getAllCategory(), this);
        categories.setAdapter(adapter);
    }

    // Set up the date picker
    private void setUpDatePicker() {
        EditText dateField = (EditText) findViewById(R.id.edittext_date);
        // Default to today's date
        LocalDateTime today = LocalDateTime.now();
        selectedDate = new DateTime(today.getYear(), today.getMonthValue(), today.getDayOfMonth());

        // Create event for when a new date is selected
        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                selectedDate = new DateTime(year, month, day);
                dateField.setText(selectedDate.toString());
            }
        };

        // Create event for when the date field is selected
        dateField.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog dialog = new DatePickerDialog(
                        CreateTransactionActivity.this,
                        date,
                        selectedDate.getYear(),
                        selectedDate.getMonth(),
                        selectedDate.getDay()
                );

                dialog.show();
            }
        });
    }

    // Set up the Create Transaction Button
    private void setUpCreateButton() {
        Button button = (Button) findViewById(R.id.button_create_transaction);

        // Set up click event
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
        EditText place = (EditText) findViewById(R.id.edittext_place);
        EditText amount = (EditText) findViewById(R.id.edittext_amount);
        EditText comments = (EditText) findViewById(R.id.edittext_comments);
        AppCompatToggleButton type = (AppCompatToggleButton) findViewById(R.id.togglebutton_type);
        Spinner category = (Spinner) findViewById(R.id.spinner_categories);

        // Create the new transaction object
        Transaction newTransaction = new Transaction(
                -1,
                whatTheHeck.getText().toString(),
                selectedDate,
                place.getText().toString(),
                Double.parseDouble(amount.getText().toString()),
                comments.getText().toString(),
                type.isChecked(),
                (MainCategory) category.getSelectedItem()
        );

        return newTransaction;
    };
}