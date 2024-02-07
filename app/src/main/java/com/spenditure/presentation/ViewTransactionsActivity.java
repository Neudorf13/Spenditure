package com.spenditure.presentation;

import androidx.appcompat.app.AppCompatActivity;

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
        transactions = transactionHandler.getAllTransactions();

        ListView transactionsListView = (ListView)findViewById(R.id.listview_transactions);
        // Create an adaptor to format transactions in the list view
        adaptor = new CustomTransactionAdapter(transactions, getApplicationContext());
        transactionsListView.setAdapter(adaptor);

        transactionsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Enable the edit and delete buttons
                changeButtonEnabled(true);

                currentIdSelected = (int) id;
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
                return true;
            } else {
                return false;
            }
        }));
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
        // Get and delete the chosen transaction
        Transaction toDelete = transactionHandler.getTransactionByID(currentIdSelected);
        transactionHandler.deleteTransaction(toDelete);

        adaptor.notifyDataSetChanged();

        changeButtonEnabled(false);
        currentIdSelected = -1;
    }
}