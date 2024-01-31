package com.spenditure.database.stub;

import com.spenditure.object.Category;
import com.spenditure.object.DateNewestFirstComparator;
import com.spenditure.object.DateOldestFirstComparator;
import com.spenditure.object.DateTime;
import com.spenditure.object.Transaction;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class TransactionStub {

    private ArrayList<Transaction> transactionList;
    private static int currentID = 1;

    public TransactionStub()
    {
        transactionList = new ArrayList<Transaction>();
        transactionList.add(new Transaction(TransactionStub.generateUniqueID(), "Morning Dons", new DateTime(1,1,1,1,1),
                "Mcdonalds",5.99, "was luke warm today, 2/10", true));
        transactionList.add(new Transaction(TransactionStub.generateUniqueID(), "Star Wars Rebels merch", new DateTime(2024,2,1,16,59),
                "Toys R Us",500.95, "Sabine looking kinda nice O_o", true));
        transactionList.add(new Transaction(TransactionStub.generateUniqueID(), "Hush money payment for porn star", new DateTime(2006,2,13,0,0),
                "Lawyer's office",130000.00, "If the police ask I had no knowledge of this payment", true));
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

    public ArrayList<Transaction> filterByCategory(ArrayList<Category> categories)
    {
        ArrayList<Transaction> filteredTransactions = new ArrayList<Transaction>();

        // loop through all transaction stored in database
        for(int i = 0; i < transactionList.size(); i++)
        {
            // checks whether at least
            boolean atLeastOneLabel = false;

            // compare the given category to the categories of the transaction being looked at
            for(int m = 0; m < categories.size(); m++)
            {
                // if the current transaction contains at least one of the give category labels set variable to true
                if(transactionList.get(i).containsCategory(m))
                {
                    atLeastOneLabel = true;
                }

            }

            // if boolean true (meaning the current transaction contains at least one of the target categories)
            // then add it to the new array list
            if(atLeastOneLabel)
            {
                filteredTransactions.add(transactionList.get(i));
            }

        }

        return filteredTransactions;
    }

    // NOT IN THE INTERFACE
    // only used for the stub database to remove a deleted category from every existing transactions
    public void removeCategoryFromAllTransactions(int categoryID)
    {
        for(int i = 0; i < transactionList.size(); i++)
        {
            // don't bother checking whether its in the transaction, transaction object handles that
            transactionList.get(i).removeCategory(categoryID);
        }
    }
}
