package com.spenditure.application;

import com.spenditure.database.CategoryPersistence;
import com.spenditure.database.TransactionPersistence;
import com.spenditure.database.UserPersistence;
import com.spenditure.database.hsqldb.CategorySQL;
import com.spenditure.database.hsqldb.TransactionSQL;
import com.spenditure.database.hsqldb.UserSQL;
import com.spenditure.database.stub.CategoryStub;
import com.spenditure.database.stub.TransactionStub;
import com.spenditure.database.stub.UserStub;
import com.spenditure.presentation.report.ViewReportActivity;


/**
 * Service
 * @author Bao Ngo
 * @version 06 Feb 2024
 * PURPOSE: provide database services for application
 */

public class Services {

    public static final boolean DEVELOPING_STATUS = false;
    private static CategoryPersistence categoryPersistence = null;
    private static TransactionPersistence transactionPersistence = null;
    private static UserPersistence userPersistence = null;



    public static synchronized CategoryPersistence getCategoryPersistence(boolean inDeveloping){
        if(categoryPersistence == null) {
            if(inDeveloping){
                categoryPersistence = new CategoryStub();
            }else {
                //Handle connect to DB
                categoryPersistence = new CategorySQL(ViewReportActivity.getDBPathName());
            }
        }

        return categoryPersistence;
    }

    public static synchronized TransactionPersistence getTransactionPersistence(boolean inDeveloping){
        if(transactionPersistence == null) {
            if(inDeveloping){
                transactionPersistence = new TransactionStub();
            }else{
                //Handle connect to DB
                transactionPersistence = new TransactionSQL(ViewReportActivity.getDBPathName());
            }
        }

        return transactionPersistence;
    }

    public static synchronized UserPersistence getUserPersistence(boolean inDeveloping){
        if(userPersistence == null) {
            if(inDeveloping){
                userPersistence = new UserStub();
            }else{
                //Handle connect to DB
                userPersistence = new UserSQL(ViewReportActivity.getDBPathName());
            }
        }

        return userPersistence;
    }

    // This method is for the shake of testing with stub database
    public static void restartCategoryDB(boolean getStubDB){
        if(getStubDB){
            categoryPersistence = new CategoryStub();
        }else{
            //Handle re-connect to DB
            categoryPersistence = new CategorySQL(ViewReportActivity.getDBPathName());
        }
    }

    // This method is for the shake of testing with stub database
    public static void restartTransactionDB(boolean getStubDB){
        if(getStubDB){
            transactionPersistence = new TransactionStub();
        }else{
            //Hanlde re-connect to DB
            transactionPersistence = new TransactionSQL(ViewReportActivity.getDBPathName());
        }
    }

    // This method is for the shake of testing with stub database
    public static void restartAccountDB(boolean getStubDB){
        if(getStubDB){
            userPersistence = new UserStub();
        }else{
            //Hanlde re-connect to DB
            userPersistence = new UserSQL(ViewReportActivity.getDBPathName());
        }
    }




}
