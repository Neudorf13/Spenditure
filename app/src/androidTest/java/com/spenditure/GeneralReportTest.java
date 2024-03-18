package com.spenditure;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.action.ViewActions.swipeLeft;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDescendantOfA;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;

import android.os.SystemClock;
import android.view.View;
import android.view.ViewGroup;

import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.action.ViewActions;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.spenditure.application.Services;
import com.spenditure.logic.GeneralReportHandler;
import com.spenditure.logic.UserManager;
import com.spenditure.object.CategoryReport;
import com.spenditure.object.MainCategory;
import com.spenditure.presentation.LoginActivity;
import com.spenditure.presentation.UIUtility;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Before;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;


@RunWith(AndroidJUnit4.class)
public class GeneralReportTest {
    private final int sleepTime = 1000;
    private GeneralReportHandler generalReportHandler;
    private String expectedTotal;
    private String expectedCount;

    private String expectedSpendMostAverage;
    private String expectedSpendLeastAverage;

    private String expectedSpendMostTotal;
    private String expectedSpendLeastTotal;

    private String expectedSpendPercentage;
    private String expectedLeastPercentage;

    private List<CategoryReport>categoryReportList;

    @Before
    public void setup(){
        ActivityScenario.launch(LoginActivity.class);

        TestUtility.login();

        generalReportHandler = new GeneralReportHandler(Services.DEVELOPING_STATUS);
        categoryReportList = generalReportHandler.getAllCategoryReport(1);
        double total = generalReportHandler.totalSpending(1);
        expectedTotal =  UIUtility.cleanTotalString(total) ;
        int numbersTransaction = generalReportHandler.numTransactions(1);
        expectedCount = UIUtility.cleanTransactionNumberString(numbersTransaction);

        List<MainCategory> sortedListCat = generalReportHandler.sortByAverage(1, true);

        expectedSpendMostAverage = sortedListCat.get(0).getName();
        expectedSpendLeastAverage = sortedListCat.get(sortedListCat.size() - 1).getName();

        sortedListCat = generalReportHandler.sortByTotal(1, true);

        expectedSpendMostTotal = sortedListCat.get(0).getName();
        expectedSpendLeastTotal = sortedListCat.get(sortedListCat.size() - 1).getName();

        sortedListCat = generalReportHandler.sortByPercent(1, true);

        expectedSpendPercentage = sortedListCat.get(0).getName();
        expectedLeastPercentage = sortedListCat.get(sortedListCat.size() - 1).getName();

        SystemClock.sleep(sleepTime);


    }

    @Test
    public void testGeneralReport(){
        onView(withId(R.id.textview_general_total_spending)).check(matches(isDisplayed()));
        onView(withId(R.id.textview_summary_num_trans)).check(matches(isDisplayed()));



        onView(withId(R.id.textview_general_total_spending)).check(matches(withText(containsString(expectedTotal))));
        onView(withId(R.id.textview_summary_num_trans)).check(matches(withText(containsString(expectedCount))));

        onView(withId(R.id.textview_custom_report_most)).check(matches(withText(containsString(expectedSpendMostAverage))));
        onView(withId(R.id.textview_custom_report_least)).check(matches(withText(containsString(expectedSpendLeastAverage))));

        onView(withId(R.id.spinner_report)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is("Report by total"))).perform(click());
        SystemClock.sleep(sleepTime);
        onView(withId(R.id.textview_custom_report_most)).check(matches(withText(containsString(expectedSpendMostTotal))));
        onView(withId(R.id.textview_custom_report_least)).check(matches(withText(containsString(expectedSpendLeastTotal))));


        onView(withId(R.id.spinner_report)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is("Report by percentage"))).perform(click());
        SystemClock.sleep(sleepTime);
        onView(withId(R.id.textview_custom_report_most)).check(matches(withText(containsString(expectedSpendPercentage))));
        onView(withId(R.id.textview_custom_report_least)).check(matches(withText(containsString(expectedLeastPercentage))));


        onView(withId(R.id.viewpager_report)).perform(scrollTo());
        onView(withId(R.id.viewpager_report)).check(matches(isDisplayed()));

        for(int i = 0; i < categoryReportList.size() ; i ++){
            
            onView(withId(R.id.viewpager_report)).check(matches(isDisplayed()));
            onView(withId(R.id.viewpager_report)).perform(swipeLeft());

        }

    }


}
