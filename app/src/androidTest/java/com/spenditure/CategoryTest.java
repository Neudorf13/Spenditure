package com.spenditure;


import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.hasDescendant;
import static androidx.test.espresso.matcher.ViewMatchers.isDescendantOfA;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import static org.hamcrest.Matchers.allOf;

import android.view.View;

import androidx.recyclerview.widget.RecyclerView;
import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.espresso.matcher.BoundedMatcher;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.spenditure.application.Services;
import com.spenditure.database.utils.DBHelper;
import com.spenditure.logic.CategoryHandler;
import com.spenditure.logic.UserManager;
import com.spenditure.object.MainCategory;
import com.spenditure.presentation.LoginActivity;
import com.spenditure.presentation.category.ViewCategoryActivity;

import org.checkerframework.checker.units.qual.C;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

@RunWith(AndroidJUnit4.class)
public class CategoryTest {
    private int numberCategory;
    private CategoryHandler categoryHandler;
    @Before
    public void setup(){


        ActivityScenario.launch(LoginActivity.class);
        categoryHandler = new CategoryHandler(Services.DEVELOPING_STATUS);
        TestUtility.login();
        onView(withId(R.id.navigation_category)).perform(click());



        List<MainCategory> currListCats = categoryHandler.getAllCategory(UserManager.getUserID());
        numberCategory = currListCats.size();
        for (MainCategory currCat : currListCats){
            if(currCat.getName().equals("New test category")){
                categoryHandler.deleteCategory(currCat.getCategoryID());
                numberCategory--;
            }
        }
    }
    @Test
    public void createCategory(){
        onView(withId(R.id.floatingActionButton_category)).perform(click());
        onView(withId(R.id.category_edittext)).perform(
                typeText("New test category"),
                ViewActions.closeSoftKeyboard()
        );
        onView(withId(R.id.category_save_btn)).perform(click());
        onView(withId(R.id.floatingActionButton_refresh)).perform(click());

        onView(withId(R.id.recyclerview_category)).perform(RecyclerViewActions.scrollToPosition(numberCategory - 1));
        //Check if the new category is added
        onView(withId(R.id.recyclerview_category)).perform(RecyclerViewActions.actionOnItem(hasDescendant(withText("New test category")), click()));


    }


}
