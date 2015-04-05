package com.outfitterandroid.unittests;

import com.outfitterandroid.User;
import com.parse.ParseException;
import com.parse.ParseUser;

import junit.framework.TestCase;

public class UserTest extends TestCase {
    /**
     * Tests priority submission setting
     */
    public void testPrioritySubmission() throws ParseException {
        ParseUser user = ParseUser.logIn("old_user", "old_user");
        boolean isPriority = User.hasSubmittedPrioritySubmissionToday(user);
        assertFalse(isPriority);
    }

}