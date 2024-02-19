package com.spenditure.presentation;

import android.content.Intent;
import android.os.Bundle;

import com.example.spenditure.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.spenditure.databinding.ActivityMainBinding;
import com.spenditure.presentation.category.ViewCategoryActivity;
import com.spenditure.presentation.transaction.CreateTransactionActivity;
import com.spenditure.presentation.transaction.ViewTransactionsActivity;


public class ViewReportActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        BottomNavigationView navView = findViewById(R.id.nav_view);
        navView.setSelectedItemId(R.id.navigation_home);

        navBarHandling();
    }

    // Handle the bottom navigation bar
    private void navBarHandling(){
        BottomNavigationView navView = findViewById(R.id.nav_view);
        navView.setSelectedItemId(R.id.navigation_home);

        navView.setOnItemSelectedListener((item -> {
            if (item.getItemId() == R.id.navigation_home) {
                return true;
            } else if (item.getItemId() == R.id.navigation_create_transaction) {
                startActivity(new Intent(getApplicationContext(), CreateTransactionActivity.class));
                return true;
            } else if (item.getItemId() == R.id.navigation_view_transactions) {
                startActivity(new Intent(getApplicationContext(), ViewTransactionsActivity.class));
                return true;
            }else if(item.getItemId() == R.id.navigation_category){
                startActivity(new Intent(getApplicationContext(), ViewCategoryActivity.class));
                return true;
            }else {
                return false;
            }
        }));
    }
}