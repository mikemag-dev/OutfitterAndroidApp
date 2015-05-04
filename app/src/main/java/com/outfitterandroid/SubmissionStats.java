package com.outfitterandroid;

/**
 * Created by Mihir on 4/10/15.
 * This is the data container class for the feedback data on a Sumbission
 */
public class SubmissionStats {
    private String submissionId;
    private String url;
    private String num_likes;
    private String num_dislikes;
    public SubmissionStats(String subId, String new_url, String likes, String dislikes)
    {
        this.submissionId = subId;
        url= new_url;
        num_dislikes= dislikes;
        num_likes=likes;
    }
    public String getNumSubmissionId()
    {
        return submissionId;
    }
    public String getUrl()
    {
        return url;
    }
    public String getNum_likes()
    {
        return num_likes;
    }
    public String getNum_dislikes()
    {
        return num_dislikes;
    }

}
