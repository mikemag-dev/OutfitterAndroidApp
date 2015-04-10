package com.outfitterandroid.unittests;

import com.outfitterandroid.SubmissionStats;

import junit.framework.TestCase;

public class SubmissionStatsTest extends TestCase {

    public void testConstructor(){
        String testURL = "test_url";
        String testNum_likes = "5";
        String testNum_dislikes = "4";
        SubmissionStats testStats = new SubmissionStats(testURL, testNum_likes, testNum_dislikes);
        assertNotNull(testStats);
    }

}