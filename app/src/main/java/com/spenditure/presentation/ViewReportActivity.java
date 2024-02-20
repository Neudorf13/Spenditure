package com.spenditure.presentation;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.spenditure.R;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.spenditure.databinding.ActivityMainBinding;
import com.spenditure.presentation.category.ViewCategoryActivity;
import com.spenditure.presentation.transaction.CreateTransactionActivity;
import com.spenditure.presentation.transaction.ViewTransactionsActivity;

import java.util.ArrayList;


public class ViewReportActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private ViewPager viewPager;
    private Slider_Adapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        viewPager = findViewById(R.id.viewpager_report);
        adapter = new Slider_Adapter(this);
        viewPager.setAdapter(adapter);


        BottomNavigationView navView = findViewById(R.id.nav_view);
        navView.setSelectedItemId(R.id.navigation_home);
        Spinner spinner = findViewById(R.id.spinner_report);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();
                Toast.makeText(ViewReportActivity.this,item,Toast.LENGTH_SHORT).show();


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ArrayList<String> choices = new ArrayList<>();
        choices.add("Report by average");
        choices.add("Report by total");
        choices.add("Report by percentage");
        ArrayAdapter<String>adapter = new ArrayAdapter<>(this, R.layout.custom_snipper_report,choices);

        adapter.setDropDownViewResource( android.R.layout.select_dialog_singlechoice);
        spinner.setAdapter(adapter);




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