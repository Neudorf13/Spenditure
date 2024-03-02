package com.spenditure.presentation.category;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.spenditure.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.spenditure.logic.CategoryHandler;
import com.spenditure.logic.UserManager;
import com.spenditure.presentation.BottomNavigationHandler;
import com.spenditure.presentation.TouchHelper;
import com.spenditure.presentation.transaction.CreateTransactionActivity;
import com.spenditure.presentation.transaction.ViewTransactionsActivity;

public class ViewCategoryActivity extends AppCompatActivity {

    private CategoryHandler categoryHandler = null;
    private RecyclerView recyclerView;
    private FloatingActionButton mFab;
    private CategoryAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        categoryHandler = new CategoryHandler(true);
        setContentView(R.layout.activity_view_category);
        recyclerView = findViewById(R.id.recyclerview_category);
        mFab = findViewById(R.id.floatingActionButton_category);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddNewCategory.newInstance().show(getSupportFragmentManager(),AddNewCategory.TAG);
                adapter.notifyDataSetChanged();
            }
        });

        adapter = new CategoryAdapter(ViewCategoryActivity.this, categoryHandler.getAllCategory(UserManager.getUserID()));
        recyclerView.setAdapter(adapter);


        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new TouchHelper(adapter));
        itemTouchHelper.attachToRecyclerView(recyclerView);

        navBarHandling();

    }



    private void navBarHandling(){
        BottomNavigationView navView = findViewById(R.id.nav_view);

        BottomNavigationHandler navigationHandler = new BottomNavigationHandler();

        navView.setOnItemSelectedListener((item -> {
            if (item.getItemId() == R.id.navigation_category){
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
        navView.setSelectedItemId(R.id.navigation_category);
    }
}