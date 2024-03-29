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
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.core.IsAnything.anything;

public class PortfolioActivityTest extends ActivityInstrumentationTestCase2{

    private static final String VALID_USERNAME = "i_am_a_user\n";
    private static final String VALID_PASSWORD = "i_am_a_user\n";

    public PortfolioActivityTest() {
        super(LoginDispatchActivity.class);
    }

    @Override
    public void setUp() throws Exception {
        super.setUp();
        if(ParseUser.getCurrentUser() != null){
            ParseUser.logOut();
        }
        Thread.sleep(2000);
        getActivity();
    }

    public void testPortfolioOpen() throws InterruptedException {
        onView(withId(R.id.login_username_input)).perform(typeText(VALID_USERNAME));
        onView(withId(R.id.login_password_input)).perform(typeText(VALID_PASSWORD));
        onView(withId(R.id.parse_login_button)).perform(click());
        Thread.sleep(2000);
        onView(withId(R.id.profile_portfolio_button)).perform(click());
        assertNotNull(onView(withId(R.id.portfolio_view)));
    }

    //Click and stats tests

    public void testClickFirstPhoto() throws InterruptedException {
        onView(withId(R.id.facebook_login)).perform(click());
        Thread.sleep(2000);
        onView(withId(R.id.profile_portfolio_button)).perform(click());
        onData(anything()).inAdapterView(withId(R.id.portfolio_view)).atPosition(0).
                onChildView(withId(R.id.imageView1)).perform(click());
    }

    public void testClickSecondPhoto() throws InterruptedException {
        onView(withId(R.id.facebook_login)).perform(click());
        Thread.sleep(2000);
        onView(withId(R.id.profile_portfolio_button)).perform(click());
        onData(anything()).inAdapterView(withId(R.id.portfolio_view)).atPosition(1).
                onChildView(withId(R.id.imageView1)).perform(click());
    }

    public void testClickThirdPhoto() throws InterruptedException {
        onView(withId(R.id.facebook_login)).perform(click());
        Thread.sleep(2000);
        onView(withId(R.id.profile_portfolio_button)).perform(click());
        onData(anything()).inAdapterView(withId(R.id.portfolio_view)).atPosition(2).
                onChildView(withId(R.id.imageView1)).perform(click());
    }

    public void testClickFirstAgain() throws InterruptedException {
        onView(withId(R.id.facebook_login)).perform(click());
        Thread.sleep(3000);
        onView(withId(R.id.profile_portfolio_button)).perform(click());
        onData(anything()).inAdapterView(withId(R.id.portfolio_view)).atPosition(0).
                onChildView(withId(R.id.imageView1)).perform(click());
    }

    /*public void testFirstPhotoLikes(){

    }

    public void testSecondPhotoDislikes(){

    }

    public void testThirdPhotoLikesNotNull(){

    }*/
}
