package com.spenditure;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.swipeLeft;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import static org.hamcrest.Matchers.allOf;
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
import com.spenditure.presentation.LoginActivity;
import com.spenditure.presentation.report.ViewReportActivity;
import com.spenditure.utility.TestUtility;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class TimeBaseHeavyTest {
    private final int sleepTime = 5000;
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
    public void testTimeBaseReport(){
        SystemClock.sleep(5000);
        SystemClock.sleep(4000);
        onView(withId(R.id.gridlayout_timebase_report)).perform(ViewActions.scrollTo());
        onView(withId(R.id.gridlayout_timebase_report)).check(matches(isDisplayed()));


        for(int i = 0; i < 12 ; i ++){
            onView(withId(R.id.gridlayout_timebase_report)).check(matches(isDisplayed()));
            onView(withId(R.id.gridlayout_timebase_report)).perform(swipeLeft());

        }


        onView(withId(R.id.spinner_timebase_report)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is(ViewReportActivity.TIME_BASE_OPTION[1]))).perform(click());
        SystemClock.sleep(3000);
        for(int i = 0; i < 4 ; i ++){
            SystemClock.sleep(500);
            onView(withId(R.id.gridlayout_timebase_report)).check(matches(isDisplayed()));
            onView(withId(R.id.gridlayout_timebase_report)).perform(swipeLeft());
        }
    }

}
