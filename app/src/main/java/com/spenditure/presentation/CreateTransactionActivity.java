package com.spenditure.presentation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatToggleButton;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

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
                createTransaction();
            }
        });
    }

    // Helper method: creates and return new Transaction object made from user-entered info
    private Transaction createTransaction(){
        // Parse all the user fields
        EditText whatTheHeck = (EditText) findViewById(R.id.edittext_what_the_heck);
        DateTime date = new DateTime(1,1,1,1,1);
        EditText place = (EditText) findViewById(R.id.edittext_place);
        EditText amount = (EditText) findViewById(R.id.edittext_amount);
        EditText comments = (EditText) findViewById(R.id.edittext_comments);
        AppCompatToggleButton type = (AppCompatToggleButton) findViewById(R.id.togglebutton_type);

        // Create the new transaction object
        Transaction newTransaction = new Transaction(1, whatTheHeck.getText().toString(), date, place.getText().toString(), Double.parseDouble(amount.getText().toString()), comments.getText().toString(), type.isChecked());

        return newTransaction;
    };
}