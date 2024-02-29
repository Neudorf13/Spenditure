package com.spenditure.database.stub;

import com.spenditure.database.AccountPersistence;

public class AccountStub implements AccountPersistence {
    @Override
    public int login(String username, String password) {
        //TODO
        return 0;
    }

    @Override
    public int getUserName(int userID) {
        //TODO
        return 0;
    }

    @Override
    public boolean changePassword(int userID, String oldPassword, String newPassword) {
        //TODO
        return false;
    }

    @Override
    public boolean changeUsername(int userID, String newUsername) {
        //TODO
        return false;
    }
}
