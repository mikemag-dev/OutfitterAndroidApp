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
 * Created by Kris on 4/24/2015.
 */

public class CommentsActivityTest extends ActivityInstrumentationTestCase2 {

    private static final String VALID_USERNAME = "i_am_a_user\n";
    private static final String VALID_PASSWORD = "i_am_a_user\n";

    public CommentsActivityTest() {
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

    public void testDiscoveryViewComments() throws InterruptedException {
        onView(withId(R.id.login_username_input)).perform(typeText(VALID_USERNAME));
        onView(withId(R.id.login_password_input)).perform(typeText(VALID_PASSWORD));
        onView(withId(R.id.parse_login_button)).perform(click());
        Thread.sleep(2000);
        onView(withId(R.id.profile_discovery_button)).check(matches(withText("Discovery")));
        onView(withId(R.id.profile_discovery_button)).perform(click());
        onView(withId(R.id.view_comments_button)).check(matches(withText("View Comments")));
    }

    public void testDiscoveryViewCommentsOpen() throws InterruptedException {
        onView(withId(R.id.login_username_input)).perform(typeText(VALID_USERNAME));
        onView(withId(R.id.login_password_input)).perform(typeText(VALID_PASSWORD));
        onView(withId(R.id.parse_login_button)).perform(click());
        Thread.sleep(2000);
        onView(withId(R.id.profile_discovery_button)).check(matches(withText("Discovery")));
        onView(withId(R.id.profile_discovery_button)).perform(click());
        onView(withId(R.id.view_comments_button)).check(matches(withText("View Comments")));
        onView(withId(R.id.view_comments_button)).perform(click());
    }

    public void testDiscoveryItemComments() throws InterruptedException {
        onView(withId(R.id.login_username_input)).perform(typeText(VALID_USERNAME));
        onView(withId(R.id.login_password_input)).perform(typeText(VALID_PASSWORD));
        onView(withId(R.id.parse_login_button)).perform(click());
        Thread.sleep(2000);
        onView(withId(R.id.profile_discovery_button)).check(matches(withText("Discovery")));
        onView(withId(R.id.profile_discovery_button)).perform(click());
        onView(withId(R.id.view_comments_button)).check(matches(withText("View Comments")));
        onView(withId(R.id.view_comments_button)).perform(click());
        onView(withId(R.id.add_comment_button)).check(matches(withText("add comment")));
    }

    public void testWriteComment(){

    }

    public void testAddComment(){

    }

    public void testSeeNewComment(){

    }

    public void testClickUpvote(){

    }

    public void testViewUpvote(){

    }

}
