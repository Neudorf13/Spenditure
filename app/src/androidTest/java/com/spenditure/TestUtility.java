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
}
