/**
 * CreateTransactionActivity.java
 * <p>
 * COMP3350 SECTION A02
 *
 * @author Jillian Friesen, 7889402
 * @date Tuesday, February 7, 2024
 * <p>
 * PURPOSE:
 * This file contains all the event handlers and UI management for the Create Transaction
 * activity where users can create and save a new transaction.
 **/


package com.spenditure.presentation.transaction;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatToggleButton;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.spenditure.logic.TransactionHandler;
import com.spenditure.logic.UserManager;
import com.spenditure.logic.exceptions.InvalidTransactionException;
import com.spenditure.object.DateTime;
import com.spenditure.object.Transaction;

import com.example.spenditure.R;
import com.spenditure.presentation.ImageCaptureActivity;
import com.spenditure.presentation.ImageViewActivity;
import com.spenditure.presentation.category.ViewCategoryActivity;
import com.spenditure.presentation.report.ViewReportActivity;

public class CreateTransactionActivity extends AppCompatActivity {

    private ActivityResultLauncher<Intent> getImageCaptureResult;
    private byte[] imageBytes;
    private Button viewImageButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_transaction);

        setUpCreateButton();
        navBarHandling();

        setUpImageCaptureButton();
        setUpViewImageButton();
    }

    private void setUpCreateButton() {
        // Set up click event for the Create Transaction Button
        Button button = (Button) findViewById(R.id.button_edit_transaction);
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
                    Toast.makeText(CreateTransactionActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void setUpViewImageButton(){
        viewImageButton = (Button) findViewById(R.id.button_view_image);

        viewImageButton.setOnClickListener(view -> {
            Intent imageViewActivity = new Intent(getApplicationContext(), ImageViewActivity.class);
            imageViewActivity.putExtra("imageBytes", imageBytes);
            startActivity(imageViewActivity);
        });
    }

    // Set up the image capture button
    private void setUpImageCaptureButton() {
        // Initialize the ActivityResultLauncher
        getImageCaptureResult = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        // Handle the result here
                        Intent data = result.getData();
                        if (data != null) {
                            Bundle received = data.getExtras();
                            imageBytes = received.getByteArray("imageBytes");

                            // Enable the view image button now
                            viewImageButton.setEnabled(true);
                        }
                    }
                }
        );

        ImageButton button = (ImageButton) findViewById(R.id.button_take_image);
        button.setOnClickListener(view -> {
            Intent imageCaptureActivity = new Intent(getApplicationContext(), ImageCaptureActivity.class);
            getImageCaptureResult.launch(imageCaptureActivity);
        });
    }

    // Handle the bottom navigation bar
    private void navBarHandling() {
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
            } else if (item.getItemId() == R.id.navigation_category) {
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
        DateTime date = new DateTime(2023, 1, 1, 1, 1, 0); // Set default date for now
        EditText place = (EditText) findViewById(R.id.edittext_place);
        EditText amount = (EditText) findViewById(R.id.edittext_amount);
        EditText comments = (EditText) findViewById(R.id.edittext_comments);
        AppCompatToggleButton type = (AppCompatToggleButton) findViewById(R.id.togglebutton_type);

        // Create the new transaction object
        Transaction newTransaction = new Transaction(UserManager.getUserID(), whatTheHeck.getText().toString(), date, place.getText().toString(), Double.parseDouble(amount.getText().toString()), comments.getText().toString(), type.isChecked());

        // Only add image if it was taken
        if (imageBytes != null) {
            newTransaction.setImage(imageBytes);
        }

        return newTransaction;
    }

    ;
}