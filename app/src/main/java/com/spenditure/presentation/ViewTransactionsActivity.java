/**
 * ViewTransactionsActivity.java
 *
 * COMP3350 SECTION A02
 *
 * @author Jillian Friesen, 7889402
 * @date Tuesday, February 7, 2024
 *
 * PURPOSE:
 *  This file contains all the event handlers and UI management for the View Transactions
 *  activity where users can view a list of all their transactions. The UI allows users
 *  to sort these transaction by date and to edit/delete transactions.
 **/

package com.spenditure.presentation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatToggleButton;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.example.spenditure.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.spenditure.logic.TransactionHandler;
import com.spenditure.object.Transaction;

import java.util.ArrayList;
import java.util.List;

public class ViewTransactionsActivity extends AppCompatActivity {

    // Instance Variables
    private static TransactionHandler transactionHandler = null;
    private List<Transaction> transactions;
    private int currentIdSelected;
    private CustomTransactionAdapter adaptor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_transactions);

        // Get all the transactions from the handler
        if( transactionHandler == null){
            transactionHandler = new TransactionHandler(true);
        }
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

        navBarHandling();
    }

    // Handle the bottom navigation bar
    private void navBarHandling(){
        BottomNavigationView navView = findViewById(R.id.nav_view);
        navView.setSelectedItemId(R.id.navigation_view_transactions);

        navView.setOnItemSelectedListener((item -> {
            if (item.getItemId() == R.id.navigation_home) {
                startActivity(new Intent(getApplicationContext(), ViewReportActivity.class));
                return true;
            } else if (item.getItemId() == R.id.navigation_create_transaction) {
                startActivity(new Intent(getApplicationContext(), CreateTransactionActivity.class));
                return true;
            } else if (item.getItemId() == R.id.navigation_view_transactions) {
                return true;
            }else if(item.getItemId() == R.id.navigation_category){
                startActivity(new Intent(getApplicationContext(), ViewCategoryActivity.class));
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
        newIntent.putExtra("transaction_handler",transactionHandler);
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