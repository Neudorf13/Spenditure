package com.spenditure;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import androidx.test.espresso.action.ViewActions;

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
}
