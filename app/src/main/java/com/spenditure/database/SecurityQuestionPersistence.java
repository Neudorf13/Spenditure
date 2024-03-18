package com.spenditure.database;

import com.spenditure.object.SecurityQuestion;

import java.util.ArrayList;

public interface SecurityQuestionPersistence {

    String getSecurityQuestion(int sid);
    ArrayList<String> getAllSecurityQuestions();
}
