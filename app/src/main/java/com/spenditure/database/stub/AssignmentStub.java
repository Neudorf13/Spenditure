package com.spenditure.database.stub;

import com.spenditure.database.AssignmentPersistence;
import com.spenditure.database.CategoryPersistence;
import com.spenditure.database.TransactionPersistence;
import com.spenditure.logic.CategoryHandler;
import com.spenditure.object.CT;
import com.spenditure.object.Category;
import com.spenditure.object.Transaction;

import java.util.ArrayList;
import java.util.Iterator;

public class AssignmentStub implements AssignmentPersistence {

    private ArrayList<CT> assignmentsList;

    public AssignmentStub() {this.assignmentsList = new ArrayList<CT>();}

    public ArrayList<CT> getAllAssignments()
    {
        return assignmentsList;
    }

    public boolean addAssignment(CT newAssignment)
    {
        for(CT ct : this.assignmentsList){
            if(ct.getTransaction().getTransactionID() == newAssignment.getTransaction().getTransactionID()
                && ct.getCategory().getID() == newAssignment.getCategory().getID())
            {
                return false;
            }
        }
        assignmentsList.add(newAssignment);
        return true;
    }

    public boolean deleteAssignmentByCategory(int categoryID)
    {
        boolean foundTarget = false;

        Iterator<CT> itr = assignmentsList.iterator();
        while (itr.hasNext())
        {
            CT current = itr.next();
            if (current.getCategory().getID() == categoryID)
            {
                foundTarget = true;
                itr.remove();
            }
        }

        return foundTarget;
    }

    public boolean deleteAssignmentByTransaction(int transactionID)
    {
        boolean foundTarget = false;

        Iterator<CT> itr = assignmentsList.iterator();
        while (itr.hasNext())
        {
            CT current = itr.next();
            if (current.getTransaction().getTransactionID() == transactionID)
            {
                foundTarget = true;
                itr.remove();
            }
        }

        return foundTarget;
    }

    public boolean deleteAssignment(int categoryID, int transactionID)
    {
        boolean foundTarget = false;

        Iterator<CT> itr = assignmentsList.iterator();
        while (itr.hasNext())
        {
            CT current = itr.next();
            if (current.getTransaction().getTransactionID() == transactionID
                && current.getCategory().getID() == categoryID)
            {
                foundTarget = true;
                itr.remove();
            }
        }

        return foundTarget;
    }

    public ArrayList<CT> getAssignmentByTransactionID(int transactionID)
    {
        ArrayList<CT> allAssignmentsWithID = new ArrayList<CT>();

        for(CT ct : this.assignmentsList){
            if(ct.getTransaction().getTransactionID() == transactionID){
                allAssignmentsWithID.add(ct);
            }
        }

        return allAssignmentsWithID;
    }

    public ArrayList<CT> getAssignmentByCategoryID(int categoryID)
    {
        ArrayList<CT> allAssignmentsWithID = new ArrayList<CT>();

        for(CT ct : this.assignmentsList){
            if(ct.getCategory().getID() == categoryID){
                allAssignmentsWithID.add(ct);
            }
        }

        return allAssignmentsWithID;
    }

    public CT getAssignment(int categoryID, int transactionID)
    {
        for(CT ct : this.assignmentsList){
            if(ct.getTransaction().getTransactionID() == transactionID && ct.getCategory().getID() == categoryID){
                return ct;
            }
        }

        return null;

    }

    public ArrayList<Transaction> filterByCategories(ArrayList<Category> searchedCategories)
    {
        ArrayList<Transaction> allAssignmentsWithCategory = new ArrayList<Transaction>();

        for(CT ct : this.assignmentsList){

             boolean addToList = false;

             for(Category currentCategory: searchedCategories)
             {
                 if(ct.getCategory().getID() == currentCategory.getID())
                 {
                     addToList = true;
                 }
             }

             if(addToList)
             {
                 boolean addToListIsDuplicate = false;

                 for(Transaction currentTransaction: allAssignmentsWithCategory)
                 {
                     if(ct.getTransaction().getTransactionID() == currentTransaction.getTransactionID())
                     {
                         addToListIsDuplicate = true;
                     }
                 }

                 if(!addToListIsDuplicate)
                 {
                     allAssignmentsWithCategory.add(ct.getTransaction());
                 }


             }
        }

        return allAssignmentsWithCategory;
    }
}
