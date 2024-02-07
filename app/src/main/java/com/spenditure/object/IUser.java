package com.spenditure.object;

/**
 * User interface
 * @author Bao Ngo
 * @version 06 Feb 2024
 */
public interface IUser {
    public String getName();
    public String getPhone();
    public String getEmail();
    public String getUsername();

    public void updateName(String newName);
    public void updateUserName(String newUserName);
    public void updateEmail(String newEmail);
    public void updatePhone(String newPhone);
}
