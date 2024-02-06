package com.spenditure.presentation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatToggleButton;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.spenditure.application.MainActivity;
import com.spenditure.logic.TransactionHandler;
import com.spenditure.object.DateTime;
import com.spenditure.object.Transaction;

import com.example.spenditure.R;

public class CreateTransactionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_transaction);

        // Set up an OnClick event for the Create Transaction Button
        Button button = (Button) findViewById(R.id.button_create_transaction);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Call helper method
                Transaction newTransaction = createTransaction();

                TransactionHandler handler = new TransactionHandler(true);
                handler.addTransaction(newTransaction);
            }
        });

        BottomNavigationView navView = findViewById(R.id.nav_view);
        navView.setSelectedItemId(R.id.navigation_home);

        navView.setOnItemSelectedListener((item -> {
            if (item.getItemId() == R.id.navigation_home) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                return true;
            } else if (item.getItemId() == R.id.navigation_reports) {
                return true;
            } else if (item.getItemId() == R.id.navigation_create_transaction) {
                return true;
            } else if (item.getItemId() == R.id.navigation_view_transactions) {
                startActivity(new Intent(getApplicationContext(), ViewTransactionsActivity.class));
                return true;
            } else {
                return false;
            }
        }));
    }

    // Helper method: creates and return new Transaction object made from user-entered info
    private Transaction createTransaction() {
        // Parse all the user fields
        EditText whatTheHeck = (EditText) findViewById(R.id.edittext_what_the_heck);
        DateTime date = new DateTime(2023,1,1,1,1); // Set default date for now
        EditText place = (EditText) findViewById(R.id.edittext_place);
        EditText amount = (EditText) findViewById(R.id.edittext_amount);
        EditText comments = (EditText) findViewById(R.id.edittext_comments);
        AppCompatToggleButton type = (AppCompatToggleButton) findViewById(R.id.togglebutton_type);

        // Create the new transaction object
        Transaction newTransaction = new Transaction(-1, whatTheHeck.getText().toString(), date, place.getText().toString(), Double.parseDouble(amount.getText().toString()), comments.getText().toString(), type.isChecked());

        return newTransaction;
    };
}