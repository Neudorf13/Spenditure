package com.spenditure.presentation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatToggleButton;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ArrayAdapter;

import com.example.spenditure.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.spenditure.application.MainActivity;
import com.spenditure.application.Services;
import com.spenditure.database.TransactionPersistence;
import com.spenditure.logic.TransactionHandler;
import com.spenditure.object.Transaction;

import java.util.ArrayList;
import java.util.List;

public class ViewTransactionsActivity extends AppCompatActivity {

    TransactionHandler transactionHandler;
    List<Transaction> transactions;
    int currentIdSelected;

    CustomTransactionAdapter adaptor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_transactions);

        // Get the transactions from the handler
        transactionHandler = new TransactionHandler(true);
        transactions = transactionHandler.getAllByNewestFirst();

        ListView transactionsListView = (ListView)findViewById(R.id.listview_transactions);
        // Create an adaptor to format transactions in the list view
        adaptor = new CustomTransactionAdapter(transactions, getApplicationContext());
        transactionsListView.setAdapter(adaptor);

        // Trigger when list item is selected
        transactionsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Enable the edit and delete buttons
                changeButtonEnabled(true);

                currentIdSelected = (int) id;
            }
        });

        // Trigger when sorting mode is changed
        AppCompatToggleButton sortingMode = (AppCompatToggleButton) findViewById(R.id.togglebutton_transaction_date_sort);
        sortingMode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // True: newest first, False: oldest first
                boolean newestFirst = sortingMode.isChecked();
                changeSortingMode(newestFirst);
            }
        });

        // Bottom nav bar handling
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
                return true;
            } else {
                return false;
            }
        }));
    }

    // Change the sorting mode for the transactions
    private void changeSortingMode(boolean newestFirst){
        ArrayList<Transaction> updatedList;

        if (newestFirst){
            // Newest transactions first
            updatedList = transactionHandler.getAllByNewestFirst();
        } else {
            // Oldest transactions first
            updatedList = transactionHandler.getAllByOldestFirst();
        }

        // Update the list view with the new order
        transactions = updatedList;
        adaptor.notifyDataSetChanged();
    }

    // Change the state of the edit and delete button
    private void changeButtonEnabled(boolean enabled){
        Button editButton = (Button) findViewById(R.id.button_edit);
        Button deleteButton = (Button) findViewById(R.id.button_delete);
        editButton.setEnabled(enabled);
        deleteButton.setEnabled(enabled);
    }

    // Pass the selected transaction to the Edit Transaction Activity
    public void editTransactionClicked(View view){
        // Pass the ID of the selected transaction to be edited
        Intent newIntent = new Intent(getApplicationContext(), EditTransactionActivity.class);
        newIntent.putExtra("selectedTransaction", currentIdSelected);
        startActivity(newIntent);

        changeButtonEnabled(false);
        currentIdSelected = -1;
    }

    // Delete the selected transaction
    public void deleteTransactionClicked(View view){
        // Deselect any items
        ListView transactionsListView = (ListView)findViewById(R.id.listview_transactions);
        transactionsListView.clearChoices();

        // Get and delete the chosen transaction
        Transaction toDelete = transactionHandler.getTransactionByID(currentIdSelected);
        transactionHandler.deleteTransaction(toDelete);

        // Refresh the list view
        adaptor.notifyDataSetChanged();

        changeButtonEnabled(false);
        currentIdSelected = -1;
    }
}