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




}
