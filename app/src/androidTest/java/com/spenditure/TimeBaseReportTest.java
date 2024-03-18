package com.spenditure;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.swipeLeft;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom;
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
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;

import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.contrib.PickerActions;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.spenditure.application.Services;
import com.spenditure.logic.ITimeBaseReportHandler;
import com.spenditure.logic.IUserManager;
import com.spenditure.logic.TimeBaseReportHandler;
import com.spenditure.logic.UserManager;
import com.spenditure.object.DateTime;
import com.spenditure.object.Report;
import com.spenditure.presentation.LoginActivity;
import com.spenditure.presentation.UIUtility;
import com.spenditure.presentation.report.ViewReportActivity;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class TimeBaseReportTest {
    private final int sleepTime = 1000;

    private String lastYearNumTransactionsExpected ;
    private String lastYearTotalExpected;
    private String lastYearAverageExpected;
    private String lastYearPercentageExpected;
    private String[] expectedMonthLabels;
    private String [] expectedWeekLabels;
    private String customNumTransactionsExpected ;
    private String customTotalExpected;
    private String customAverageExpected;
    private String customPercentageExpected;

    @Before
    public void setup(){
        ActivityScenario.launch(LoginActivity.class);
        TestUtility.login();
        ITimeBaseReportHandler timeBaseReportHandler = new TimeBaseReportHandler(Services.DEVELOPING_STATUS);
        Report lastYearReport = timeBaseReportHandler.reportBackOneYear(1,TimeBaseReportHandler.getCurrentDate());

        lastYearNumTransactionsExpected = UIUtility.cleanTransactionNumberString(lastYearReport.getNumTrans());
        lastYearTotalExpected = UIUtility.cleanTotalString(lastYearReport.getTotal());
        lastYearAverageExpected = UIUtility.cleanAverageString(lastYearReport.getAvgTransSize());
        lastYearPercentageExpected = UIUtility.cleanPercentageString(lastYearReport.getPercent());

        expectedMonthLabels = DateTime.MONTHS;
        expectedWeekLabels = DateTime.WEEKS;

        DateTime start = new DateTime(2023, 9, 5);
        DateTime end = new DateTime(2024, 3, 4);

        Report custom = timeBaseReportHandler.reportOnUserProvidedDates(1, start, end);
        customNumTransactionsExpected = UIUtility.cleanTransactionNumberString(custom.getNumTrans());
        customTotalExpected = UIUtility.cleanTotalString(custom.getTotal());
        customAverageExpected = UIUtility.cleanAverageString(custom.getAvgTransSize());
        customPercentageExpected = UIUtility.cleanPercentageString(custom.getPercent());
    }

    @After
    public void tearDown(){
        IUserManager userManager = new UserManager(Services.DEVELOPING_STATUS);
        userManager.logout();
    }

    @Test
    public void testTimeBaseReport(){
        onView(withId(R.id.button_report_from)).perform(ViewActions.scrollTo());
        onView(withId(R.id.button_report_from)).check(matches(isDisplayed()));
        onView(withId(R.id.button_report_from)).perform(click());

        SystemClock.sleep(4000);


        onView(withId(R.id.report_last_year)).perform(ViewActions.scrollTo());

        onView(withId(R.id.textview_lastYear_total)).check(matches(withText(containsString(lastYearTotalExpected))));
        onView(withId(R.id.textview_lastYear_percentage)).check(matches(withText(containsString(lastYearPercentageExpected))));
        onView(withId(R.id.textview_lastYear_transactionsCount)).check(matches(withText(containsString(lastYearNumTransactionsExpected))));
        onView(withId(R.id.textview_lastYear_average)).check(matches(withText(containsString(lastYearAverageExpected))));

        SystemClock.sleep(4000);
        onView(withId(R.id.gridlayout_timebase_report)).perform(ViewActions.scrollTo());
        onView(withId(R.id.gridlayout_timebase_report)).check(matches(isDisplayed()));


        for(int i = 0; i < expectedMonthLabels.length ; i ++){

//            onView(allOf(withId(R.id.slide_tittle), isDescendantOfA(firstChildOf(withId(R.id.gridlayout_timebase_report),i))))
//                    .check(matches(withText(expectedMonthLabels[i])));
            onView(withId(R.id.gridlayout_timebase_report)).check(matches(isDisplayed()));
            onView(withId(R.id.gridlayout_timebase_report)).perform(swipeLeft());

        }


        onView(withId(R.id.spinner_timebase_report)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is(ViewReportActivity.TIME_BASE_OPTION[1]))).perform(click());
        SystemClock.sleep(3000);
        for(int i = 0; i < expectedWeekLabels.length ; i ++){
            SystemClock.sleep(500);
            onView(withId(R.id.gridlayout_timebase_report)).check(matches(isDisplayed()));
            onView(withId(R.id.gridlayout_timebase_report)).perform(swipeLeft());
        }

    }

    @Test
    public void testSelectDate(){
        onView(withId(R.id.textview_customTime_percentage)).perform(ViewActions.scrollTo());
        onView(withId(R.id.button_report_from)).check(matches(isDisplayed()));

        TestUtility.setDate(R.id.button_report_from,2023,9,5);
        onView(withId(R.id.button_report_from)).check(matches(withText(containsString("9/5/2023"))));

        SystemClock.sleep(1000);

        onView(withId(R.id.button_report_to)).check(matches(isDisplayed()));
        TestUtility.setDate(R.id.button_report_to,2024,3,4);
        onView(withId(R.id.button_report_to)).check(matches(withText(containsString("3/4/2024"))));

        onView(withId(R.id.button_get_report)).check(matches(isDisplayed()));
        onView(withId(R.id.button_get_report)).perform(click());



        SystemClock.sleep(5000);

        onView(withId(R.id.textview_customTime_totalAmount)).check(matches(withText(containsString(customTotalExpected))));
        onView(withId(R.id.textview_customTime_percentage)).check(matches(withText(containsString(customPercentageExpected))));
        onView(withId(R.id.textview_customTime_totalTrans)).check(matches(withText(containsString(customNumTransactionsExpected))));
        onView(withId(R.id.textview_customTime_average)).check(matches(withText(containsString(customAverageExpected))));

    }



    public static Matcher<View> firstChildOf(final Matcher<View> parentMatcher,int index) {
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
