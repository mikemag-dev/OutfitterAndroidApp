package com.outfitterandroid.unittests;

import android.graphics.Bitmap;

import com.outfitterandroid.Submission;

import junit.framework.TestCase;

import java.util.Date;

/**
 * Tests for submission
 */
public class SubmissionTest extends TestCase {

    public void testSubmissionCreation(){
        Submission testSub = new Submission();
        assertNotNull(testSub);
    }

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
        Submission testSub = new Submission();
        int testArticle = 2;
        testSub.setArticle(testArticle);
        assertEquals(testArticle, testSub.getArticle());
    }

    public void testIsPrioritySubmission() throws Exception {
        Submission testSub = new Submission();
        boolean isPriority = true;
        testSub.setIsPrioritySubmission(isPriority);
        assertTrue(testSub.isPrioritySubmission());
    }

    public void testIsToReceiveMaleFeedback() throws Exception {
        Submission testSub = new Submission();
        boolean isMale = false;
        testSub.setToReceiveMaleFeedback(isMale);
        assertFalse(testSub.isToReceiveMaleFeedback());
    }

    public void testIsToReceiveFemaleFeedback() throws Exception {
        Submission testSub = new Submission();
        boolean isFemale = true;
        testSub.setToReceiveFemaleFeedback(isFemale);
        assertTrue(testSub.isToReceiveFemaleFeedback());
    }

    public void testGetImage() throws Exception {
        Submission testSub = new Submission();
        Bitmap testImage = null;
        testSub.setImage(testImage);
        assertEquals(testImage, testSub.getImage());
    }

    public void testGetSubmittedByUser() throws Exception {
        Submission testSub = new Submission();
        String submittedString = "test";
        testSub.setSubmittedByUser(submittedString);
        assertEquals(submittedString, testSub.getSubmittedByUser());
    }
}