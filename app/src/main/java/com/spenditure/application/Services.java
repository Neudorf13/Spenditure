package com.spenditure.application;

import com.spenditure.database.CategoryPersistence;
import com.spenditure.database.stub.CategoryStub;

public class Services {
    private static CategoryPersistence categoryPersistence = null;

    public static CategoryPersistence getCategoryPersistence(boolean inDeveloping){
        if(inDeveloping){
            categoryPersistence = new CategoryStub();
        }else {
            //Hanlde connect to DB

        }
        return categoryPersistence;
    }

}
