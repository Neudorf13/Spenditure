package com.spenditure;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;

import android.os.SystemClock;

import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.ViewAction;
import androidx.test.espresso.action.ViewActions;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.spenditure.application.Services;
import com.spenditure.logic.CategoryHandler;
import com.spenditure.logic.ICategoryHandler;
import com.spenditure.logic.ITransactionHandler;
import com.spenditure.logic.TransactionHandler;
import com.spenditure.object.Transaction;
import com.spenditure.presentation.LoginActivity;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

@RunWith(AndroidJUnit4.class)
public class IllustrativeReportTest {
    private final int sleepTime = 1000;
    @Before
    public void setup(){
        ActivityScenario.launch(LoginActivity.class);
        TestUtility.login("Me","123");
    }

    @Test
    public void testChartAvailable(){
        SystemClock.sleep(3000);

        onView(withId(R.id.piechart_category)).perform(ViewActions.scrollTo());
        onView(withId(R.id.piechart_category)).check(matches(isDisplayed()));

        //Test line diagram exist
        SystemClock.sleep(sleepTime);
        onView(withId(R.id.lineChart_spending)).perform(ViewActions.scrollTo());
        onView(withId(R.id.lineChart_spending)).check(matches(isDisplayed()));

        //Test line diagram exists if choose another option "Report by month breaking into weeks"
        onView(withId(R.id.spinner_timebase_report)).perform(ViewActions.scrollTo());
        onView(withId(R.id.spinner_timebase_report)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is("Report by month breaking into weeks"))).perform(click());
        SystemClock.sleep(sleepTime);
        onView(withId(R.id.lineChart_spending)).perform(ViewActions.scrollTo());
        onView(withId(R.id.lineChart_spending)).check(matches(isDisplayed()));

        //Test line diagram exists if choose another option "Report by year breaking into month"
        onView(withId(R.id.spinner_timebase_report)).perform(ViewActions.scrollTo());
        onView(withId(R.id.spinner_timebase_report)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is("Report by year breaking into month"))).perform(click());
        SystemClock.sleep(sleepTime);
        onView(withId(R.id.lineChart_spending)).perform(ViewActions.scrollTo());
        onView(withId(R.id.lineChart_spending)).check(matches(isDisplayed()));
    }

}
