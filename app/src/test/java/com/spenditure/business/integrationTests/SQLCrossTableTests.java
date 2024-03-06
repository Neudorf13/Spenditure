package com.spenditure.business.integrationTests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import com.spenditure.application.Services;
import com.spenditure.database.CategoryPersistence;
import com.spenditure.database.TransactionPersistence;
import com.spenditure.database.UserPersistence;
import com.spenditure.utils.TestUtils;

import org.junit.Before;

import java.io.File;
import java.io.IOException;

public class SQLCrossTableTests {

    private File tempDB;

    TransactionPersistence transactionPersistence;
    CategoryPersistence categoryPersistence;
    UserPersistence userPersistence;

    @Before
    public void setup() throws IOException {

        this.tempDB = TestUtils.copyDB();

        this.transactionPersistence = Services.getTransactionPersistence(false);
        this.categoryPersistence = Services.getCategoryPersistence(false);
        this.userPersistence = Services.getUserPersistence(false);

        assertNotNull(this.transactionPersistence);
        assertNotNull(this.categoryPersistence);
        assertNotNull(this.userPersistence);

    }

//    @Test
//    public void addCategoryAndTransaction() {
//
////        userPersistence.printUserTable();
//
////        int register = userPersistence.register(77,"testDummy","123abc","ddd@gmail.com");
//
////        userPersistence.printUserTable();
//
////        assertEquals(77, register);
//
//        categoryPersistence.printCategoryTable();
//
//        MainCategory category = categoryPersistence.addCategory("test",77);
//
//        categoryPersistence.printCategoryTable();
//
//        assertNotNull(category);
//
//        transactionPersistence.printTransactionTable();
//
//        transactionPersistence.addTransaction(new Transaction(2,77,"testTransaction", new DateTime(2020,10,15), "testLand", 17.77, "nothing to say", false, null, 4));
//
//        transactionPersistence.printTransactionTable();
//
//    }


}
