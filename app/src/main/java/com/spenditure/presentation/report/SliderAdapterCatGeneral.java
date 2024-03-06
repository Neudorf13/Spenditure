package com.spenditure.presentation.report;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.spenditure.R;
import com.spenditure.logic.CategoryHandler;
import com.spenditure.logic.GeneralReportHandler;
import com.spenditure.logic.ReportManager;
import com.spenditure.logic.UserManager;
import com.spenditure.object.MainCategory;
import com.spenditure.presentation.UIUtility;

import java.util.List;

/**
 * Slider for Report on each category
 * @author Bao Ngo
 * @version 04 Mar 2024
 */

public class SliderAdapterCatGeneral extends PagerAdapter {
    private Context context;
    private LayoutInflater inflater;
    private ReportManager reportManager;
    private GeneralReportHandler generalReportHandler;
    private CategoryHandler categoryHandler;
    private List<MainCategory> categoryList;
    private int[] list_bg_color = { //Default background color for each slider
      R.drawable.background_light_green,
      R.drawable.background_dark_blue
    };

    public SliderAdapterCatGeneral(Context context, List<MainCategory> categoryList){
        this.context = context;
        this.reportManager = new ReportManager(true);
        this.generalReportHandler = new GeneralReportHandler(true);
        this.categoryHandler = new CategoryHandler(true);
        this.categoryList = categoryList;
    }


    @Override
    public int getCount() {
        return categoryHandler.getAllCategory(UserManager.getUserID()).size();
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
        String countTransactionsString =  UIUtility.cleanTransactionNumberString(generalReportHandler.numTransactions(UserManager.getUserID(), currCategory.getCategoryID()));
        String totalTransactionsString= UIUtility.cleanTotalString(generalReportHandler.totalSpending(UserManager.getUserID(), currCategory.getCategoryID()));
        String averageString=  UIUtility.cleanAverageString(generalReportHandler.averageSpending(UserManager.getUserID(), currCategory.getCategoryID()));
        String percentageString = UIUtility.cleanPercentageString(generalReportHandler.percentage(UserManager.getUserID(), currCategory.getCategoryID()));

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

}
