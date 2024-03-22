package com.spenditure.presentation.Goals;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.spenditure.R;
import com.spenditure.application.Services;
import com.spenditure.logic.GoalHandler;
import com.spenditure.logic.IGoalHandler;
import com.spenditure.logic.UserManager;
import com.spenditure.logic.exceptions.InvalidGoalException;
import com.spenditure.object.Goal;
import com.spenditure.presentation.BottomNavigationHandler;

import java.util.List;

public class ViewGoalsActivity extends AppCompatActivity {
    private static IGoalHandler goalHandler = null;
    private List<Goal> goals;
    private int currentIdSelected = -1;
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
        adaptor = new CustomGoalAdapter(goals, this);
        goalsListView.setAdapter(adaptor);

        // Trigger when list item is selected
        goalsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                currentIdSelected = (int) id;
            }
        });

        setUpAddButton();
        setUpDeleteButton();
        navBarHandling();
    }

    private void setUpAddButton() {
        FloatingActionButton button = findViewById(R.id.floatingActionButton_new_goal);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                try {
                    Intent newIntent = new Intent(getApplicationContext(), CreateGoalActivity.class);
                    startActivity(newIntent);
                } catch (InvalidGoalException e){
                    Toast.makeText(ViewGoalsActivity.this, e.getMessage(),Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void setUpDeleteButton() {  // TODO: not functioning yet
        FloatingActionButton button = findViewById(R.id.floatingActionButton_delete);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (currentIdSelected < 0) {
                    // No item selected
                    Toast.makeText(ViewGoalsActivity.this, "No Goal selected",Toast.LENGTH_LONG).show();
                    return;
                }

                // Deselect any items
                goalsListView.clearChoices();

                // Get and delete the chosen goal
                Goal toDelete = goalHandler.getGoalByID(currentIdSelected);
                goalHandler.deleteGoal(toDelete.getGoalID());

                // Refresh the list view
                adaptor = new CustomGoalAdapter(goalHandler.getGoalsForUserID(UserManager.getUserID()), getBaseContext());
                goalsListView.setAdapter(adaptor);

                currentIdSelected = -1;
            }
        });
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