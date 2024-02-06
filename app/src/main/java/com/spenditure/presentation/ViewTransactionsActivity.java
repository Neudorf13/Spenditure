package com.spenditure.presentation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
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

    List<Transaction> transactions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_transactions);

        // Get the transactions from the handler
        TransactionHandler transactionHandler = new TransactionHandler(true);
        transactions = transactionHandler.getAllTransactions();

        ListView transactionsListView = (ListView)findViewById(R.id.listview_transactions);
        // Create an adaptor to format transactions in the list view
        CustomTransactionAdapter adaptor = new CustomTransactionAdapter(transactions, getApplicationContext());
        transactionsListView.setAdapter(adaptor);

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
}