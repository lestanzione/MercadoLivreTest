package br.com.stanzione.mercadolivretest.main;

import android.content.Intent;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import br.com.stanzione.mercadolivretest.R;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> activityRule = new ActivityTestRule<>(MainActivity.class, true, false);

    @Test
    public void withValidAmountShouldPass(){

        activityRule.launchActivity(new Intent());

        onView(withId(R.id.amountEditText))
            .perform(typeText("0.01"));

        onView(withId(R.id.payButton))
                .perform(click());

    }

    @Test
    public void withInvalidAmountShouldShowMessage() {

        activityRule.launchActivity(new Intent());

        onView(withId(R.id.amountEditText))
                .perform(typeText("0.00"));

        onView(withId(R.id.payButton))
                .perform(click());

        onView(allOf(withId(android.support.design.R.id.snackbar_text), withText(R.string.message_invalid_amount)))
                .check(matches(isDisplayed()));

    }

}