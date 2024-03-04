package com.spenditure.business.integrationTests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import com.spenditure.logic.TransactionHandler;
import com.spenditure.logic.UserManager;
import com.spenditure.logic.exceptions.InvalidUserInformationException;
import com.spenditure.utils.TestUtils;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

public class AccountManagerIT {

    private UserManager accountManager;
    private File tempDB;

    @Before
    public void setup() throws IOException {
        this.tempDB = TestUtils.copyDB();
        this.accountManager = new UserManager(false);
    }
    @After
    public void tearDown(){
        UserManager.cleanup();
        this.accountManager = null;
        this.tempDB = null;
    }

    @Test
    public void testLogin(){
        int userID = accountManager.login("Me","123");
        assertEquals(1,userID);
        accountManager.logout();

        userID = accountManager.login("He","12345");
        assertEquals(3,userID);
    }
//
    @Test
    public void testGetUserID(){
        accountManager.login("Me","123");
        int userID = UserManager.getUserID();
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
        int userID = accountManager.login("You","1234");
        boolean success = accountManager.changePassword(userID,"1234","ok");
        assertTrue(success);
        accountManager.logout();
        userID = accountManager.login("You","ok");
        assertEquals(2,userID);
    }

    @Test
    public void testRegister(){
        int userID = accountManager.register("new user","testpassword");
        assertEquals(4,userID);
        accountManager.logout();
        userID = accountManager.login("new user","testpassword");
        assertEquals(4,userID);
    }
//
    @Test
    public void testLogOut(){
        accountManager.login("Me","123");
        accountManager.logout();
        boolean isLogout = false;
        try{
            UserManager.getUserID();
        }catch (InvalidUserInformationException e){
            isLogout= true;
        }

        assertTrue(isLogout);
    }

    @Test
    public void testChangeUsername(){
        int userID = accountManager.login("TestingUser1","12345");
        assertEquals(4,userID);
        boolean success = accountManager.changeUsername(userID,"newUsername");
        assertTrue(success);
        assertEquals("newUsername",accountManager.getUserName(userID));
    }
}
