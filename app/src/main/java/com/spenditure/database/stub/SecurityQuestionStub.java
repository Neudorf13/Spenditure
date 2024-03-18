package com.spenditure.database.stub;

import com.spenditure.database.SecurityQuestionPersistence;
import com.spenditure.object.Goal;
import com.spenditure.object.SecurityQuestion;

import java.util.ArrayList;

public class SecurityQuestionStub implements SecurityQuestionPersistence {

    private ArrayList<SecurityQuestion> securityQuestionList;

    public SecurityQuestionStub() {
        this.securityQuestionList = new ArrayList<>();
        securityQuestionList.add(new SecurityQuestion(1,"name of childhood pet"));
        securityQuestionList.add(new SecurityQuestion(2,"fathers bachelors name"));
        securityQuestionList.add(new SecurityQuestion(3,"favorite UofM comp class course number"));
    }

    @Override
    public String getSecurityQuestion(int sid) {
        for (SecurityQuestion question : securityQuestionList) {
            if(question.getSid() == sid) {
                return question.getSecurityQuestion();
            }
        }
        return null;
    }

    @Override
    public ArrayList<String> getAllSecurityQuestions() {
        ArrayList<String> questions = new ArrayList<>();
        for (SecurityQuestion question : securityQuestionList) {
            questions.add(question.getSecurityQuestion());
        }
        return questions;
    }


}
