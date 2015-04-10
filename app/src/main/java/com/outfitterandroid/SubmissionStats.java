package com.outfitterandroid;

/**
 * Created by mihir on 4/10/15.
 */
public class SubmissionStats {
    private String url;
    private String num_likes;
    private String num_dislikes;
    public SubmissionStats(String new_url, String likes, String dislikes)
    {
        url= new_url;
        num_dislikes= dislikes;
        num_likes=likes;
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
