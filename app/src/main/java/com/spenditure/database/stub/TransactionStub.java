package com.spenditure.database.stub;

import com.spenditure.database.TransactionPersistence;
import com.spenditure.object.Category;
import com.spenditure.object.DateNewestFirstComparator;
import com.spenditure.object.DateOldestFirstComparator;
import com.spenditure.object.DateTime;
import com.spenditure.object.Transaction;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class TransactionStub implements TransactionPersistence {

    private ArrayList<Transaction> transactionList;
    private static int currentID = 1;

    public TransactionStub()
    {
        transactionList = new ArrayList<Transaction>();
        transactionList.add(new Transaction(TransactionStub.generateUniqueID(), "Morning Dons", new DateTime(1,1,1,1,1),
                "Mcdonalds",5.99, "was luke warm today, 2/10", true));
        transactionList.add(new Transaction(TransactionStub.generateUniqueID(), "Star Wars Rebels merch", new DateTime(2024,2,1,16,59),
                "Toys R Us",500.95, "Sabine looking kinda nice O_o", true));
        transactionList.add(new Transaction(TransactionStub.generateUniqueID(), "Shopping spree at the mall", new DateTime(2023, 5, 20, 15, 30),
                "Mall", 250.50, "Bought clothes and accessories", true));
        transactionList.add(new Transaction(TransactionStub.generateUniqueID(), "Dinner at a fancy restaurant", new DateTime(2023, 7, 8, 19, 0),
                "Gourmet Restaurant", 150.75, "Celebrated anniversary", true));
        transactionList.add(new Transaction(TransactionStub.generateUniqueID(), "Hotel accommodation for business trip", new DateTime(2023, 8, 15, 12, 0),
                "Grand Hotel", 300.0, "Stayed for 3 nights", true));
        transactionList.add(new Transaction(TransactionStub.generateUniqueID(), "Grocery shopping for the week", new DateTime(2023, 8, 20, 10, 0),
                "Supermarket", 80.25, "Bought fruits, vegetables, and dairy products", true));
        transactionList.add(new Transaction(TransactionStub.generateUniqueID(), "Utility bill payment", new DateTime(2023, 8, 25, 9, 0),
                "Utility Company", 120.0, "Paid electricity and water bills", true));
        transactionList.add(new Transaction(TransactionStub.generateUniqueID(), "Concert ticket purchase", new DateTime(2023, 9, 5, 18, 30),
                "Concert Hall", 50.0, "Attended the concert of favorite band", true));
        transactionList.add(new Transaction(TransactionStub.generateUniqueID(), "Car maintenance service", new DateTime(2023, 9, 10, 8, 0),
                "Auto Service Center", 200.0, "Performed routine maintenance and oil change", true));
        transactionList.add(new Transaction(TransactionStub.generateUniqueID(), "Online shopping for household items", new DateTime(2023, 9, 15, 16, 0),
                "Online Store", 90.50, "Bought cleaning supplies and kitchenware", true));
        transactionList.add(new Transaction(TransactionStub.generateUniqueID(), "Movie tickets for family outing", new DateTime(2023, 9, 20, 14, 0),
                "Cinema", 60.0, "Watched latest blockbuster movie with family", true));
        transactionList.add(new Transaction(TransactionStub.generateUniqueID(), "Gym membership subscription", new DateTime(2023, 9, 25, 17, 0),
                "Fitness Center", 75.0, "Monthly subscription for gym access", true));
        transactionList.add(new Transaction(TransactionStub.generateUniqueID(), "Restaurant bill for friend's birthday dinner", new DateTime(2023, 9, 30, 20, 0),
                "Fine Dining Restaurant", 200.0, "Celebrated friend's birthday with a fancy dinner", true));
        transactionList.add(new Transaction(TransactionStub.generateUniqueID(), "Online course enrollment fee", new DateTime(2023, 10, 5, 11, 0),
                "Online Education Platform", 150.0, "Enrolled in a programming course", true));

    }

    public ArrayList<Transaction> getAllTransactions()
    {
        return transactionList;
    }

    public boolean addTransaction(Transaction newTransaction)
    {
        for(int i = 0; i < transactionList.size(); i++)
        {
            if(transactionList.get(i).getTransactionID() == newTransaction.getTransactionID())
            {
                return false;
            }
        }

        transactionList.add(newTransaction);
        return true;
    }

    public boolean modifyTransaction(Transaction targetTransaction)
    {
        for(int i = 0; i < transactionList.size(); i++)
        {
            if(transactionList.get(i).getTransactionID() == targetTransaction.getTransactionID())
            {
                transactionList.set(i, targetTransaction);
                return true;
            }
        }

        return false;
    }

    public boolean deleteTransaction(Transaction targetTransaction)
    {
        boolean foundTransaction = false;

        Iterator<Transaction> itr = transactionList.iterator();
        while (itr.hasNext())
        {
            Transaction current = itr.next();
            if (current.getTransactionID() == targetTransaction.getTransactionID())
            {
                foundTransaction = true;
                itr.remove();
            }
        }

        return foundTransaction;
    }

    public Transaction getTransactionByID(int id)
    {
        for(Transaction transaction : this.transactionList)
        {
            if(transaction.getTransactionID() == id){
                return transaction;
            }
        }

        return null;
    }

    public static int generateUniqueID()
    {
        return currentID++;
    }

    // sorting
    public ArrayList<Transaction> sortByDateNewestFirst()
    {
        Collections.sort(transactionList, new DateNewestFirstComparator());
        return transactionList;
    }

    public ArrayList<Transaction> sortByDateOldestFirst()
    {
        Collections.sort(transactionList, new DateOldestFirstComparator());
        return transactionList;
    }



}
