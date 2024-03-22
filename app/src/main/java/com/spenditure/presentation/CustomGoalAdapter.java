package com.spenditure.presentation;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.spenditure.R;
import com.spenditure.application.Services;
import com.spenditure.logic.CategoryHandler;
import com.spenditure.logic.GoalHandler;
import com.spenditure.logic.ICategoryHandler;
import com.spenditure.object.Goal;
import com.spenditure.object.MainCategory;

import java.util.List;

public class CustomGoalAdapter extends BaseAdapter {

    // Instance Variables
    private Context context;
    private List<Goal> goals;
    private LayoutInflater inflator;
    private ICategoryHandler categoryHandler;

    // Constructor
    public CustomGoalAdapter(List<Goal> goals, Context context){
        this.context = context;
        this.goals = goals;
        inflator = LayoutInflater.from(context);

        categoryHandler = new CategoryHandler(Services.DEVELOPING_STATUS);
    }

    @Override
    public int getCount() {
        return goals.size();
    }

    @Override
    public Goal getItem(int i) {
        return goals.get(i);
    }

    @Override
    public long getItemId(int i) {
        return goals.get(i).getGoalID();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup){
        view =inflator.inflate(R.layout.activity_goal_list_view, null);

        TextView name = view.findViewById(R.id.textview_goal_name);
        TextView amount = view.findViewById(R.id.textview_amount);
        TextView category = view.findViewById(R.id.textview_category);
        TextView completionDate = view.findViewById(R.id.textview_completion_date);

        name.setText(goals.get(i).getGoalName());
        amount.setText("$" + goals.get(i).getSpendingGoal());
        MainCategory cat = categoryHandler.getCategoryByID(goals.get(i).getCategoryID());
        category.setText(cat.getName());
        completionDate.setText("Complete by: " + goals.get(i).getToBeCompletedBy().toString());

        return view;
    }

}
