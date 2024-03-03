/**
 * EditTransactionActivity.java
 *
 * COMP3350 SECTION A02
 *
 * @author Jillian Friesen, 7889402
 * @date Tuesday, February 7, 2024
 *
 * PURPOSE:
 *  This file contains all the event handlers and UI management for the Edit Transaction
 *  activity where users can modify and save an existing transaction.
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

import com.example.spenditure.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.spenditure.logic.CategoryHandler;
import com.spenditure.logic.TransactionHandler;
import com.spenditure.logic.UserManager;
import com.spenditure.object.DateTime;
import com.spenditure.object.MainCategory;
import com.spenditure.object.Transaction;
import com.spenditure.presentation.BottomNavigationHandler;
import com.spenditure.presentation.category.CustomCategorySpinnerAdapter;
import com.spenditure.presentation.report.ViewReportActivity;

import java.time.LocalDateTime;

public class EditTransactionActivity extends AppCompatActivity {

    // Instance Variables
    private Transaction givenTransaction;
    private CustomCategorySpinnerAdapter adapter;
    private DateTime selectedDate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_transaction);

        int givenID = -1;

        // Get the passed transaction ID
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            givenID = extras.getInt("selectedTransaction");
        }

        // Get the transaction
        TransactionHandler handler = new TransactionHandler(true);
        givenTransaction = handler.getTransactionByID(givenID);

        setUpCategories();

        // Populate the UI fields
        populateTransactionFields(givenTransaction);

        setUpEditButton();
        setUpDatePicker();
        navBarHandling();
    }

    // Set up the category drop down menu
    private void setUpCategories() {
        CategoryHandler categoryHandler = new CategoryHandler(true);

        Spinner categories = (Spinner) findViewById(R.id.spinner_categories);

        // Create adapter to display the categories
        adapter = new CustomCategorySpinnerAdapter(categoryHandler.getAllCategory(UserManager.getUserID()), this);
        categories.setAdapter(adapter);
    }

    // Set up the date picker
    private void setUpDatePicker() {
        EditText dateField = (EditText) findViewById(R.id.edittext_date);
        selectedDate = new DateTime(
                givenTransaction.getDateTime().getYear(),
                givenTransaction.getDateTime().getMonth(),
                givenTransaction.getDateTime().getDay()
        );
        dateField.setText(selectedDate.toString());

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
                        EditTransactionActivity.this,
                        date,
                        selectedDate.getYear(),
                        selectedDate.getMonth(),
                        selectedDate.getDay()
                );

                dialog.show();
            }
        });
    }

    // Set up the Edit Transaction Button
    private void setUpEditButton() {
        Button button = (Button) findViewById(R.id.button_edit_transaction);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Call helper method
                Transaction updatedTransaction = editTransaction();

                TransactionHandler handler = new TransactionHandler(true);
                handler.modifyTransaction(updatedTransaction);

                startActivity(new Intent(getApplicationContext(), ViewTransactionsActivity.class));
            }
        });
    }

    // Handle the bottom navigation bar
    private void navBarHandling(){
        BottomNavigationView navView = findViewById(R.id.nav_view);

        BottomNavigationHandler navigationHandler = new BottomNavigationHandler();

        navView.setOnItemSelectedListener((item -> {
            Class<? extends AppCompatActivity> newActivity = navigationHandler.select(item.getItemId());
            if(newActivity != null){
                startActivity(new Intent(getApplicationContext(), newActivity));
                return true;
            }
            return false;
        }));
    }

    // Populate the fields on the UI using the given transaction
    private void populateTransactionFields(Transaction transaction){
        EditText whatTheHeck = (EditText) findViewById(R.id.edittext_what_the_heck);
        whatTheHeck.setText(transaction.getName());

        EditText place = (EditText) findViewById(R.id.edittext_place);
        place.setText(transaction.getPlace());

        EditText amount = (EditText) findViewById(R.id.edittext_amount);
        amount.setText(Double.toString(transaction.getAmount()));

        EditText comments = (EditText) findViewById(R.id.edittext_comments);
        comments.setText(transaction.getComments());

       // Spinner category = (Spinner) findViewById(R.id.spinner_categories);
      //  category.setSelection(adapter.getPosition(transaction.getCategory()));

      //  AppCompatToggleButton type = (AppCompatToggleButton) findViewById(R.id.togglebutton_type);
      //  type.setChecked(transaction.getType());
    }

    // Helper method: return the updated Transaction object made from user-entered info
    private Transaction editTransaction() {
        // Parse all the user fields
        EditText whatTheHeck = (EditText) findViewById(R.id.edittext_what_the_heck);
        DateTime date = new DateTime(2023,1,1,1,1); // Set default date for now
        EditText place = (EditText) findViewById(R.id.edittext_place);
        EditText amount = (EditText) findViewById(R.id.edittext_amount);
        EditText comments = (EditText) findViewById(R.id.edittext_comments);
        AppCompatToggleButton type = (AppCompatToggleButton) findViewById(R.id.togglebutton_type);
        Spinner category = (Spinner) findViewById(R.id.spinner_categories);

        // Create the new transaction object
        /*
        Transaction updatedTransaction = new Transaction(
                givenTransaction.getTransactionID(),
                whatTheHeck.getText().toString(),
                selectedDate,
                place.getText().toString(),
                Double.parseDouble(amount.getText().toString()),
                comments.getText().toString(),
                type.isChecked(),
                (MainCategory) category.getSelectedItem()
        );

         */

      //  return updatedTransaction;
        return null;
    };
}