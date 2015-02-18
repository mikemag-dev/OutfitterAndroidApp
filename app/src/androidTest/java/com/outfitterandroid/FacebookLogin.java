package com.outfitterandroid;

/**
 * Created by MaguireM on 2/18/15.
 */

import android.test.ActivityInstrumentationTestCase2;
import android.view.View;

import org.hamcrest.Matcher;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static android.support.test.espresso.matcher.ViewMatchers.withId;


public class FacebookLogin extends ActivityInstrumentationTestCase2 {

    public FacebookLogin(Class activityClass) {
        super(activityClass);
    }

    @Override
    public void setUp() throws Exception {
        super.setUp();
        getActivity();
    }

    public void testLoginWithFacebook() {
        onView(withText("Log in with Facebook")).perform(click());
    }
}
