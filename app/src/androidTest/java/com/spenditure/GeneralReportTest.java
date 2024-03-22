package com.spenditure;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.action.ViewActions.swipeLeft;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDescendantOfA;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;

import android.os.SystemClock;
import android.view.View;
import android.view.ViewGroup;

import androidx.test.core.app.ActivityScenario;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.spenditure.application.Services;
import com.spenditure.logic.CategoryHandler;
import com.spenditure.logic.ICategoryHandler;
import com.spenditure.logic.ITransactionHandler;
import com.spenditure.logic.TransactionHandler;
import com.spenditure.logic.UserManager;
import com.spenditure.presentation.LoginActivity;
import com.spenditure.utility.TestUtility;
import com.spenditure.utility.ViewPagerSupporter;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.After;
import org.junit.Before;

import org.junit.Test;
import org.junit.runner.RunWith;


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

    @After
    public void teardown(){
        TestUtility.cleanUpEnvir(categoryHandler,transactionHandler,4);
    }



    @Test
    public void testGeneralReport() {
        SystemClock.sleep(sleepTime);

        assertEquals(4,UserManager.getUserID());
        onView(withId(R.id.textview_general_total_spending)).check(matches(isDisplayed()));
        onView(withId(R.id.textview_summary_num_trans)).check(matches(isDisplayed()));

        onView(withId(R.id.textview_general_total_spending)).check(matches(withText(containsString("757.44"))));
        onView(withId(R.id.textview_summary_num_trans)).check(matches(withText(containsString("3"))));


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

    }

    @Test
    public void testCategoriesReport(){
        onView(withId(R.id.viewpager_report)).perform(scrollTo());
        onView(withId(R.id.viewpager_report)).check(matches(isDisplayed()));



        //Check the first item of viewpager
        onView(allOf(withId(R.id.slide_tittle), isDescendantOfA(ViewPagerSupporter.getChildOf(withId(R.id.viewpager_report),0))))
                    .check(matches(withText("Grocery")));
        onView(allOf(withId(R.id.textview_catReport_transactionsCount), isDescendantOfA(ViewPagerSupporter.getChildOf(withId(R.id.viewpager_report),0))))
                .check(matches(withText("1 transactions")));

        onView(allOf(withId(R.id.textview_catReport_total), isDescendantOfA(ViewPagerSupporter.getChildOf(withId(R.id.viewpager_report),0))))
                .check(matches(withText("$5.99 CAD")));

        onView(allOf(withId(R.id.textview_catReport_percentage), isDescendantOfA(ViewPagerSupporter.getChildOf(withId(R.id.viewpager_report),0))))
                .check(matches(withText("0.8%")));

        onView(allOf(withId(R.id.textview_catReport_average), isDescendantOfA(ViewPagerSupporter.getChildOf(withId(R.id.viewpager_report),0))))
                .check(matches(withText("$5.99 CAD")));

        //Check the second item of viewpager
        onView(withId(R.id.viewpager_report)).check(matches(isDisplayed()));
        onView(withId(R.id.viewpager_report)).perform(swipeLeft());
        SystemClock.sleep(sleepTime);

        onView(allOf(withId(R.id.slide_tittle), isDescendantOfA(firstChildOf(withId(R.id.viewpager_report),1))))
                .check(matches(withText("Food")));


        onView(allOf(withId(R.id.textview_catReport_transactionsCount), isDescendantOfA(ViewPagerSupporter.getChildOf(withId(R.id.viewpager_report),1))))
                .check(matches(withText("1 transactions")));

        onView(allOf(withId(R.id.textview_catReport_total), isDescendantOfA(ViewPagerSupporter.getChildOf(withId(R.id.viewpager_report),1))))
                .check(matches(withText("$500.95 CAD")));

        onView(allOf(withId(R.id.textview_catReport_percentage), isDescendantOfA(ViewPagerSupporter.getChildOf(withId(R.id.viewpager_report),1))))
                .check(matches(withText("66.14%")));

        onView(allOf(withId(R.id.textview_catReport_average), isDescendantOfA(ViewPagerSupporter.getChildOf(withId(R.id.viewpager_report),1))))
                .check(matches(withText("$500.95 CAD")));

        onView(withId(R.id.viewpager_report)).check(matches(isDisplayed()));
        onView(withId(R.id.viewpager_report)).perform(swipeLeft());
        SystemClock.sleep(sleepTime);

    }

    private static Matcher<View> firstChildOf(final Matcher<View> parentMatcher, int index) {
        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("with first child view of type parentMatcher");
            }

            @Override
            public boolean matchesSafely(View view) {

                if (!(view.getParent() instanceof ViewGroup)) {
                    return parentMatcher.matches(view.getParent());
                }
                ViewGroup group = (ViewGroup) view.getParent();
                return parentMatcher.matches(view.getParent()) && group.getChildAt(index).equals(view);

            }
        };
    }




}
