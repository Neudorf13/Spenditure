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
    private ITransactionHandler transactionHandler;
    private ICategoryHandler categoryHandler;

    @Before
    public void setup() {
        // create transaction handler with stub
        this.userManager = new UserManager(Services.DEVELOPING_STATUS);
        // reset transaction handler
        UserManager.cleanup(true);

        // login (this is the same for every test)
        ActivityScenario.launch(LoginActivity.class);
        TestUtility.login("Me", "123");
    }

    @After
    public void tearDown()
    {
        // log out
        onView(withId(R.id.navigation_user)).perform(click());
        onView(withId(R.id.button_logout)).perform(click());

    }

    @Test
    public void changePassword()
    {
        // move to profile management section
        onView(withId(R.id.navigation_user)).perform(click());

        //delete current username
        onView(withId(R.id.edittext_username))
                .perform(replaceText("Me"))
                .perform(click())
                .perform(pressKey(KeyEvent.KEYCODE_DEL))
                .perform(pressKey(KeyEvent.KEYCODE_DEL))
                .check(matches(withText("")));

        // edit username
        onView(withId(R.id.edittext_username)).perform(
                typeText("Me2"),
                ViewActions.closeSoftKeyboard()
        );

        // enter new password
        onView(withId(R.id.edittext_new_password)).perform(
                typeText("321"),
                ViewActions.closeSoftKeyboard()
        );

        // save changes
        onView(withId(R.id.button_save_changes)).perform(click());

        // log out
        onView(withId(R.id.button_logout)).perform(click());

        // log back in
        onView(withId(R.id.edittext_username)).perform(
                typeText("Me2"),
                ViewActions.closeSoftKeyboard()
        );
        onView(withId(R.id.edittext_current_password)).perform(
                typeText("321"),
                ViewActions.closeSoftKeyboard()
        );
        onView(withId(R.id.button_login)).perform(click());

    }


    @Test
    public void newAccount()
    {
        // move to profile management section
        onView(withId(R.id.navigation_user)).perform(click());

        // log out
        onView(withId(R.id.button_logout)).perform(click());

        // create new account
        onView(withId(R.id.floatingActionButton_register)).perform(click());

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
                typeText("TestUserEmail@gmail.com"),
                ViewActions.closeSoftKeyboard()
        );

        // enter new email
        onView(withId(R.id.edittext_email)).perform(
                typeText("TestUserEmail@gmail.com"),
                ViewActions.closeSoftKeyboard()
        );

        // select question
        onView(withId(R.id.spinner_categories)).perform(click());
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
