package com.spenditure.presentation.report;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.example.spenditure.R;
import com.spenditure.logic.ReportManager;
import com.spenditure.object.MainCategory;

import java.util.List;


public class SliderAdapterCatGeneral extends PagerAdapter {
    Context context;
    LayoutInflater inflater;
    private ReportManager reportManager;
    private List<MainCategory> categoryList;
    private int[] list_bg_color = {
      R.drawable.background_light_green,
      R.drawable.background_dark_blue
    };

    public SliderAdapterCatGeneral(Context context, List<MainCategory> categoryList){
        this.context = context;
        this.reportManager = new ReportManager(true);
        this.categoryList = categoryList;
    }


    @Override
    public int getCount() {
        return reportManager.countAllCategories();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return (view == (LinearLayout)object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.report_slide,container,false);
        LinearLayout linearLayout = view.findViewById(R.id.report_slider_layout);
        MainCategory currCategory = categoryList.get(position);

        //Get data from report manager
        String countTransactionsString = reportManager.countTransactionsByCategory(currCategory.getID())+ " transactions";
        String totalTransactionsString= "$"+ handle_decimal(reportManager.getTotalForCategory(currCategory.getID()));
        String averageString= "$"+ handle_decimal(reportManager.getAverageForCategory(currCategory.getID()));
        String percentageString = handle_decimal(reportManager.getPercentForCategory(currCategory.getID())) + "%";

        //query UI components
        TextView tittle = view.findViewById(R.id.slide_tittle);
        TextView countTransactions = view.findViewById(R.id.textview_catReport_transactionsCount);
        TextView totalTransactions = view.findViewById(R.id.textview_catReport_total);
        TextView average = view.findViewById(R.id.textview_catReport_average);
        TextView percentage = view.findViewById(R.id.textview_catReport_percentage);

        //Fill UI component with information
        tittle.setText(currCategory.getName());
        countTransactions.setText(countTransactionsString);
        totalTransactions.setText(totalTransactionsString);
        average.setText(averageString);
        percentage.setText(percentageString);

        linearLayout.setBackgroundResource(list_bg_color[position % list_bg_color.length]);
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((LinearLayout)object);
    }

    private double handle_decimal(double number){
        return Math.ceil(number * 100) / 100;
    }


}
