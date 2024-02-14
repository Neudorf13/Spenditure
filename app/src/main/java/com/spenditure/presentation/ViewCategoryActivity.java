package com.spenditure.presentation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.spenditure.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.*;

public class ViewCategoryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_category);
        navBarHandling();

    }

//    private void navBarHandling(){
//        BottomNavigationView navView = findViewById(R.id.nav_view);
//        navView.setSelectedItemId(R.id.navigation_category);
//
//        navView.setOnItemSelectedListener((item -> {
//            if (item.getItemId() == R.id.navigation_home) {
//                startActivity(new Intent(getApplicationContext(), ViewReportActivity.class));
//                return true;
//            } else if (item.getItemId() == R.id.navigation_create_transaction) {
//                startActivity(new Intent(getApplicationContext(), CreateTransactionActivity.class));
//                return true;
//            } else if (item.getItemId() == R.id.navigation_view_transactions) {
//                startActivity(new Intent(getApplicationContext(), ViewTransactionsActivity.class));
//                return true;
//            }else if (item.getItemId() == R.id.navigation_category) {
//                return true;
//            } else {
//                return false;
//            }
//        }));
//    }

    private void navBarHandling(){
        BottomNavigationView navView = findViewById(R.id.nav_view);
        Map<Integer, Class<? extends AppCompatActivity>> activityMap = new HashMap<Integer,Class<? extends AppCompatActivity>>();
        activityMap.put(R.id.navigation_home, ViewReportActivity.class);
        activityMap.put(R.id.navigation_create_transaction, CreateTransactionActivity.class);
        activityMap.put(R.id.navigation_view_transactions, ViewTransactionsActivity.class);

        BottomNavigationHandler navigationHandler = new BottomNavigationHandler(this, activityMap);
        navigationHandler.setupWithNavController(navView);

        // Set the selected item if needed
        navView.setSelectedItemId(R.id.navigation_category);
    }
}