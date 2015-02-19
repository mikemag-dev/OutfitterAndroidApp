package com.outfitterandroid;

/**
 * Created by MaguireM on 2/18/15.
 */

import android.support.test.espresso.Espresso;
import android.support.test.espresso.ViewInteraction;
import android.support.test.espresso.action.ViewActions;
import android.test.ActivityInstrumentationTestCase2;
import android.view.View;
import android.content.Context;
import android.support.test.espresso.base.IdlingResourceRegistry;


import com.parse.ParseUser;

import org.hamcrest.Matcher;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;;
import static android.support.test.espresso.assertion.ViewAssertions.doesNotExist;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.isRoot;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static java.lang.Thread.sleep;;


public class LoginTest extends ActivityInstrumentationTestCase2 {


    public LoginTest() {
        super(LoginDispatchActivity.class);
    }

    @Override
    public void setUp() throws Exception {
        super.setUp();
        if(ParseUser.getCurrentUser() != null){
            ParseUser.logOut();
        }
        getActivity();
    }

    public void testLoginWithFacebook() throws InterruptedException {
        //facebook login is a web service that idles, non-thread-safe testing issues
        onView(withId(R.id.facebook_login)).perform(click());
        Thread.sleep(500);
        onView(withId(R.id.profile_logout_button)).check(matches(withText("Logout")));
        onView(withId(R.id.profile_logout_button)).perform(click());

    }

    public void testLoginValidUserNoPassword() throws InterruptedException {
        String validUsername = "i_am_a_user\n";
        String emptyPassword = "\n";

        onView(withId(R.id.login_username_input)).perform(typeText(validUsername));
        onView(withId(R.id.login_password_input)).perform(typeText(emptyPassword));
        onView(withId(R.id.parse_login_button)).perform(click());
        Thread.sleep(500);

        onView(withId(R.id.profile_logout_button)).check(doesNotExist());
    }

    public void testLoginValidUserInvalidPassword() throws InterruptedException {
        String validUsername = "i_am_a_user\n";
        String invalidPassword = "not_the_password\n";

        onView(withId(R.id.login_username_input)).perform(typeText(validUsername));
        onView(withId(R.id.login_password_input)).perform(typeText(invalidPassword));
        onView(withId(R.id.parse_login_button)).perform(click());
        Thread.sleep(500);

        onView(withId(R.id.profile_logout_button)).check(doesNotExist());
    }

    public void testLoginValidUserCorrectPassword() throws InterruptedException {
        String validUsername = "i_am_a_user\n";
        String validPassword = "i_am_a_user\n";

        onView(withId(R.id.login_username_input)).perform(typeText(validUsername));
        onView(withId(R.id.login_password_input)).perform(typeText(validPassword));
        onView(withId(R.id.parse_login_button)).perform(click());
        Thread.sleep(500);
        onView(withId(R.id.profile_logout_button)).check(matches(withText("Logout")));
        //onView(withId(R.id.profile_logout_button)).perform(click());
    }
}
