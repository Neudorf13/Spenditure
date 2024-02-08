package com.spenditure.application;

import com.spenditure.database.CategoryPersistence;
import com.spenditure.database.TransactionPersistence;
import com.spenditure.database.stub.CategoryStub;
import com.spenditure.database.stub.TransactionStub;


/**
 * Service
 * @author Bao Ngo
 * @version 06 Feb 2024
 * PURPOSE: provide database services for application
 */

public class Services {
    private static CategoryPersistence categoryPersistence = null;
    private static TransactionPersistence transactionPersistence = null;

    public static CategoryPersistence getCategoryPersistence(boolean getStubDB){
        if(getStubDB){
            if (categoryPersistence == null){
                categoryPersistence = new CategoryStub();
            }
        }else {
            //Hanlde connect to DB

        }
        return categoryPersistence;
    }

    // This method is for the shake of testing with stub database
    public static void restartCategoryDB(boolean getStubDB){
        if(getStubDB){
            categoryPersistence = new CategoryStub();
        }else{
            //Hanlde re-connect to DB
        }
    }

    public static TransactionPersistence getTransactionPersistence(boolean getStubDB){
        if(getStubDB){
            if(transactionPersistence == null) {
                transactionPersistence = new TransactionStub();
            }
        }else{
            //Hanlde connect to DB
        }
        return transactionPersistence;
    }

    // This method is for the shake of testing with stub database
    public static void restartTransactionDB(boolean getStubDB){
        if(getStubDB){
            transactionPersistence = new TransactionStub();
        }else{
            //Hanlde re-connect to DB
        }
    }


}
