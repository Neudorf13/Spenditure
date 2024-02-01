package com.spenditure.application;

import com.spenditure.database.AssignmentPersistence;
import com.spenditure.database.CategoryPersistence;
import com.spenditure.database.TransactionPersistence;
import com.spenditure.database.stub.AssignmentStub;
import com.spenditure.database.stub.CategoryStub;
import com.spenditure.database.stub.TransactionStub;

public class Services {
    private static CategoryPersistence categoryPersistence = null;
    private static TransactionPersistence transactionPersistence = null;
    private static AssignmentPersistence assignmentPersistence = null;

    public static CategoryPersistence getCategoryPersistence(boolean inDeveloping){
        if(inDeveloping){
            categoryPersistence = new CategoryStub();
        }else {
            //Hanlde connect to DB

        }
        return categoryPersistence;
    }

    public static TransactionPersistence getTransactionPersistence(boolean inDeveloping){
        if(inDeveloping){
            transactionPersistence = new TransactionStub();
        }else{
            //Hanlde connect to DB
        }
        return transactionPersistence;
    }

    public static AssignmentPersistence getAssignmentPersistence(boolean inDeveloping){
        if(inDeveloping){
            assignmentPersistence = new AssignmentStub();
        }else{
            //Hanlde connect to DB

        }
        return assignmentPersistence;
    }



}
