package com.spenditure.logic;

import java.util.ArrayList;

public interface ISecurityQuestionHandler {


    /*
        getSecurityQuestion
        Given a Security Question ID, retrieves that Security Question (if any).
     */
    String getSecurityQuestion(int securityQuestionID);

    /*
        getAllSecurityQuestions
        Returns an ArrayList with all the Security Questions.
     */
    ArrayList<String> getAllSecurityQuestions();
}
