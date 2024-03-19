package com.spenditure.presentation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.spenditure.R;

import java.util.List;

public class CustomSecurityQuestionSpinnerActivity extends BaseAdapter {
    // Instance Variables
    private Context context;
    private List<String> questions;
    private LayoutInflater inflator;

    public CustomSecurityQuestionSpinnerActivity(List<String> questions, Context context){
        this.context = context;
        this.questions = questions;
        inflator = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return questions.size();
    }

    @Override
    public String getItem(int i) {
        return questions.get(i);
    }

    @Override
    public long getItemId(int i) {
        return questions.get(i).getCategoryID();
    }   // TODO: fix this?

    public int getPosition(String question) {
        return questions.indexOf(question);
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup){
        view =inflator.inflate(R.layout.activity_custom_security_question_spinner, null);

        TextView question = (TextView) view.findViewById(R.id.textview_security_question);
        question.setText(questions.get(i));

        return view;
    }
}