package com.spenditure.object;

import java.util.ArrayList;
import java.util.Iterator;

public class Transaction implements ITransaction{

    private int transactionID;

    private String name;

    private DateTime dateTime;

    private String place;

    private double amount;

    private String comments;

    private boolean type;

    private Category category;

    public Transaction(int transactionID, String name, DateTime dateTime,
                       String place, double amount, String comments,
                       boolean type, Category category) {
        this.transactionID = transactionID;

        this.name = name;

        this.dateTime = dateTime;

        this.place = place;

        this.amount = amount;

        this.comments = comments;

        this.type = type;

        this.category = category;

    }

    public Transaction(int transactionID, String name, DateTime dateTime,
                       String place, double amount, String comments,
                       boolean type) {
        this.transactionID = transactionID;

        this.name = name;

        this.dateTime = dateTime;

        this.place = place;

        this.amount = amount;

        this.comments = comments;

        this.type = type;

        this.category = null;

    }

    //getters
    public int getTransactionID()
    {
        return transactionID;
    }

    public String getName()
    {
        return name;
    }

    public DateTime getDateTime()
    {
        return dateTime;
    }

    public String getPlace()
    {
        return place;
    }

    public double getAmount()
    {
        return amount;
    }

    public String getComments()
    {
        return comments;
    }

    public boolean getType()
    {
        return type;
    }

    public Category getCategory() {
        return category;
    }



    // setters
    public void setTransactionID(int transactionID)
    {
        this.transactionID = transactionID;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public void setDateTime(DateTime dateTime)
    {
        this.dateTime = dateTime;
    }

    public void setPlace(String place)
    {
        this.place = place;
    }

    public void setAmount(double amount)
    {
        this.amount = amount;
    }

    public void setComments(String comments)
    {
        this.comments = comments;
    }

    public void setType(boolean type)
    {
        this.type = type;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

}
