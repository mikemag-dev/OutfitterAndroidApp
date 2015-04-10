package com.outfitterandroid;

/**
 * Created by Kris on 4/5/2015.
 */

import android.test.ActivityInstrumentationTestCase2;

import com.parse.ParseUser;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.core.AllOf.allOf;
import static org.hamcrest.core.IsInstanceOf.instanceOf;

public class DiscoveryActivityTest extends ActivityInstrumentationTestCase2 {

    private static final String VALID_USERNAME = "i_am_a_user\n";
    private static final String VALID_PASSWORD = "i_am_a_user\n";

    public DiscoveryActivityTest() {
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

    public void testDiscoveryOpen() throws InterruptedException {
        onView(withId(R.id.login_username_input)).perform(typeText(VALID_USERNAME));
        onView(withId(R.id.login_password_input)).perform(typeText(VALID_PASSWORD));
        onView(withId(R.id.parse_login_button)).perform(click());
        Thread.sleep(2000);
        onView(withId(R.id.profile_discovery_button)).check(matches(withText("Discovery")));
        onView(withId(R.id.profile_discovery_button)).perform(click());

    }

    public void testDiscoveryDislikeButtonExists() throws InterruptedException {
        onView(withId(R.id.login_username_input)).perform(typeText(VALID_USERNAME));
        onView(withId(R.id.login_password_input)).perform(typeText(VALID_PASSWORD));
        onView(withId(R.id.parse_login_button)).perform(click());
        Thread.sleep(2000);
        onView(withId(R.id.profile_discovery_button)).check(matches(withText("Discovery")));
        onView(withId(R.id.profile_discovery_button)).perform(click());
        onView(withId(R.id.discovery_dislike_button)).check(matches(withText("Dislike")));
    }

    public void testDiscoverLikeButtonExists() throws InterruptedException {
        onView(withId(R.id.login_username_input)).perform(typeText(VALID_USERNAME));
        onView(withId(R.id.login_password_input)).perform(typeText(VALID_PASSWORD));
        onView(withId(R.id.parse_login_button)).perform(click());
        Thread.sleep(2000);
        onView(withId(R.id.profile_discovery_button)).check(matches(withText("Discovery")));
        onView(withId(R.id.profile_discovery_button)).perform(click());
        onView(withId(R.id.discovery_like_button)).check(matches(withText("Like")));
    }

    public void testDiscoverDislikeButtonClick() throws InterruptedException {
        onView(withId(R.id.login_username_input)).perform(typeText(VALID_USERNAME));
        onView(withId(R.id.login_password_input)).perform(typeText(VALID_PASSWORD));
        onView(withId(R.id.parse_login_button)).perform(click());
        Thread.sleep(2000);
        onView(withId(R.id.profile_discovery_button)).check(matches(withText("Discovery")));
        onView(withId(R.id.profile_discovery_button)).perform(click());
        onView(withId(R.id.discovery_dislike_button)).check(matches(withText("Dislike")));
        onView(withId(R.id.discovery_dislike_button)).perform(click());
        onView(withId(R.id.discovery_dislike_button)).check(matches(withText("Dislike")));
    }

    public void testDiscoverLikeButtonClick() throws InterruptedException {
        onView(withId(R.id.login_username_input)).perform(typeText(VALID_USERNAME));
        onView(withId(R.id.login_password_input)).perform(typeText(VALID_PASSWORD));
        onView(withId(R.id.parse_login_button)).perform(click());
        Thread.sleep(2000);
        onView(withId(R.id.profile_discovery_button)).perform(click());
        onView(withId(R.id.discovery_like_button)).check(matches(withText("Like")));
        onView((withId(R.id.discovery_like_button))).perform(click());
        onView(withId(R.id.discovery_like_button)).check(matches(withText("Like")));
    }

    //Filter test below
    public void testDiscoverFilterGuysGirls() throws InterruptedException {
        onView(withId(R.id.login_username_input)).perform(typeText(VALID_USERNAME));
        onView(withId(R.id.login_password_input)).perform(typeText(VALID_PASSWORD));
        onView(withId(R.id.parse_login_button)).perform(click());
        Thread.sleep(2000);
        onView(withId(R.id.profile_discovery_button)).perform(click());
        onView(withId(R.id.gender_submitted_by_spinner)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is("Guys & Girls"))).perform(click());
        onView(withText("Guys & Girls")).check(matches(withText("Guys & Girls")));
    }
/**
    public void testDiscoverFilterGuys(){

    }

    public void testDiscoverFilterGirls(){

    }

    public void testDiscoverFilterAnyOutfit(){

    }

    public void testDiscoverFilterFullOutfit(){

    }

    public void testDiscoverFilterTopOutfit(){

    }

    public void testDiscoverFilterBottomOutfit(){

    }

    public void testDiscoverFilterShoesOutfit(){

    }

    public void testDiscoverFilterAccessoryOutfit(){

    }*/
}
