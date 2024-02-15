package com.spenditure.presentation;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.spenditure.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.HashMap;
import java.util.Map;

public class BottomNavigationHandler {
    private final Context context;
    private final Map<Integer, Class<? extends AppCompatActivity>> activityMap;

    public BottomNavigationHandler(Context context) {
        this.context = context;
        this.activityMap = NavigationConfig.ACTIVITY_MAP;
    }

    public void setupWithNavController(BottomNavigationView navView) {
        navView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            Class<? extends Activity> activityClass = activityMap.get(itemId);
            if (activityClass != null) {
                context.startActivity(new Intent(context, activityClass));
                return true;
            }
            return false;
        });
    }
}

class NavigationConfig {
    public static final Map<Integer, Class<? extends AppCompatActivity>> ACTIVITY_MAP = new HashMap<>();

    static {
        ACTIVITY_MAP.put(R.id.navigation_home, ViewReportActivity.class);
        ACTIVITY_MAP.put(R.id.navigation_create_transaction, CreateTransactionActivity.class);
        ACTIVITY_MAP.put(R.id.navigation_view_transactions, ViewTransactionsActivity.class);
        // Add other mappings as needed
    }
}