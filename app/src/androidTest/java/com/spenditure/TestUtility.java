package com.spenditure;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withParent;

import android.os.SystemClock;
import android.widget.DatePicker;

import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.contrib.PickerActions;

import com.spenditure.logic.ICategoryHandler;
import com.spenditure.logic.ITransactionHandler;
import com.spenditure.object.DateTime;
import com.spenditure.object.MainCategory;

public abstract class TestUtility {
    public static void login(){
        onView(withId(R.id.edittext_username)).perform(
                typeText("Me"),
                ViewActions.closeSoftKeyboard()
        );
        onView(withId(R.id.edittext_current_password)).perform(
                typeText("123"),
                ViewActions.closeSoftKeyboard()
        );
        onView(withId(R.id.button_login)).perform(click());
    }

    public static void setDate(int buttonStartDatePicker, int year, int monthOfYear, int dayOfMonth) {
        onView(withId(buttonStartDatePicker)).perform(click());
        SystemClock.sleep(1000);
        onView(isAssignableFrom(DatePicker.class))
                .perform(PickerActions.setDate(year, monthOfYear, dayOfMonth));
        onView(withId(android.R.id.button1)).perform(click());
    }

    public static void setUpTestDBForUser(ICategoryHandler categoryHandler,ITransactionHandler transactionHandler, int userID){
        //This is the same set up we use to do unit test, we found it useful to re-use to setting we have in unit test because we knew what are the expected values
        MainCategory grocery = categoryHandler.addCategory("Grocery",4);
        MainCategory food  = categoryHandler.addCategory("Food",4);
        MainCategory hangOut = categoryHandler.addCategory("Hang out",4);

        transactionHandler.addTransaction( 4, "Morning Dons", new DateTime(2000,1,1,1,1, 1),
                "Mcdonalds",5.99, "was luke warm today, 2/10", true, null, grocery.getCategoryID());
        transactionHandler.addTransaction( 4, "Star Wars Rebels merch", new DateTime(2024,2,1,16,59, 59),
                "Toys R Us",500.95, "Sabine looking kinda nice O_o", true, null, food.getCategoryID());
        transactionHandler.addTransaction(4, "Shopping spree at the mall", new DateTime(2023, 5, 20, 15, 30, 30),
                "Mall", 250.50, "Bought clothes and accessories", true, null, hangOut.getCategoryID());

    }
}
