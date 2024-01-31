package com.spenditure.object;

import java.util.ArrayList;

public interface ITransaction {

    //getters
    int getTransactionID();

    String getName();

    DateTime getDateTime();

    String getPlace();

    double getAmount();

    String getComments();

    boolean getType();

    ArrayList<Integer> getCategories();


    // category stuff
    void addCategory(int categoryID);

    void removeCategory(int categoryID);


    // setters
    void setTransactionID(int transactionID);

    void setName(String name);

    void setDateTime(DateTime dateTime);

    void setPlace(String place);

    void setAmount(double amount);

    void setComments(String comments);

    void setType(boolean type);

    void setCategories(ArrayList<Integer> categories);

}
