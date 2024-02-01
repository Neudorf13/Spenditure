package com.spenditure.object;

public class CT implements ICT {

    Category category;
    Transaction transaction;

    public CT(Category category, Transaction transaction)
    {
        this.category = category;
        this.transaction = transaction;
    }

    public Category getCategory()
    {
        return category;
    }

    public Transaction getTransaction()
    {
        return transaction;
    }
}
