package com.outfitterandroid.unittests;

import com.outfitterandroid.Submission;

import junit.framework.TestCase;

import java.util.Date;

/**
 * Tests for submission
 */
public class SubmissionTest extends TestCase {

    public void testGetCreatedAt() throws Exception {
        Submission testSub = new Submission();
        Date testDate = new Date();
        testDate.setTime(1000000);
        testSub.setCreatedAt(testDate);
        assertEquals(testDate, testSub.getCreatedAt());
    }

    public void testGetNumLikes() throws Exception {
        Submission testSub = new Submission();
        int numLikes = 100;
        testSub.setNumLikes(numLikes);
        assertEquals(numLikes, testSub.getNumLikes());
    }

    public void testGetNumDislikes() throws Exception {
        Submission testSub = new Submission();
        int numDislikes = 100;
        testSub.setNumDislikes(numDislikes);
        assertEquals(numDislikes, testSub.getNumDislikes());
    }

    public void testGetArticle() throws Exception {

    }

    public void testIsPrioritySubmission() throws Exception {

    }

    public void testIsToReceiveMaleFeedback() throws Exception {

    }

    public void testIsToReceiveFemaleFeedback() throws Exception {

    }

    public void testGetImage() throws Exception {

    }

    public void testGetSubmittedByUser() throws Exception {

    }
}