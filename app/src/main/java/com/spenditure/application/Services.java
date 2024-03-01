package com.spenditure.application;

import com.spenditure.database.AccountPersistence;
import com.spenditure.database.CategoryPersistence;
import com.spenditure.database.TransactionPersistence;
import com.spenditure.database.stub.AccountStub;
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
    private static AccountPersistence accountPersistence = null;
    public static AccountPersistence getAccountPersistence(boolean getStubDB){
        if(getStubDB){
            if (accountPersistence == null){   //Apply Singleton
                accountPersistence = new AccountStub();
            }
        }else {
            //Hanlde connect to DB

        }
        return accountPersistence;
    }

    public static CategoryPersistence getCategoryPersistence(boolean getStubDB){
        if(getStubDB){
            if (categoryPersistence == null){   //Apply Singleton
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
            if(transactionPersistence == null) { //Apply Singleton
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

    // This method is for the shake of testing with stub database
    public static void restartAccountDB(boolean getStubDB){
        if(getStubDB){
            accountPersistence = new AccountStub();
        }else{
            //Hanlde re-connect to DB
        }
    }


}
