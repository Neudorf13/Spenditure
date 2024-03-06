package com.spenditure.presentation.category;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.spenditure.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.spenditure.database.utils.DBHelper;
import com.spenditure.logic.CategoryHandler;
import com.spenditure.logic.UserManager;
import com.spenditure.presentation.BottomNavigationHandler;
import com.spenditure.presentation.TouchHelper;
import com.spenditure.presentation.transaction.CreateTransactionActivity;
import com.spenditure.presentation.transaction.ViewTransactionsActivity;


/**
 * Adapter for Category Main activity
 * @author Bao Ngo
 * @version 04 Mar 2024
 */
public class ViewCategoryActivity extends AppCompatActivity {

    private CategoryHandler categoryHandler = null;
    private RecyclerView recyclerView;
    private FloatingActionButton mFab;
    private CategoryAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DBHelper.copyDatabaseToDevice(getApplicationContext());
        categoryHandler = new CategoryHandler(false);
//        categoryHandler = new CategoryHandler(false);
        setContentView(R.layout.activity_view_category);
        recyclerView = findViewById(R.id.recyclerview_category);
        mFab = findViewById(R.id.floatingActionButton_category);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new CategoryAdapter(ViewCategoryActivity.this, categoryHandler.getAllCategory(UserManager.getUserID()));
        recyclerView.setAdapter(adapter);

        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AddNewCategory.newInstance().show(getSupportFragmentManager(),AddNewCategory.TAG);
                adapter.notifyDataSetChanged();
            }
        });

        //We are planning to let user remove category, but this react is unexpected, so we leave this for future work
//        ItemTouchHelper itemTouchHelper = new ItemTouchHelper((new TouchHelper(adapter)));
//        itemTouchHelper.attachToRecyclerView(recyclerView);

        navBarHandling();

    }

    private void navBarHandling(){ //Handling input to nav bar to move it to right activity
        BottomNavigationView navView = findViewById(R.id.nav_view);

        BottomNavigationHandler navigationHandler = new BottomNavigationHandler();

        navView.setOnItemSelectedListener((item -> {
            if (item.getItemId() == R.id.navigation_category){
                return false;   //Default setup provided by Android Studio
            }
            Class<? extends AppCompatActivity> newActivity = navigationHandler.select(item.getItemId());
            if(newActivity != null){
                startActivity(new Intent(getApplicationContext(), newActivity));
                return true; //Default setup provided by Android Studio
            }
            return false; //Default setup provided by Android Studio
        }));

        // Set the selected item if needed
        navView.setSelectedItemId(R.id.navigation_category);
    }
}