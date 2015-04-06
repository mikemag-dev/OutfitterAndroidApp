package com.outfitterandroid;

import android.test.ActivityInstrumentationTestCase2;

import com.parse.ParseUser;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * Created by Kris on 4/5/2015.
 */


public class ProfileActivityTest extends ActivityInstrumentationTestCase2 {

    public ProfileActivityTest() {
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

    public void testDiscoveryButton() throws InterruptedException {
        String validUsername = "i_am_a_user\n";
        String validPassword = "i_am_a_user\n";

        onView(withId(R.id.login_username_input)).perform(typeText(validUsername));
        onView(withId(R.id.login_password_input)).perform(typeText(validPassword));
        onView(withId(R.id.parse_login_button)).perform(click());
        Thread.sleep(500);
        onView(withId(R.id.profile_discovery_button)).check(matches(withText("Discovery")));
        onView(withId(R.id.profile_logout_button)).perform(click());
    }

    public void testPortfolioButton(){

    }

    public void testLogoutButton(){

    }

    public void testDeleteUserButton(){

    }

    public void testTakePictureButton(){

    }
}
