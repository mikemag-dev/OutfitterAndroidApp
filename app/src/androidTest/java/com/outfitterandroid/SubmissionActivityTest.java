package com.outfitterandroid;

import android.test.ActivityInstrumentationTestCase2;

import com.parse.ParseUser;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

/**
 * Created by Kris on 4/5/2015.
 */

public class SubmissionActivityTest extends ActivityInstrumentationTestCase2 {

    private static final String VALID_USERNAME = "i_am_a_user\n";
    private static final String VALID_PASSWORD = "i_am_a_user\n";

    public SubmissionActivityTest() {
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

    //TODO Finish Tests
    public void testTakePictureOpen() throws InterruptedException {
        onView(withId(R.id.login_username_input)).perform(typeText(VALID_USERNAME));
        onView(withId(R.id.login_password_input)).perform(typeText(VALID_PASSWORD));
        onView(withId(R.id.parse_login_button)).perform(click());
        Thread.sleep(2000);
        onView(withId(R.id.profile_capture_image_button)).perform(click());
    }
}
