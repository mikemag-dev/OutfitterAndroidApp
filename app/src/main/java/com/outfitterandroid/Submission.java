package com.outfitterandroid;

import com.parse.ParseFile;

import java.util.Date;

/**
 * Created by MaguireM on 2/23/15.
 */
public class Submission {

    public final static int OUTFIT = 0;
    public final static int ACCESSORY = 1;
    public final static int TOP = 2;
    public final static int BOTTOM = 3;
    public final static int SHOES = 4;

    private String mUsername;
    private ParseFile mImage;
    private Date mCreatedAt;
    private int mNumLikes;
    private int mNumDislikes;
    private int mArticle;
    private boolean mIsPrioritySubmission;
    private boolean mToReceiveMaleFeedback;
    private boolean mToReceiveFemaleFeedback;

    public String getUsername() {
        return mUsername;
    }

    public void setUsername(String mUsername) {
        this.mUsername = mUsername;
    }

    public ParseFile getImage() {
        return mImage;
    }

    public void setImage(ParseFile mImage) {
        this.mImage = mImage;
    }

    public Date getCreatedAt() {
        return mCreatedAt;
    }

    public void setCreatedAt(Date mCreatedAt) {
        this.mCreatedAt = mCreatedAt;
    }

    public int getNumLikes() {
        return mNumLikes;
    }

    public void setNumLikes(int mNumLikes) {
        this.mNumLikes = mNumLikes;
    }

    public int getNumDislikes() {
        return mNumDislikes;
    }

    public void setNumDislikes(int mNumDislikes) {
        this.mNumDislikes = mNumDislikes;
    }

    public int getArticle() {
        return mArticle;
    }

    public void setArticle(int mArticle) {
        this.mArticle = mArticle;
    }

    public boolean isIsPrioritySubmission() {
        return mIsPrioritySubmission;
    }

    public void setIsPrioritySubmission(boolean mIsPrioritySubmission) {
        this.mIsPrioritySubmission = mIsPrioritySubmission;
    }

    public boolean isToReceiveMaleFeedback() {
        return mToReceiveMaleFeedback;
    }

    public void setToReceiveMaleFeedback(boolean mToReceiveMaleFeedback) {
        this.mToReceiveMaleFeedback = mToReceiveMaleFeedback;
    }

    public boolean isToReceiveFemaleFeedback() {
        return mToReceiveFemaleFeedback;
    }

    public void setToReceiveFemaleFeedback(boolean mToReceiveFemaleFeedback) {
        this.mToReceiveFemaleFeedback = mToReceiveFemaleFeedback;
    }

}

