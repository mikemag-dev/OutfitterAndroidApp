package com.outfitterandroid;

import android.test.ActivityInstrumentationTestCase2;

import com.parse.ParseUser;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.core.IsAnything.anything;

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
        Thread.sleep(2000);
        if(ParseUser.getCurrentUser() != null){
            ParseUser.logOut();
            Thread.sleep(2000);
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
        Thread.sleep(2000);
        onView(withId(R.id.view_comments_button)).check(matches(withText("View Comments")));
        Thread.sleep(2000);
    }

    public void testDiscoveryViewCommentsOpen() throws InterruptedException {
        Thread.sleep(2000);
        onView(withId(R.id.login_username_input)).perform(typeText(VALID_USERNAME));
        onView(withId(R.id.login_password_input)).perform(typeText(VALID_PASSWORD));
        onView(withId(R.id.parse_login_button)).perform(click());
        Thread.sleep(2000);
        onView(withId(R.id.profile_discovery_button)).check(matches(withText("Discovery")));
        onView(withId(R.id.profile_discovery_button)).perform(click());
        Thread.sleep(2000);
        onView(withId(R.id.view_comments_button)).check(matches(withText("View Comments")));
        onView(withId(R.id.view_comments_button)).perform(click());
        Thread.sleep(2000);
    }

    public void testDiscoveryItemComments() throws InterruptedException {
        Thread.sleep(2000);
        onView(withId(R.id.login_username_input)).perform(typeText(VALID_USERNAME));
        onView(withId(R.id.login_password_input)).perform(typeText(VALID_PASSWORD));
        onView(withId(R.id.parse_login_button)).perform(click());
        Thread.sleep(2000);
        onView(withId(R.id.profile_discovery_button)).check(matches(withText("Discovery")));
        onView(withId(R.id.profile_discovery_button)).perform(click());
        onView(withId(R.id.view_comments_button)).check(matches(withText("View Comments")));
        onView(withId(R.id.view_comments_button)).perform(click());
        Thread.sleep(2000);
        onView(withId(R.id.add_comment_button)).check(matches(withText("add comment")));
        Thread.sleep(2000);
    }

    public void testWriteComment() throws InterruptedException {
        onView(withId(R.id.login_username_input)).perform(typeText(VALID_USERNAME));
        onView(withId(R.id.login_password_input)).perform(typeText(VALID_PASSWORD));
        onView(withId(R.id.parse_login_button)).perform(click());
        Thread.sleep(2000);
        onView(withId(R.id.profile_discovery_button)).check(matches(withText("Discovery")));
        onView(withId(R.id.profile_discovery_button)).perform(click());
        Thread.sleep(2000);
        onView(withId(R.id.view_comments_button)).check(matches(withText("View Comments")));
        onView(withId(R.id.view_comments_button)).perform(click());
        onView(withId(R.id.add_comment_button)).check(matches(withText("add comment")));
        onView(withId(R.id.comment_edit_text)).perform(typeText("test"));
    }

    public void testDiscoveryViewComments2() throws InterruptedException {
        onView(withId(R.id.login_username_input)).perform(typeText(VALID_USERNAME));
        onView(withId(R.id.login_password_input)).perform(typeText(VALID_PASSWORD));
        onView(withId(R.id.parse_login_button)).perform(click());
        Thread.sleep(2000);
        onView(withId(R.id.profile_discovery_button)).check(matches(withText("Discovery")));
        onView(withId(R.id.profile_discovery_button)).perform(click());
        Thread.sleep(2000);
        onView(withId(R.id.view_comments_button)).check(matches(withText("View Comments")));
        Thread.sleep(2000);
    }

    public void testAddComment() throws InterruptedException {
        onView(withId(R.id.login_username_input)).perform(typeText(VALID_USERNAME));
        onView(withId(R.id.login_password_input)).perform(typeText(VALID_PASSWORD));
        onView(withId(R.id.parse_login_button)).perform(click());
        Thread.sleep(2000);
        onView(withId(R.id.profile_discovery_button)).check(matches(withText("Discovery")));
        onView(withId(R.id.profile_discovery_button)).perform(click());
        Thread.sleep(2000);
        onView(withId(R.id.view_comments_button)).check(matches(withText("View Comments")));
        onView(withId(R.id.view_comments_button)).perform(click());
        Thread.sleep(2000);
        onView(withId(R.id.add_comment_button)).check(matches(withText("add comment")));
        onView(withId(R.id.comment_edit_text)).perform(typeText("test"));
        onView(withId(R.id.add_comment_button)).perform(click());
        Thread.sleep(2000);
    }

    public void testSeeNewComment() throws InterruptedException {
        onView(withId(R.id.login_username_input)).perform(typeText(VALID_USERNAME));
        onView(withId(R.id.login_password_input)).perform(typeText(VALID_PASSWORD));
        onView(withId(R.id.parse_login_button)).perform(click());
        Thread.sleep(2000);
        onView(withId(R.id.profile_discovery_button)).check(matches(withText("Discovery")));
        onView(withId(R.id.profile_discovery_button)).perform(click());
        Thread.sleep(2000);
        onView(withId(R.id.view_comments_button)).check(matches(withText("View Comments")));
        onView(withId(R.id.view_comments_button)).perform(click());
        Thread.sleep(2000);
        onView(withId(R.id.add_comment_button)).check(matches(withText("add comment")));
        onView(withId(R.id.comment_edit_text)).perform(typeText("test"));
        onView(withId(R.id.add_comment_button)).perform(click());
        Thread.sleep(2000);
        onView(withText("test")).check(matches(withText("test")));
        Thread.sleep(2000);
    }

    public void testAddComment2() throws InterruptedException {
        onView(withId(R.id.login_username_input)).perform(typeText(VALID_USERNAME));
        onView(withId(R.id.login_password_input)).perform(typeText(VALID_PASSWORD));
        onView(withId(R.id.parse_login_button)).perform(click());
        Thread.sleep(2000);
        onView(withId(R.id.profile_discovery_button)).check(matches(withText("Discovery")));
        onView(withId(R.id.profile_discovery_button)).perform(click());
        Thread.sleep(2000);
        onView(withId(R.id.view_comments_button)).check(matches(withText("View Comments")));
        onView(withId(R.id.view_comments_button)).perform(click());
        Thread.sleep(2000);
        onView(withId(R.id.add_comment_button)).check(matches(withText("add comment")));
        onView(withId(R.id.comment_edit_text)).perform(typeText("test2"));
        onView(withId(R.id.add_comment_button)).perform(click());
        Thread.sleep(2000);
    }

    public void testViewUpvote() throws InterruptedException {
        onView(withId(R.id.login_username_input)).perform(typeText(VALID_USERNAME));
        onView(withId(R.id.login_password_input)).perform(typeText(VALID_PASSWORD));
        onView(withId(R.id.parse_login_button)).perform(click());
        Thread.sleep(2000);
        onView(withId(R.id.profile_discovery_button)).check(matches(withText("Discovery")));
        onView(withId(R.id.profile_discovery_button)).perform(click());
        Thread.sleep(2000);
        onView(withId(R.id.view_comments_button)).check(matches(withText("View Comments")));
        onView(withId(R.id.view_comments_button)).perform(click());
        Thread.sleep(2000);
        onView(withId(R.id.add_comment_button)).check(matches(withText("add comment")));
        onView(withId(R.id.num_upvotes_text_view)).check(matches(withText("2")));
    }

    public void testPortfolioViewComments() throws InterruptedException {
        onView(withId(R.id.facebook_login)).perform(click());
        Thread.sleep(3000);
        onView(withId(R.id.profile_portfolio_button)).perform(click());
        Thread.sleep(3000);
        onData(anything()).inAdapterView(withId(R.id.portfolio_view)).atPosition(0).
                onChildView(withId(R.id.imageView1)).perform(click());
        Thread.sleep(2000);
        onView(withId(R.id.view_comments_button)).check(matches(withText("View Comments")));
    }

    public void testPortfolioOpenComments() throws InterruptedException {
        onView(withId(R.id.facebook_login)).perform(click());
        Thread.sleep(3000);
        onView(withId(R.id.profile_portfolio_button)).perform(click());
        Thread.sleep(3000);
        onData(anything()).inAdapterView(withId(R.id.portfolio_view)).atPosition(0).
                onChildView(withId(R.id.imageView1)).perform(click());
        Thread.sleep(2000);
        onView(withId(R.id.view_comments_button)).check(matches(withText("View Comments")));
        onView(withId(R.id.view_comments_button)).perform(click());
        Thread.sleep(2000);
    }

    public void testPortfolioViewCommentsList() throws InterruptedException {
        onView(withId(R.id.facebook_login)).perform(click());
        Thread.sleep(3000);
        onView(withId(R.id.profile_portfolio_button)).perform(click());
        Thread.sleep(3000);
        onData(anything()).inAdapterView(withId(R.id.portfolio_view)).atPosition(0).
                onChildView(withId(R.id.imageView1)).perform(click());
        Thread.sleep(2000);
        onView(withId(R.id.view_comments_button)).check(matches(withText("View Comments")));
        onView(withId(R.id.view_comments_button)).perform(click());
        Thread.sleep(2000);
        onView(withId(R.id.add_comment_button)).check(matches(withText("add comment")));
    }

    public void testPortfolioWriteComments() throws InterruptedException {
        onView(withId(R.id.facebook_login)).perform(click());
        Thread.sleep(3000);
        onView(withId(R.id.profile_portfolio_button)).perform(click());
        Thread.sleep(3000);
        onData(anything()).inAdapterView(withId(R.id.portfolio_view)).atPosition(0).
                onChildView(withId(R.id.imageView1)).perform(click());
        Thread.sleep(2000);
        onView(withId(R.id.view_comments_button)).check(matches(withText("View Comments")));
        onView(withId(R.id.view_comments_button)).perform(click());
        Thread.sleep(2000);
        onView(withId(R.id.add_comment_button)).check(matches(withText("add comment")));
        onView(withId(R.id.comment_edit_text)).perform(typeText("test"));

    }

    public void testPortfolioAddComments() throws InterruptedException {
        onView(withId(R.id.facebook_login)).perform(click());
        Thread.sleep(3000);
        onView(withId(R.id.profile_portfolio_button)).perform(click());
        Thread.sleep(3000);
        onData(anything()).inAdapterView(withId(R.id.portfolio_view)).atPosition(0).
                onChildView(withId(R.id.imageView1)).perform(click());
        Thread.sleep(2000);
        onView(withId(R.id.view_comments_button)).check(matches(withText("View Comments")));
        onView(withId(R.id.view_comments_button)).perform(click());
        Thread.sleep(2000);
        onView(withId(R.id.add_comment_button)).check(matches(withText("add comment")));
        onView(withId(R.id.comment_edit_text)).perform(typeText("test"));
        onView(withId(R.id.add_comment_button)).perform(click());
        Thread.sleep(2000);
    }

}
