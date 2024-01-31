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

    private ArrayList<Integer> categories;

    public Transaction(int transactionID, String name, DateTime dateTime,
                       String place, double amount, String comments,
                       boolean type, ArrayList<Integer> categories)
    {
        this.transactionID = transactionID;

        this.name = name;

        this.dateTime = dateTime;

        this.place = place;

        this.amount = amount;

        this.comments = comments;

        this.type = type;

        this.categories = categories;
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

        this.categories = new ArrayList<Integer>();
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

    public ArrayList<Integer> getCategories()
    {
        return categories;
    }


    // category stuff
    public void addCategory(int categoryID)
    {
        for(int i = 0; i < categories.size(); i++)
        {
            if(categories.get(i) == categoryID)
            {
                return;
            }
        }

        categories.add(categoryID);
    }

    public void removeCategory(int categoryID)
    {
        Iterator<Integer> itr = categories.iterator();
        while (itr.hasNext())
        {
            int current = itr.next();
            if (current == categoryID)
            {
                itr.remove();
            }
        }

    }

    public boolean containsCategory(int categoryID)
    {
        for(int i = 0; i < categories.size(); i++)
        {
            if(categories.get(i) == categoryID)
            {
                return true;
            }
        }

        return false;
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

    public void setCategories(ArrayList<Integer> categories)
    {
        this.categories = categories;
    }
}
