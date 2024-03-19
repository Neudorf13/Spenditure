package com.spenditure.logic;

import com.spenditure.application.Services;
import com.spenditure.database.SecurityQuestionPersistence;

import java.util.ArrayList;

public class SecurityQuestionHandler implements ISecurityQuestionHandler {

    private SecurityQuestionPersistence dataAccessSecurityQuestion;

    public SecurityQuestionHandler(boolean getStubDB) {

        if(dataAccessSecurityQuestion == null)
            dataAccessSecurityQuestion = Services.getSecurityQuestionPersistence(getStubDB);

    }

    public void cleanup(boolean getStubDB) {

        Services.restartSecurityQuestionDB(getStubDB);

        dataAccessSecurityQuestion = Services.getSecurityQuestionPersistence(getStubDB);

    }

    /*

        getSecurityQuestion

        Given a Security Question ID, retrieves that Security Question (if any).

     */
    @Override
    public String getSecurityQuestion(int securityQuestionID) {

        assert( securityQuestionID > 0 );

        return dataAccessSecurityQuestion.getSecurityQuestion(securityQuestionID);

    }

    /*

        getAllSecurityQuestions

        Returns an ArrayList with all the Security Questions.

     */
    @Override
    public ArrayList<String> getAllSecurityQuestions() {

        return dataAccessSecurityQuestion.getAllSecurityQuestions();

    }

}
