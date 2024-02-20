package com.spenditure.presentation;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.example.spenditure.R;

public class Slider_Adapter extends PagerAdapter {
    Context context;
    LayoutInflater inflater;

    public Slider_Adapter(Context context){
        this.context = context;
    }
    private String[] list_tittle = {
            "Food",
            "Grocery",
            "Hang out"
    };

    private int[] list_bg_color = {
           R.drawable.gradient_background_dark_blue,
            R.drawable.gradient_background_light_green
    };

    @Override
    public int getCount() {
        return list_tittle.length;
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
        TextView tittle = view.findViewById(R.id.slide_tittle);
        tittle.setText(list_tittle[position]);
        linearLayout.setBackgroundResource(list_bg_color[position % list_bg_color.length]);
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((LinearLayout)object);
    }


}
