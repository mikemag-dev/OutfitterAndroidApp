package com.outfitterandroid;

/**
 * Created by Kris on 4/5/2015.
 */

import android.test.ActivityInstrumentationTestCase2;

import com.parse.ParseUser;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

public class DiscoveryActivityTest extends ActivityInstrumentationTestCase2 {

    public DiscoveryActivityTest() {
        super(DiscoveryActivity.class);
    }

    @Override
    public void setUp() throws Exception {
        super.setUp();
        if(ParseUser.getCurrentUser() != null){
            ParseUser.logOut();
        }
        getActivity();
    }

    //TODO Finish tests
    public void testDiscoveryOpen() throws InterruptedException {
        onView(withId(R.id.facebook_login)).perform(click());
        Thread.sleep(1000);
        //onView(withId(R.id.profile_discovery_button)).check(matches(withText("Discovery")));
        //onView(withId(R.id.profile_discovery_button)).perform(click());
        //Thread.sleep(500);
        //onView(withId(R.id.discovery_like_button)).check(matches(withText("Like")));
        //onView(withId(R.id.discovery_dislike_button)).check(matches(withText("Dislike")));
        onView(withId(R.id.profile_logout_button)).check(matches(withText("Logout")));
        onView(withId(R.id.profile_logout_button)).perform(click());
    }
}
