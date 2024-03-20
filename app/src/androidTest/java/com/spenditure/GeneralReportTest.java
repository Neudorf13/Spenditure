package com.spenditure;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.action.ViewActions.swipeLeft;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;

import android.os.SystemClock;

import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.action.ViewActions;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.spenditure.application.Services;
import com.spenditure.logic.CategoryHandler;
import com.spenditure.logic.ICategoryHandler;
import com.spenditure.logic.ITransactionHandler;
import com.spenditure.logic.TransactionHandler;
import com.spenditure.logic.UserManager;
import com.spenditure.object.Transaction;
import com.spenditure.presentation.LoginActivity;

import org.junit.After;
import org.junit.Before;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;


@RunWith(AndroidJUnit4.class)
public class GeneralReportTest {
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

        TestUtility.login("TestingUser1","12345");
    }



    @Test
    public void testGeneralReport() {
        SystemClock.sleep(sleepTime);

        assertEquals(4,UserManager.getUserID());
        onView(withId(R.id.textview_general_total_spending)).check(matches(isDisplayed()));
        onView(withId(R.id.textview_summary_num_trans)).check(matches(isDisplayed()));

        onView(withId(R.id.textview_general_total_spending)).check(matches(withText(containsString("757.44"))));
        onView(withId(R.id.textview_summary_num_trans)).check(matches(withText(containsString("3"))));

        //This function called should be placed in @After, but is it because the library I used, @After function is called before @Before?
        TestUtility.cleanUpEnvir(categoryHandler,transactionHandler,4);
    }

    @Test
    public void testCustomGenerral(){
        SystemClock.sleep(sleepTime);

        onView(withId(R.id.textview_custom_report_most)).check(matches(withText(containsString("Food"))));
        onView(withId(R.id.textview_custom_report_least)).check(matches(withText(containsString("Grocery"))));

        onView(withId(R.id.spinner_report)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is("Report by total"))).perform(click());
        SystemClock.sleep(sleepTime);
        onView(withId(R.id.textview_custom_report_most)).check(matches(withText(containsString("Food"))));
        onView(withId(R.id.textview_custom_report_least)).check(matches(withText(containsString("Grocery"))));

        onView(withId(R.id.spinner_report)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is("Report by percentage"))).perform(click());
        SystemClock.sleep(sleepTime);
        onView(withId(R.id.textview_custom_report_most)).check(matches(withText(containsString("Food"))));
        onView(withId(R.id.textview_custom_report_least)).check(matches(withText(containsString("Grocery"))));

        TestUtility.cleanUpEnvir(categoryHandler,transactionHandler,4);
    }

    @Test
    public void testCategoriesReport(){
        onView(withId(R.id.viewpager_report)).perform(scrollTo());
        onView(withId(R.id.viewpager_report)).check(matches(isDisplayed()));

        for(int i = 0; i < 3 ; i ++){

            onView(withId(R.id.viewpager_report)).check(matches(isDisplayed()));
            onView(withId(R.id.viewpager_report)).perform(swipeLeft());
            SystemClock.sleep(sleepTime);

        }
        TestUtility.cleanUpEnvir(categoryHandler,transactionHandler,4);
    }




}
