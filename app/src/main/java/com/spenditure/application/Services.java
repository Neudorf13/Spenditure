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


}
