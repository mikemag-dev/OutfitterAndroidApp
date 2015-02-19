package com.outfitterandroid;

import android.test.ActivityInstrumentationTestCase2;

import java.util.UUID;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.doesNotExist;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * Created by MaguireM on 2/18/15.
 */
public class DeleteExistingUserTest extends ActivityInstrumentationTestCase2 {
    private static final String TEST_ID = UUID.randomUUID().toString().substring(0,8);
    private static final String NEW_USERNAME = "test_"+TEST_ID+"\n";
    private static final String NEW_PASSWORD = "new_password\n";
    private static final String NEW_EMAIL = TEST_ID + "@gmail.com\n";
    private static final String NEW_NAME = "New Name\n";
    private static final String EXISTING_USERNAME = "old_user\n";
    private static final String EXISTING_PASSWORD = "old_user\n";
    private static final String EXISTING_EMAIL = "old_user@gmail.com\n";
    private static final String EXISTING_NAME = "Old Name\n";

    public DeleteExistingUserTest() {
        super(LoginDispatchActivity.class);
    }

    @Override
    public void setUp() throws Exception {
        super.setUp();
        getActivity();
    }

    public void testSignupAndDeleteNewUser() throws InterruptedException {
        //sign up new account
        onView(withId(R.id.parse_signup_button)).perform(click());
        onView(withId(R.id.signup_username_input)).perform(typeText(NEW_USERNAME));
        onView(withId(R.id.signup_password_input)).perform(typeText(NEW_PASSWORD));
        onView(withId(R.id.signup_confirm_password_input)).perform(typeText(NEW_PASSWORD));
        onView(withId(R.id.signup_email_input)).perform(typeText(NEW_EMAIL));
        onView(withId(R.id.signup_name_input)).perform(typeText(NEW_NAME));

        onView(withId(R.id.create_account)).perform(click());

        assertLoggedIn();
        deleteAndLogout();

        //try to log in with deleted account
        onView(withId(R.id.parse_login_button)).perform(click());
        onView(withId(R.id.login_username_input)).perform(typeText(NEW_USERNAME));
        onView(withId(R.id.login_password_input)).perform(typeText(NEW_PASSWORD));

        assertNotLoggedIn();
    }

    private static void deleteAndLogout() throws InterruptedException {
        onView(withId(R.id.profile_delete_user_button)).perform(click());
        Thread.sleep(1000);
    }

    private static void assertLoggedIn() throws InterruptedException {
        Thread.sleep(1000);
        onView(withId(R.id.profile_logout_button)).check(matches(withText("Logout")));
    }

    private static void assertNotLoggedIn() throws InterruptedException {
        Thread.sleep(1000);
        onView(withId(R.id.profile_logout_button)).check(doesNotExist());
    }

}
