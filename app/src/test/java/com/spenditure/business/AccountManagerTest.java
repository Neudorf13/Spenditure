package com.spenditure.business;

import org.junit.Before;
import org.junit.Test;
import org.junit.After;
import static org.junit.Assert.*;

import com.spenditure.logic.AccountManager;
import com.spenditure.logic.CategoryHandler;

/**
 * Category handler unit tests
 * @author Bao Ngo
 * @version 01 March 2024
 */
public class AccountManagerTest {

    private AccountManager accountManager;
    @Before
    public void setup(){
        this.accountManager = new AccountManager(true);
    }
    @After
    public void tearDown(){
        this.accountManager = null;
    }

    @Test
    public void testLogin(){
        int expectedSize = 3;

    }

    @Test
    public void testLogin(){
        int expectedSize = 3;

    }
}
