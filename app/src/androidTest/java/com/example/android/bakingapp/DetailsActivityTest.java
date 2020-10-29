package com.example.android.bakingapp;


import androidx.test.espresso.Espresso;
import androidx.test.espresso.IdlingRegistry;
import androidx.test.espresso.contrib.RecyclerViewActions;

import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner;
import androidx.test.rule.ActivityTestRule;

import com.example.android.bakingapp.activity.DetailsActivity;

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
public class DetailsActivityTest {

    @Rule
    public ActivityTestRule<DetailsActivity> mDetailsActivityTestRule =
            new ActivityTestRule<>(DetailsActivity.class);


    @Before
    public void registerIdlingResource() {

        IdlingRegistry.getInstance().register(SimpleIdlingResource.resource);

    }


    @Test
    public void clickSteptem_OpenActivityHasPreviousButton() {

        Espresso.onView(withId(R.id.stepsRecyclerView)).perform(RecyclerViewActions.actionOnItemAtPosition(2, click()));
        Espresso.onView((withId(R.id.previousButton))).check(matches(isDisplayed()));


    }

    @Test
    public void clickStepItem_OpenRecipeStepActivity() {
        Espresso.onView((withId(R.id.stepsRecyclerView))).check(matches(isDisplayed()));
    }


    @After
    public void unregisterIdlingResource() {
        IdlingRegistry.getInstance().register(SimpleIdlingResource.resource);

    }


}
