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
import com.spenditure.object.ICategory;
import com.spenditure.object.IReport;
import com.spenditure.object.MainCategory;

import java.util.List;

public class SliderAdapterTimeBase extends PagerAdapter {
    Context context;
    LayoutInflater inflater;
   private List<IReport> reportList;
    private int[] list_bg_color = {
            R.drawable.background_light_green,
            R.drawable.background_dark_blue
    };
    private String mode;

    public SliderAdapterTimeBase(Context context, List<IReport> reportList, String mode){
        this.context = context;
        this.reportList = reportList;
        this.mode = mode;
    }


    @Override
    public int getCount() {
        return reportList.size();
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
        IReport currReport = reportList.get(position);

        //Get data from report manager
        String countTransactionsString = currReport.getNumTrans()+ " transactions";
        String totalTransactionsString= "$"+ handle_decimal(currReport.getTotal());
        String averageString= "$"+ handle_decimal(currReport.getAvgTransSize());
        String percentageString = handle_decimal(currReport.getPercentage()) + "%";
        String title = mode + " " + (position+1);
//
        //query UI components
        TextView tittle = view.findViewById(R.id.slide_tittle);
        TextView countTransactions = view.findViewById(R.id.textview_catReport_transactionsCount);
        TextView totalTransactions = view.findViewById(R.id.textview_catReport_total);
        TextView average = view.findViewById(R.id.textview_catReport_average);
        TextView percentage = view.findViewById(R.id.textview_catReport_percentage);
//
//        //Fill UI component with information
        tittle.setText(title );
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
