package com.spenditure;



import static androidx.test.InstrumentationRegistry.getTargetContext;
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
import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;

import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.anything;

import android.Manifest;
import android.accounts.AccountManager;
import android.app.UiAutomation;
import android.os.Build;
import android.os.SystemClock;
import android.view.View;
import android.widget.AdapterView;
import android.widget.DatePicker;

import androidx.recyclerview.widget.RecyclerView;
import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.contrib.PickerActions;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.espresso.intent.rule.IntentsTestRule;
import androidx.test.espresso.matcher.BoundedMatcher;
import androidx.test.espresso.matcher.RootMatchers;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.GrantPermissionRule;


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
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

public class ImageTest {

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
    public void addImage()
    {
        // this moves us from the home page to "Create transaction" page
        onView(withId(R.id.navigation_create_transaction)).perform(click());

        // fill in transaction name
        onView(withId(R.id.edittext_what_the_heck)).perform(
                typeText("Testing image"),
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

        // take picture
        onView(withId(R.id.button_take_image)).perform(click());

        // press capture
        onView(withId(R.id.button_take_image)).perform(click());

        // confirm
        onView(withId(R.id.button_confirm)).perform(click());

        // save transaction
        onView(withId(R.id.button_create_transaction)).perform(
                click()
        );

        // check database that it is not null
        assert(transactionHandler.getTransactionByName(UserManager.getUserID(), "Testing image").get(0).getImage() != null);

        // now we click over to view transactions
        onView(withId(R.id.navigation_view_transactions)).perform(click());

        // press on button to sort by newest first
        onView(withId(R.id.togglebutton_transaction_date_sort)).perform(click());

        // select top transaction
        onData(anything()).inAdapterView(withId(R.id.listview_transactions)).atPosition(0).perform(click());

        // click edit
        onView(withId(R.id.button_edit)).perform(click());

        // view image
        onView(withId(R.id.button_view_image)).perform(click());

        // exit image button_back
        onView(withId(R.id.button_back)).perform(click());

        // exit transaction edit screen
        onView(withId(R.id.navigation_create_transaction)).perform(click());


    }


    @Test
    public void addImageToTransaction()
    {
        // this moves us from the home page to "Create transaction" page
        onView(withId(R.id.navigation_view_transactions)).perform(click());

        // select top transaction
        onData(anything()).inAdapterView(withId(R.id.listview_transactions)).atPosition(0).perform(click());

        // click edit
        onView(withId(R.id.button_edit)).perform(click());

        // take picture
        onView(withId(R.id.button_take_image)).perform(click());

        // press capture
        onView(withId(R.id.button_take_image)).perform(click());

        // confirm
        onView(withId(R.id.button_confirm)).perform(click());

        // view image
        onView(withId(R.id.button_view_image)).perform(click());

        // exit image button_back
        onView(withId(R.id.button_back)).perform(click());

        // press save button
        onView(withId(R.id.button_edit_transaction)).perform(click());

        // verify an image has been added
        assert(transactionHandler.getTransactionByName(UserManager.getUserID(), "Morning Dons").get(0).getImage() != null);

        // now we click over to view transactions
        onView(withId(R.id.navigation_view_transactions)).perform(click());

        // select top transaction
        onData(anything()).inAdapterView(withId(R.id.listview_transactions)).atPosition(0).perform(click());

        // click edit
        onView(withId(R.id.button_edit)).perform(click());

        // view image
        onView(withId(R.id.button_view_image)).perform(click());

        // exit image button_back
        onView(withId(R.id.button_back)).perform(click());

        // exit transaction edit screen
        onView(withId(R.id.navigation_create_transaction)).perform(click());

    }
}
