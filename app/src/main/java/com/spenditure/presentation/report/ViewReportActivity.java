package com.spenditure.presentation.report;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.GridLayout;
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
import com.spenditure.presentation.BottomNavigationHandler;

import java.util.List;


public class ViewReportActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private ViewPager viewPagerCategory;
    private SliderAdapter adapter;
    private ReportManager reportManager;
    private final String[] custom_option = {"Report by average","Report by total","Report by percentage"};
    private final String[] time_base_option = {"Report by year breaking into month","Report by month breaking into weeks"};
    private CategoryHandler categoryHandler;
    private ViewPager viewPagerCustom;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        reportManager = new ReportManager(true);
        categoryHandler = new CategoryHandler(true);

        handleGeneralReport();
        handleCustomCategoryReport();
        handleCategoriesReport();
        handleTimebaseReport();
        handleCustomDateReport();
        navBarHandling();
    }

    private void handleCustomDateReport(){
        Button fromButton = findViewById(R.id.button_report_from);
        Button toButton = findViewById(R.id.button_report_to);
        fromButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog(fromButton);
            }
        });

        toButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog(toButton);
            }
        });
    }

    private void handleTimebaseReport(){
        ShimmerFrameLayout shimmerFrameLayout = findViewById(R.id.shimmer_timebase_report);
        viewPagerCustom = findViewById(R.id.gridlayout_timebase_report);
        Spinner spinner = findViewById(R.id.spinner_timebase_report);


        SliderAdapter adapterCustom= new SliderAdapter(this,categoryHandler.getAllCategory());
        viewPagerCustom.setAdapter(adapterCustom);


        ArrayAdapter<String>adapter = new ArrayAdapter<>(this, R.layout.custom_snipper_report,time_base_option);
        adapter.setDropDownViewResource( android.R.layout.select_dialog_singlechoice);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                viewPagerCustom.setVisibility(View.INVISIBLE);
                shimmerFrameLayout.setVisibility(View.VISIBLE);
                shimmerFrameLayout.startShimmerAnimation();

                Handler handler = new Handler();
                handler.postDelayed(()->{
                    viewPagerCustom.setVisibility(View.VISIBLE);
                    shimmerFrameLayout.stopShimmerAnimation();
                    shimmerFrameLayout.setVisibility(View.INVISIBLE);
                },3000);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    // Handle the bottom navigation bar
    private void navBarHandling(){
//        BottomNavigationView navView = findViewById(R.id.nav_view);
//        navView.setSelectedItemId(R.id.navigation_home);
//
//        navView.setOnItemSelectedListener((item -> {
//            if (item.getItemId() == R.id.navigation_home) {
//                return true;
//            } else if (item.getItemId() == R.id.navigation_create_transaction) {
//                startActivity(new Intent(getApplicationContext(), CreateTransactionActivity.class));
//                return true;
//            } else if (item.getItemId() == R.id.navigation_view_transactions) {
//                startActivity(new Intent(getApplicationContext(), ViewTransactionsActivity.class));
//                return true;
//            }else if(item.getItemId() == R.id.navigation_category){
//                startActivity(new Intent(getApplicationContext(), ViewCategoryActivity.class));
//                return true;
//            }else {
//                return false;
//            }
//        }));

        BottomNavigationView navView = findViewById(R.id.nav_view);
        navView.setSelectedItemId(R.id.navigation_home);
        BottomNavigationHandler navigationHandler = new BottomNavigationHandler();

        navView.setOnItemSelectedListener((item -> {
            if (item.getItemId() == R.id.navigation_home){
                return false;
            }
            Class<? extends AppCompatActivity> newActivity = navigationHandler.select(item.getItemId());
            if(newActivity != null){
                startActivity(new Intent(getApplicationContext(), newActivity));
                return true;
            }
            return false;
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
        viewPagerCategory = findViewById(R.id.viewpager_report);
        adapter = new SliderAdapter(this,categoryHandler.getAllCategory());
        viewPagerCategory.setAdapter(adapter);
    }

    private void openDialog(Button button){
        DatePickerDialog dialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                String displayDay = String.valueOf(dayOfMonth ) + "/"  + String.valueOf(month+ 1) + "/" + String.valueOf(year);
                button.setText(displayDay);
            }
        }, 2024, 0, 15);
        dialog.show();
    }

}