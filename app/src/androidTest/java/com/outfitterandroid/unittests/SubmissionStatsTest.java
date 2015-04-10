package com.outfitterandroid.unittests;

import com.outfitterandroid.SubmissionStats;

import junit.framework.TestCase;

public class SubmissionStatsTest extends TestCase {

    public void testConstructor(){
        String testURL = "test_url";
        String testNumLikes = "5";
        String testNumDislikes = "4";
        SubmissionStats testStats = new SubmissionStats(testURL, testNumLikes, testNumDislikes);
        assertNotNull(testStats);
    }

    public void testGetUrl(){
        String testURL = "test_url";
        String testNumLikes = "5";
        String testNumDislikes = "4";
        SubmissionStats testStats = new SubmissionStats(testURL, testNumLikes, testNumDislikes);
        String resultUrl = testStats.getUrl();
        assertEquals(testURL, resultUrl);
    }

    public void testGetNumLikes(){
        String testURL = "test_url";
        String testNumLikes = "5";
        String testNumDislikes = "4";
        SubmissionStats testStats = new SubmissionStats(testURL, testNumLikes, testNumDislikes);
        String resultNumLikes = testStats.getNum_likes();
        assertEquals(testNumLikes, resultNumLikes);
    }

    public void testGetNumDislikes(){
        String testURL = "test_url";
        String testNumLikes = "5";
        String testNumDislikes = "4";
        SubmissionStats testStats = new SubmissionStats(testURL, testNumLikes, testNumDislikes);
        String resultNumDislikes = testStats.getNum_dislikes();
        assertEquals(testNumDislikes, resultNumDislikes);
    }

    public void testUrlNotNull(){
        String testURL = "test_url";
        String testNum_likes = "5";
        String testNum_dislikes = "4";
        SubmissionStats testStats = new SubmissionStats(testURL, testNum_likes, testNum_dislikes);
        String resultUrl = testStats.getUrl();
        assertNotNull(resultUrl);
    }

}