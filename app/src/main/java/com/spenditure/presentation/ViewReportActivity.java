package com.spenditure.presentation;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.spenditure.R;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.spenditure.databinding.ActivityMainBinding;
import com.spenditure.logic.CategoryHandler;
import com.spenditure.logic.ReportManager;
import com.spenditure.object.MainCategory;
import com.spenditure.presentation.category.ViewCategoryActivity;
import com.spenditure.presentation.transaction.CreateTransactionActivity;
import com.spenditure.presentation.transaction.ViewTransactionsActivity;

import java.util.ArrayList;
import java.util.List;


public class ViewReportActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private ViewPager viewPager;
    private Slider_Adapter adapter;
    private ReportManager reportManager;
    private final String[] custom_option = {"Report by average","Report by total","Report by percentage"};
    private CategoryHandler categoryHandler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        reportManager = new ReportManager(true);
        categoryHandler = new CategoryHandler(true);

        handleGeneralReport();
        handleCustomCategoryReport();
        handleCategoriesReport();
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

    private void handleGeneralReport(){
        String spendString = "You've spent $ " + reportManager.getTotalForAllTransactions();
        String makeTransactions = "You've made " + reportManager.countAllTransactions() + " transactions";
        TextView short_text = findViewById(R.id.textview_summary_report_short);
        TextView long_text = findViewById(R.id.textview_summary_report_long);

        if (spendString.length() > makeTransactions.length()){
            long_text.setText(spendString);
            short_text.setText(makeTransactions);
        }else{
            long_text.setText(makeTransactions);
            short_text.setText(spendString);
        }
    }

    private void handleCustomCategoryReport(){
        Spinner spinner = findViewById(R.id.spinner_report);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();
                Toast.makeText(ViewReportActivity.this,item,Toast.LENGTH_SHORT).show();

                String spendMostString = "Mostly spending on ";
                String spendLeastString = "Least spending on ";
                List<MainCategory>categories;

                switch (position){
                    case 2:
                        categories = reportManager.sortByPercent(true);
                        break;
                    case 1:
                        categories = reportManager.sortByTotal(true);
                        break;
                    default:
                        categories = reportManager.sortByAverage(true);
                }

                if(categories.size() == 0){
                    findViewById(R.id.imageView_check_custom).setVisibility(View.INVISIBLE);
                    ((TextView)findViewById(R.id.textview_custom_report_long)).setText("Group transactions to categories to see the report.");
                }else{
                    spendMostString += categories.get(0).getName();
                    spendLeastString += categories.get(categories.size()-1).getName();
                    if( spendMostString.length() > spendLeastString.length()){
                        ((TextView)findViewById(R.id.textview_custom_report_long)).setText(spendMostString);
                        ((TextView)findViewById(R.id.textview_custom_report_short)).setText(spendLeastString);
                    }else{
                        ((TextView)findViewById(R.id.textview_custom_report_long)).setText(spendLeastString);
                        ((TextView)findViewById(R.id.textview_custom_report_short)).setText(spendMostString);
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ArrayAdapter<String>adapter = new ArrayAdapter<>(this, R.layout.custom_snipper_report,custom_option);
        adapter.setDropDownViewResource( android.R.layout.select_dialog_singlechoice);
        spinner.setAdapter(adapter);
    }

    private void handleCategoriesReport(){
        viewPager = findViewById(R.id.viewpager_report);
        adapter = new Slider_Adapter(this,categoryHandler.getAllCategory());
        viewPager.setAdapter(adapter);
    }

}