package com.spenditure.business;

import org.junit.Before;
import org.junit.Test;
import org.junit.After;
import static org.junit.Assert.*;

import com.spenditure.logic.AccountManager;
import com.spenditure.logic.CategoryHandler;
import com.spenditure.logic.exceptions.InvalidUserInformationException;

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
        AccountManager.cleanup();
        this.accountManager = null;
    }

    @Test
    public void testLogin(){
        int userID = accountManager.login("Me","123");
        assertEquals(1,userID);
        accountManager.logout();

        userID = accountManager.login("He","12345");
        assertEquals(3,userID);
    }

    @Test
    public void testGetUserID(){
        accountManager.login("Me","123");
        int userID = AccountManager.getUserID();
        assertEquals(1,userID);

    }

    @Test
    public void testGetUsername(){
        int userID = accountManager.login("Me","123");
        String username= accountManager.getUserName(userID);
        assertEquals("Me",username);

    }

    @Test
    public void testChangePassword(){
        int userID = accountManager.login("Me","123");
        boolean success = accountManager.changePassword(userID,"123","ok");
        assertTrue(success);
        accountManager.logout();
        userID = accountManager.login("Me","ok");
        assertEquals(1,userID);
    }

    @Test
    public void testRegister(){
        int userID = accountManager.register("new user","testpassword");
        assertEquals(4,userID);
        accountManager.logout();
        userID = accountManager.login("new user","testpassword");
        assertEquals(4,userID);
    }

    @Test
    public void testLogOut(){
        accountManager.login("Me","123");
        accountManager.logout();
        boolean isLogout = false;
        try{
            AccountManager.getUserID();
        }catch (InvalidUserInformationException e){
            isLogout= true;
        }

        assertTrue(isLogout);
    }

    @Test
    public void testChangeUsername(){
        int userID = accountManager.login("Me","123");
        assertEquals(1,userID);
        boolean success = accountManager.changeUsername(userID,"newUsername");
        assertTrue(success);
        assertEquals("newUsername",accountManager.getUserName(userID));
    }
}
