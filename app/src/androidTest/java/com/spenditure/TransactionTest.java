package com.spenditure;



import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.hasDescendant;
import static androidx.test.espresso.matcher.ViewMatchers.isDescendantOfA;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.anything;

import android.os.SystemClock;
import android.view.View;
import android.widget.AdapterView;
import android.widget.DatePicker;

import androidx.recyclerview.widget.RecyclerView;
import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.contrib.PickerActions;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.espresso.matcher.BoundedMatcher;
import androidx.test.espresso.matcher.RootMatchers;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.spenditure.application.Services;
import com.spenditure.database.TransactionPersistence;
import com.spenditure.logic.CategoryHandler;
import com.spenditure.logic.ICategoryHandler;
import com.spenditure.logic.ITransactionHandler;
import com.spenditure.logic.TransactionHandler;
import com.spenditure.logic.UserManager;
import com.spenditure.object.MainCategory;
import com.spenditure.object.Transaction;
import com.spenditure.presentation.LoginActivity;
import com.spenditure.utility.TestUtility;

import org.hamcrest.Matchers;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;


import java.util.List;

@RunWith(AndroidJUnit4.class)
public class TransactionTest {
    private final int sleepTime = 1000;
    private ITransactionHandler transactionHandler;
    private ICategoryHandler categoryHandler;



    @Before
    public void setup(){

        ActivityScenario.launch(LoginActivity.class);
        SystemClock.sleep(sleepTime);

        transactionHandler = new TransactionHandler(Services.DEVELOPING_STATUS);
        categoryHandler = new CategoryHandler(Services.DEVELOPING_STATUS);
        TestUtility.setUpEnvirForReportTest(categoryHandler,transactionHandler,4);


        TestUtility.login("TestingUser1","12345");
    }


    @After
    public void teardown(){
        TestUtility.cleanUpEnvir(categoryHandler,transactionHandler,4);
    }

    @Test
    public void createTransaction()
    {
        // this moves us from the home page to "Create transaction" page
        onView(withId(R.id.navigation_create_transaction)).perform(click());

        // now we must fill in the info for the new transaction

        // fill in transaction name
        onView(withId(R.id.edittext_what_the_heck)).perform(
                typeText("Tutor Bao in DB mastery"),
                ViewActions.closeSoftKeyboard()
        );

        // fill in date
        onView(withId(R.id.edittext_date)).perform(click());
        onView(withClassName(Matchers.equalTo(DatePicker.class.getName()))).perform(PickerActions.setDate(2024, 5, 13));
        onView(withId(android.R.id.button1)).perform(click());

        // fill in place
        onView(withId(R.id.edittext_place)).perform(
                typeText("Trevor's closet"),
                ViewActions.closeSoftKeyboard()
        );

        // fill in amount
        onView(withId(R.id.edittext_amount)).perform(
                typeText("69"),
                ViewActions.closeSoftKeyboard()
        );

        // fill in comments
        onView(withId(R.id.edittext_comments)).perform(
                typeText("I still get to keep the title, right?"),
                ViewActions.closeSoftKeyboard()
        );

        // toggle button (from withdraw to deposit)
        onView(withId(R.id.togglebutton_type)).perform(
                click()
        );

        // select category option
        onView(withId(R.id.spinner_categories)).perform(click());
        onView(allOf(withText("Food"))).perform(click());

        // save the transaction
        onView(withId(R.id.button_create_transaction)).perform(
                click()
        );

        // now we click over to view transactions
        onView(withId(R.id.navigation_view_transactions)).perform(click());

        // press on button to sort by newest first
        onView(withId(R.id.togglebutton_transaction_date_sort)).perform(click());

        // get top most transaction then check to see whether it is what we expect it to be
        onData(anything()).inAdapterView(withId(R.id.listview_transactions)).atPosition(0).
                onChildView(withId(R.id.textview_list_what_the_heck)).
                check(matches(withText("Tutor Bao in DB mastery")));

        onData(anything()).inAdapterView(withId(R.id.listview_transactions)).atPosition(0).
                onChildView(withId(R.id.textview_list_amount)).
                check(matches(withText("$69.00")));
    }



    @Test
    public void viewTransaction()
    {
        // now we click over to view transactions
        onView(withId(R.id.navigation_view_transactions)).perform(click());

        // check to see if the topmost transaction contains what we expect it to contain
        onData(anything()).inAdapterView(withId(R.id.listview_transactions)).atPosition(0).
                onChildView(withId(R.id.textview_list_what_the_heck)).
                check(matches(withText("Morning Dons")));

        onData(anything()).inAdapterView(withId(R.id.listview_transactions)).atPosition(0).
                onChildView(withId(R.id.textview_list_amount)).
                check(matches(withText("$5.99")));

        // click the sort button
        onView(withId(R.id.togglebutton_transaction_date_sort)).perform(click());

        // check to see if the new top transaction contains what we want it to contain
        onData(anything()).inAdapterView(withId(R.id.listview_transactions)).atPosition(0).
                onChildView(withId(R.id.textview_list_what_the_heck)).
                check(matches(withText("Star Wars Rebels merch")));

        onData(anything()).inAdapterView(withId(R.id.listview_transactions)).atPosition(0).
                onChildView(withId(R.id.textview_list_amount)).
                check(matches(withText("$500.95")));

        // go back and make sure the old top transaction has not changed

        // click the sort button
        onView(withId(R.id.togglebutton_transaction_date_sort)).perform(click());

        // check to see if the topmost transaction contains what we expect it to contain
        onData(anything()).inAdapterView(withId(R.id.listview_transactions)).atPosition(0).
                onChildView(withId(R.id.textview_list_what_the_heck)).
                check(matches(withText("Morning Dons")));

        onData(anything()).inAdapterView(withId(R.id.listview_transactions)).atPosition(0).
                onChildView(withId(R.id.textview_list_amount)).
                check(matches(withText("$5.99")));

    }



    @Test
    public void deleteTransaction()
    {
        // now we click over to view transactions
        onView(withId(R.id.navigation_view_transactions)).perform(click());

        // press on button to sort by newest first
        onView(withId(R.id.togglebutton_transaction_date_sort)).perform(click());

        // click on second transaction
        onData(anything()).inAdapterView(withId(R.id.listview_transactions)).atPosition(1).perform(click());

        // delete second transaction
        onView(withId(R.id.button_delete)).perform(click());

        // refresh page
        onView(withId(R.id.navigation_user)).perform(click());
        onView(withId(R.id.navigation_view_transactions)).perform(click());

        // check if new second transaction matches what we expect it to match
        onData(anything()).inAdapterView(withId(R.id.listview_transactions)).atPosition(1).
                onChildView(withId(R.id.textview_list_what_the_heck)).
                check(matches(withText("Star Wars Rebels merch")));

        onData(anything()).inAdapterView(withId(R.id.listview_transactions)).atPosition(1).
                onChildView(withId(R.id.textview_list_amount)).
                check(matches(withText("$500.95")));

    }

    @Test
    public void modifyTransaction()
    {
        // now we click over to view transactions
        onView(withId(R.id.navigation_view_transactions)).perform(click());

        // select second transaction from the top
        onData(anything()).inAdapterView(withId(R.id.listview_transactions)).atPosition(1).perform(click());

        // click edit
        onView(withId(R.id.button_edit)).perform(click());

        // edit name
        onView(withId(R.id.edittext_what_the_heck)).perform(
                replaceText("PP fan club store"),
                ViewActions.closeSoftKeyboard()
        );

        // edit place
        onView(withId(R.id.edittext_place)).perform(
                replaceText("That mall"),
                ViewActions.closeSoftKeyboard()
        );

        // edit amount
        onView(withId(R.id.edittext_amount)).perform(
                replaceText("3350.99"),
                ViewActions.closeSoftKeyboard()
        );

        // edit type
        onView(withId(R.id.togglebutton_type)).perform(
                click()
        );

        // changed my mind
        onView(withId(R.id.togglebutton_type)).perform(
                click()
        );

        // edit category
        onView(withId(R.id.spinner_categories)).perform(click());
        onView(allOf(withText("Hang out"))).perform(click());

        // edit comments
        onView(withId(R.id.edittext_comments)).perform(
                replaceText("Bought life size Pedro Pascal body pillow"),
                ViewActions.closeSoftKeyboard()
        );

        // press save button
        onView(withId(R.id.button_edit_transaction)).perform(click());

        // verify information has been changed
        onData(anything()).inAdapterView(withId(R.id.listview_transactions)).atPosition(1).
                onChildView(withId(R.id.textview_list_what_the_heck)).
                check(matches(withText("PP fan club store")));

        onData(anything()).inAdapterView(withId(R.id.listview_transactions)).atPosition(1).
                onChildView(withId(R.id.textview_list_amount)).
                check(matches(withText("$3350.99")));

    }




}
