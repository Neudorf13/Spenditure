package com.spenditure.business;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import com.spenditure.logic.TransactionHandler;
import com.spenditure.database.stub.TransactionStub;
import com.spenditure.object.DateTime;
import com.spenditure.object.Transaction;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

public class TransactionHandlerTest {

    private TransactionHandler transactionHandler;
    private static final int EXPECTED_SIZE = 14;

    @Before
    public void setup() {
        TransactionStub.cleanup();
        this.transactionHandler = new TransactionHandler(true);
    }

    @After
    public void tearDown() { this.transactionHandler = null; }

    @Test
    public void testTransactionSet() {

        assertEquals(transactionHandler.getAllTransactions().size(), EXPECTED_SIZE);

        assertEquals("Morning Dons", transactionHandler.getTransactionByID(1).getName());
        assertEquals("Star Wars Rebels merch", transactionHandler.getTransactionByID(2).getName());
        assertEquals("Shopping spree at the mall", transactionHandler.getTransactionByID(3).getName());
        assertEquals("Dinner at a fancy restaurant", transactionHandler.getTransactionByID(4).getName());
        assertEquals("Hotel accommodation for business trip", transactionHandler.getTransactionByID(5).getName());
        assertEquals("Grocery shopping for the week", transactionHandler.getTransactionByID(6).getName());
        assertEquals("Utility bill payment", transactionHandler.getTransactionByID(7).getName());
        assertEquals("Concert ticket purchase", transactionHandler.getTransactionByID(8).getName());
        assertEquals("Car maintenance service", transactionHandler.getTransactionByID(9).getName());
        assertEquals("Online shopping for household items", transactionHandler.getTransactionByID(10).getName());
        assertEquals("Movie tickets for family outing", transactionHandler.getTransactionByID(11).getName());
        assertEquals("Gym membership subscription", transactionHandler.getTransactionByID(12).getName());
        assertEquals("Restaurant bill for friend's birthday dinner", transactionHandler.getTransactionByID(13).getName());
        assertEquals("Online course enrollment fee", transactionHandler.getTransactionByID(14).getName());

    }

    @Test
    public void testInsert() {

        assertEquals(transactionHandler.getAllTransactions().size(), EXPECTED_SIZE);

        int numInvalidTests = 8;
        Transaction[] invalid = new Transaction[numInvalidTests];

        //invalid ID
        invalid[0] = new Transaction(0, "2024 BMW M4 Competition Cabriolet", new DateTime(2024, 3, 3, 12, 00), "BMW Dealership", 110200, "", true);
        //ID already exists
        invalid[1] = new Transaction(1, "2024 Ford Raptor R", new DateTime(2024, 2, 28, 12, 00), "Ford Dealership", 118954, "Includes optional Raptor 37 Performance Package", true);
        //invalid name
        invalid[2] = new Transaction(-1, "", new DateTime(2024, 10, 10, 12, 00), "???", 9000000, "???", true);
        //invalid date
        invalid[3] = new Transaction(-1, "Louisiana", new DateTime(1803, 7, 4, 12, 00), "New Orleans", 358000000, "", true);
        //invalid leap year
        invalid[4] = new Transaction(-1, "2024 Land Rover Range Rover SV P615 Long Wheelbase", new DateTime(2023, 2, 29, 10, 00), "Jaguar Land Rover Dealership", 397224, "Maxed out options and accessories", true);
        //invalid place
        invalid[5] = new Transaction(-1, "2024 Acura TLX Type S", new DateTime(2024, 1, 31, 20, 00), "", 66478.50, "", true);
        //invalid amount
        invalid[6] = new Transaction(-1, "The Moon", new DateTime(2020, 12, 25, 16, 20), "Space", -1398140054810.5082150, "Illegally acquired", true);

        String invalidComment = "CANTWAIT!!";
        for( int i = 0; i < 350; i++ )
            invalidComment += "!";

        invalid[7] = new Transaction(-1, "2024 Porsche 911 GT3 RS", new DateTime(2024, 5, 8, 12, 12), "Porsche Dealership", 301439, invalidComment, true);

        for( int i = 0; i < numInvalidTests; i ++ ) {
            assertFalse(transactionHandler.addTransaction(invalid[i]));
        }

        assertEquals(transactionHandler.getAllTransactions().size(), EXPECTED_SIZE);

        //Test valid insertion
        Transaction newTransaction = new Transaction(-1, "Tow Truck Fee", new DateTime(2024, 2, 29, 18, 31), "Pembina Highway", 143.59, "Damn BMW", true);
        transactionHandler.addTransaction(newTransaction);

        assertEquals(transactionHandler.getAllTransactions().size(), EXPECTED_SIZE + 1);

        assertEquals("Morning Dons", transactionHandler.getTransactionByID(1).getName());
        assertEquals("Tow Truck Fee", transactionHandler.getTransactionByID(EXPECTED_SIZE + 1).getName());


    }

    @Test
    public void testDelete() {

        assertEquals(transactionHandler.getAllTransactions().size(), EXPECTED_SIZE);

        assertEquals("Morning Dons", transactionHandler.getTransactionByID(1).getName());
        assertEquals("Star Wars Rebels merch", transactionHandler.getTransactionByID(2).getName());
        assertEquals("Shopping spree at the mall", transactionHandler.getTransactionByID(3).getName());
        assertEquals("Dinner at a fancy restaurant", transactionHandler.getTransactionByID(4).getName());
        assertEquals("Hotel accommodation for business trip", transactionHandler.getTransactionByID(5).getName());
        assertEquals("Grocery shopping for the week", transactionHandler.getTransactionByID(6).getName());
        assertEquals("Utility bill payment", transactionHandler.getTransactionByID(7).getName());
        assertEquals("Concert ticket purchase", transactionHandler.getTransactionByID(8).getName());
        assertEquals("Car maintenance service", transactionHandler.getTransactionByID(9).getName());
        assertEquals("Online shopping for household items", transactionHandler.getTransactionByID(10).getName());
        assertEquals("Movie tickets for family outing", transactionHandler.getTransactionByID(11).getName());
        assertEquals("Gym membership subscription", transactionHandler.getTransactionByID(12).getName());
        assertEquals("Restaurant bill for friend's birthday dinner", transactionHandler.getTransactionByID(13).getName());
        assertEquals("Online course enrollment fee", transactionHandler.getTransactionByID(14).getName());

        Transaction test = new Transaction(-1, "", new DateTime(0, 0, 0, 0, 0), "", 0, "", false);
        assertFalse(transactionHandler.deleteTransaction(test));
        test.setTransactionID(1000);
        assertFalse(transactionHandler.deleteTransaction(test));

        for( int i = 0; i <= EXPECTED_SIZE/2; i ++ ) {
            Transaction toDelete = transactionHandler.getTransactionByID((2* i + 1));
            transactionHandler.deleteTransaction(toDelete);
        }

        assertEquals(EXPECTED_SIZE/2, transactionHandler.getAllTransactions().size());

        assertNull(transactionHandler.getTransactionByID(1));
        assertEquals("Star Wars Rebels merch", transactionHandler.getTransactionByID(2).getName());
        assertNull(transactionHandler.getTransactionByID(3));
        assertEquals("Dinner at a fancy restaurant", transactionHandler.getTransactionByID(4).getName());
        assertNull(transactionHandler.getTransactionByID(5));
        assertEquals("Grocery shopping for the week", transactionHandler.getTransactionByID(6).getName());
        assertNull(transactionHandler.getTransactionByID(7));
        assertEquals("Concert ticket purchase", transactionHandler.getTransactionByID(8).getName());
        assertNull(transactionHandler.getTransactionByID(9));
        assertEquals("Online shopping for household items", transactionHandler.getTransactionByID(10).getName());
        assertNull(transactionHandler.getTransactionByID(11));
        assertEquals("Gym membership subscription", transactionHandler.getTransactionByID(12).getName());
        assertNull(transactionHandler.getTransactionByID(13));
        assertEquals("Online course enrollment fee", transactionHandler.getTransactionByID(14).getName());

    }

    @Test
    public void testModify() {

        assertEquals(transactionHandler.getAllTransactions().size(), EXPECTED_SIZE);

        Transaction toModify = transactionHandler.getTransactionByID(2);

        //Modifying name
        toModify.setName("");
        assertFalse(transactionHandler.modifyTransaction(toModify));
        toModify.setName("Paycheck");
        assertTrue(transactionHandler.modifyTransaction(toModify));
        assertEquals(transactionHandler.getTransactionByID(2).getName(), "Paycheck");

        //Modifying date/time
        toModify.setDateTime(new DateTime(159025, 1048, 1058, 159810, 15970135));
        assertFalse(transactionHandler.modifyTransaction(toModify));
        toModify.setDateTime(new DateTime(2024, 4, 15, 00, 00));
        assertTrue(transactionHandler.modifyTransaction(toModify));

        //Modifying place
        toModify.setPlace("");
        assertFalse(transactionHandler.modifyTransaction(toModify));
        toModify.setPlace("Work");
        assertTrue(transactionHandler.modifyTransaction(toModify));
        assertEquals(transactionHandler.getTransactionByID(2).getPlace(), "Work");

        //Modifying amount
        toModify.setAmount(-1358154093.91399);
        assertFalse(transactionHandler.modifyTransaction(toModify));
        toModify.setAmount(1500.00);
        assertTrue(transactionHandler.modifyTransaction(toModify));
        assertEquals(transactionHandler.getTransactionByID(2).getAmount(), 1500.00, 1);

        //Modifying comment
        toModify.setComments("");
        assertTrue(transactionHandler.modifyTransaction(toModify));

        //Modifying type
        toModify.setType(false);
        assertTrue(transactionHandler.modifyTransaction(toModify));
        assertFalse(transactionHandler.getTransactionByID(2).getType());

        assertEquals(transactionHandler.getAllTransactions().size(), EXPECTED_SIZE);
    }

    @Test
    public void testGetOldestFirst() {

        ArrayList<Transaction> list = transactionHandler.getAllByOldestFirst();

        assertEquals(list.get(0).getName(), transactionHandler.getTransactionByID(2).getName());
        assertEquals(list.get(EXPECTED_SIZE-1).getName(), transactionHandler.getTransactionByID(1).getName());

    }

    @Test
    public void testGetNewestFirst() {

        ArrayList<Transaction> list = transactionHandler.getAllByNewestFirst();

        assertEquals(list.get(0).getName(), transactionHandler.getTransactionByID(1).getName());
        assertEquals(list.get(EXPECTED_SIZE-1).getName(), transactionHandler.getTransactionByID(2).getName());
    }

}
