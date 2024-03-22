package com.spenditure.presentation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.spenditure.R;
import com.spenditure.application.Services;
import com.spenditure.logic.GoalHandler;
import com.spenditure.logic.IGoalHandler;
import com.spenditure.logic.TransactionHandler;
import com.spenditure.logic.UserManager;
import com.spenditure.object.Goal;
import com.spenditure.presentation.transaction.CustomTransactionAdapter;

import org.checkerframework.framework.qual.DefaultQualifier;

import java.util.List;

public class ViewGoalsActivity extends AppCompatActivity {
    private static IGoalHandler goalHandler = null;
    private List<Goal> goals;
    private int currentIdSelected;
    private CustomGoalAdapter adaptor; // TODO
    private ListView goalsListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_goals);

        if (goalHandler == null) {
            goalHandler = new GoalHandler(Services.DEVELOPING_STATUS);
        }

        goals = goalHandler.getGoalsForUserID(UserManager.getUserID());

        goalsListView = findViewById(R.id.listview_goals);

        // Create an adaptor to format transactions in the list view
        adaptor = new CustomGoalAdapter(goals, this);    // TODO
        goalsListView.setAdapter(adaptor);

        // Trigger when list item is selected
        goalsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Enable the delete button
                deleteButtonChangeState(true);

                currentIdSelected = (int) id;
            }
        });

        navBarHandling();
    }

    // Change the state of the delete button
    private void deleteButtonChangeState(boolean enabled){
        FloatingActionButton deleteButton = findViewById(R.id.floatingActionButton_delete);
        deleteButton.setEnabled(enabled);

        if (!enabled) {
            goalsListView.setSelection(-1);
            currentIdSelected = -1;
        }
    }

    // Handle the bottom navigation bar
    private void navBarHandling(){
        BottomNavigationView navView = findViewById(R.id.nav_view);

        BottomNavigationHandler navigationHandler = new BottomNavigationHandler();

        navView.setOnItemSelectedListener((item -> {
            if (item.getItemId() == R.id.navigation_view_transactions){ // TODO
                return false;
            }
            Class<? extends AppCompatActivity> newActivity = navigationHandler.select(item.getItemId());
            if(newActivity != null){
                startActivity(new Intent(getApplicationContext(), newActivity));
                return true;
            }
            return false;
        }));

        // Set the selected item if needed
        navView.setSelectedItemId(R.id.navigation_view_transactions);   // TODO
    }
}