package com.spenditure;


import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.pressKey;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;

import android.os.SystemClock;
import android.view.KeyEvent;

import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.action.ViewActions;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.spenditure.application.Services;
import com.spenditure.logic.CategoryHandler;
import com.spenditure.logic.ICategoryHandler;
import com.spenditure.logic.ITransactionHandler;
import com.spenditure.logic.TransactionHandler;
import com.spenditure.logic.UserManager;
import com.spenditure.presentation.LoginActivity;
import com.spenditure.utility.TestUtility;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

public class AccountManagementTest
{
    private final int sleepTime = 10000;
    private ITransactionHandler transactionHandler;
    private ICategoryHandler categoryHandler;

    @Before
    public void setup(){
        ActivityScenario.launch(LoginActivity.class);
        SystemClock.sleep(sleepTime);

        transactionHandler = new TransactionHandler(Services.DEVELOPING_STATUS);
        categoryHandler = new CategoryHandler(Services.DEVELOPING_STATUS);
        TestUtility.setUpEnvirForReportTest(categoryHandler,transactionHandler,4);

       // TestUtility.login("TestingUser1","12345");
    }

    @After
    public void teardown(){
        TestUtility.cleanUpEnvir(categoryHandler,transactionHandler,4);

        SystemClock.sleep(sleepTime);
    }

    @Test
    public void changePassword()
    {
        // login
        TestUtility.login("TestingUser1","12345");

        // move to profile management section
        onView(withId(R.id.navigation_user)).perform(click());

        // edit username
        onView(withId(R.id.edittext_username)).perform(
                replaceText("TestingUser1New"),
                ViewActions.closeSoftKeyboard()
        );

        // enter old password
        onView(withId(R.id.edittext_current_password)).perform(
                replaceText("12345"),
                ViewActions.closeSoftKeyboard()
        );

        // enter new password
        onView(withId(R.id.edittext_new_password)).perform(
                typeText("54321"),
                ViewActions.closeSoftKeyboard()
        );

        // save changes
        onView(withId(R.id.button_save_changes)).perform(click());

        // log out
        onView(withId(R.id.button_logout)).perform(click());

        // log back in
        onView(withId(R.id.edittext_username)).perform(
                typeText("TestingUser1New"),
                ViewActions.closeSoftKeyboard()
        );
        onView(withId(R.id.edittext_current_password)).perform(
                typeText("54321"),
                ViewActions.closeSoftKeyboard()
        );
        onView(withId(R.id.button_login)).perform(click());

    }

    @Test
    public void newAccount()
    {
        // create new account
        onView(withId(R.id.floatingActionButton_register)).perform(click());

        SystemClock.sleep(sleepTime);

        // edit username
        onView(withId(R.id.edittext_username)).perform(
                typeText("NewUserName"),
                ViewActions.closeSoftKeyboard()
        );

        // enter new password
        onView(withId(R.id.edittext_current_password)).perform(
                typeText("NewPassword123"),
                ViewActions.closeSoftKeyboard()
        );

        // enter new email
        onView(withId(R.id.edittext_email)).perform(
                typeText("johnny.appleseed@domain.com"),
                ViewActions.closeSoftKeyboard()
        );

        // select question
        onView(withId(R.id.spinner_security_question)).perform(click());
        onView(allOf(withText("best comp class"))).perform(click());

        // enter question answer
        onView(withId(R.id.edittext_question_answer)).perform(
                typeText("Rob's class"),
                ViewActions.closeSoftKeyboard()
        );

        // save changes
        onView(withId(R.id.button_register)).perform(click());

        // we are now logged in

        // move to profile management section
        onView(withId(R.id.navigation_user)).perform(click());

        // log out
        onView(withId(R.id.button_logout)).perform(click());

        // log back in
        onView(withId(R.id.edittext_username)).perform(
                typeText("NewUserName"),
                ViewActions.closeSoftKeyboard()
        );
        onView(withId(R.id.edittext_current_password)).perform(
                typeText("NewPassword123"),
                ViewActions.closeSoftKeyboard()
        );
        onView(withId(R.id.button_login)).perform(click());
    }




}
