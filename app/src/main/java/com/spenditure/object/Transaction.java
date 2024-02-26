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

    private byte[] image;

    //private MainCategory category;
    private int categoryID;

    public Transaction(int transactionID, int userID, String name, DateTime dateTime,
                       String place, double amount, String comments,
                       boolean type, byte[] image, int categoryID) {
        this.transactionID = transactionID;

        this.userID = userID;

        this.name = name;

        this.dateTime = dateTime;

        this.place = place;

        this.amount = amount;

        this.comments = comments;

        this.type = type;

        this.image = image;

        this.categoryID = categoryID;   //should a Transaction really store a Category?? Shouldn't it just be a category ID?

    }

    public Transaction(int transactionID, String name, DateTime dateTime,
                       String place, double amount, String comments,
                       boolean type, byte[] image) {
        this.transactionID = transactionID;

        this.name = name;

        this.dateTime = dateTime;

        this.place = place;

        this.amount = amount;

        this.comments = comments;

        this.type = type;

        this.image = image;

        //this.category = null;
        this.categoryID = 0;    //represents not having an associated category
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

//    public MainCategory getCategory() {
//        return category;
//    }


    public byte[] getImage() {
        return image;
    }

    public int getCategoryID() {
        return categoryID;
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

//    public void setCategory(MainCategory category) {
//        this.category = category;
//    }


    public void setImage(byte[] image) {
        this.image = image;
    }

    public void setCategoryID(int categoryID) {
        this.categoryID = categoryID;
    }

}
