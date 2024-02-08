/**
 * ITransaction.java
 *
 * COMP3350 SECTION A02
 *
 * @author Jared Rost,
 * @date Feb 7, 2024
 *
 * PURPOSE:
 *  Showcase what methods Transaction object should implement
 *
 **/

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



    // setters
    void setTransactionID(int transactionID);

    void setName(String name);

    void setDateTime(DateTime dateTime);

    void setPlace(String place);

    void setAmount(double amount);

    void setComments(String comments);

    void setType(boolean type);


}
