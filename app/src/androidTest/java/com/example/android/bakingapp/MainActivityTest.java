package com.example.android.bakingapp;


import androidx.test.espresso.Espresso;
import androidx.test.espresso.IdlingRegistry;
import androidx.test.espresso.contrib.RecyclerViewActions;

import androidx.test.rule.ActivityTestRule;
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner;


import com.example.android.bakingapp.activity.MainActivity;
import com.example.android.bakingapp.idlingResource.SimpleIdlingResource;


import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;


import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;


@RunWith(AndroidJUnit4ClassRunner.class)
public class MainActivityTest {


    private SimpleIdlingResource idlingResource;

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule =
            new ActivityTestRule<>(MainActivity.class);

    @Before
    public void registerIdlingResource() {
        IdlingRegistry.getInstance().register(SimpleIdlingResource.resource);
    }


    @Test
    public void clickRecipeItem_OpenRecipeViewActivity() {

        Espresso.onView((withId(R.id.recipeRecyclerView))).check(matches(isDisplayed()));

    }

    @Test
    public void clickOnRecipeList_OpenRecipeIngredients() {


        Espresso.onView(withId(R.id.recipeRecyclerView)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        Espresso.onView((withId(R.id.ingredientsRecyclerView))).check(matches(isDisplayed()));


    }


    @After
    public void unregisterIdlingResource() {
        IdlingRegistry.getInstance().register(SimpleIdlingResource.resource);

    }


}