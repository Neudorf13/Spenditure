package com.spenditure.object;

public class Transaction implements ITransaction{

    private int transactionID;

    private int userID;

    private String name;

    private DateTime dateTime;

    private String place;

    private double amount;

    private String comments;

    private boolean type;

    private MainCategory category;

    public Transaction(int transactionID, int userID, String name, DateTime dateTime,
                       String place, double amount, String comments,
                       boolean type, MainCategory category) {
        this.transactionID = transactionID;

        this.userID = userID;

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

    public int getUserID() { return userID;}

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

    public MainCategory getCategory() {
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

    public void setCategory(MainCategory category) {
        this.category = category;
    }

}
