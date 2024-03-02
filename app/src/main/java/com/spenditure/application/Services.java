package com.spenditure.application;

import com.spenditure.database.CategoryPersistence;
import com.spenditure.database.TransactionPersistence;
import com.spenditure.database.UserPersistence;
import com.spenditure.database.hsqldb.CategorySQL;
import com.spenditure.database.hsqldb.TransactionSQL;
import com.spenditure.database.hsqldb.UserSQL;
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
    private static UserPersistence userPersistence = null;

    public static synchronized CategoryPersistence getCategoryPersistence(boolean inDeveloping){
        if(categoryPersistence == null) {
            if(inDeveloping){
                categoryPersistence = new CategoryStub();
            }else {
                //Hanlde connect to DB
                categoryPersistence = new CategorySQL(MainActivity.getDBPathName());
            }
        }

        return categoryPersistence;
    }

    public static synchronized TransactionPersistence getTransactionPersistence(boolean inDeveloping){
        if(transactionPersistence == null) {
            if(inDeveloping){
                transactionPersistence = new TransactionStub();
            }else{
                //Hanlde connect to DB
                transactionPersistence = new TransactionSQL(MainActivity.getDBPathName());
            }
        }

        return transactionPersistence;
    }

    public static synchronized UserPersistence getUserPersistence(boolean inDeveloping){
        if(userPersistence == null) {
            if(inDeveloping){
                //userPersistence = new TransactionStub();
            }else{
                //Hanlde connect to DB
                userPersistence = new UserSQL(MainActivity.getDBPathName());
            }
        }

        return userPersistence;
    }


}
