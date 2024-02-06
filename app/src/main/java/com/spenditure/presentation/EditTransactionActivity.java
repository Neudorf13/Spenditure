package com.spenditure.presentation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatToggleButton;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.spenditure.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.spenditure.application.MainActivity;
import com.spenditure.logic.TransactionHandler;
import com.spenditure.object.DateTime;
import com.spenditure.object.Transaction;

public class EditTransactionActivity extends AppCompatActivity {

    private Transaction givenTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_transaction);

        populateTransactionFields(givenTransaction);

        // Set up an OnClick event for the Edit Transaction Button
        Button button = (Button) findViewById(R.id.button_edit_transaction);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Call helper method
                Transaction updatedTransaction = editTransaction();

                TransactionHandler handler = new TransactionHandler(true);
                handler.modifyTransaction(updatedTransaction);
            }
        });

        BottomNavigationView navView = findViewById(R.id.nav_view);
        navView.setSelectedItemId(R.id.navigation_home);

        navView.setOnItemSelectedListener((item -> {
            if (item.getItemId() == R.id.navigation_home) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                return true;
            } else if (item.getItemId() == R.id.navigation_create_transaction) {
                startActivity(new Intent(getApplicationContext(), CreateTransactionActivity.class));
                return true;
            } else if (item.getItemId() == R.id.navigation_view_transactions) {
                startActivity(new Intent(getApplicationContext(), ViewTransactionsActivity.class));
                return true;
            } else {
                return false;
            }
        }));
    }

    // Populate the fields on the UI using the given transaction
    private void populateTransactionFields(Transaction transaction){
        // Populate all the user fields
        EditText whatTheHeck = (EditText) findViewById(R.id.edittext_what_the_heck);
        whatTheHeck.setText(transaction.getName());

        DateTime date = new DateTime(2023,1,1,1,1); // Set default date for now
        EditText dateTime = (EditText) findViewById(R.id.edittext_date);

        EditText place = (EditText) findViewById(R.id.edittext_place);
        place.setText(transaction.getPlace());

        EditText amount = (EditText) findViewById(R.id.edittext_amount);
        amount.setText(Double.toString(transaction.getAmount()));

        EditText comments = (EditText) findViewById(R.id.edittext_comments);
        comments.setText(transaction.getComments());

        AppCompatToggleButton type = (AppCompatToggleButton) findViewById(R.id.togglebutton_type);
        type.setChecked(transaction.getType());
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

        // Create the new transaction object
        Transaction updatedTransaction = new Transaction(-1, whatTheHeck.getText().toString(), date, place.getText().toString(), Double.parseDouble(amount.getText().toString()), comments.getText().toString(), type.isChecked());

        return updatedTransaction;
    };
}