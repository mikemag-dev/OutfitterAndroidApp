package com.outfitterandroid;

import android.graphics.Bitmap;

import com.parse.ParseFile;

import java.util.ArrayList;
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

    //private ArrayList<Bitmap> mImageList;
    private String mSubmittedByUser;
    private String mGenderOfSubmitter;
    private Bitmap mImage;
    private Date mCreatedAt;
    private int mNumLikes;
    private int mNumDislikes;
    private int mArticle;
    private boolean mIsPrioritySubmission;
    private boolean mToReceiveMaleFeedback = true;
    private boolean mToReceiveFemaleFeedback = true;

    /*public ArrayList<Bitmap> getImageList() {
        return mImageList;
    }

    public void setImageList(ArrayList<Bitmap> mImageList) {
        this.mImageList = mImageList;
    }

    public void addImage(Bitmap mImage) {
        if (null == mImage){
            mImageList = new ArrayList<Bitmap>();
        }
        mImageList.add(mImage);
    }*/

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

    public boolean isPrioritySubmission() {
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

    public static String articleIdToString(int id)
    {
        switch (id){
            case 0: return "Full Outfit";
            case 1: return "Top";
            case 2: return "Bottom";
            case 3: return "Shoes";
            case 4: return "Accessory";
        }

        return "error";
    }

    public boolean isToReceiveFemaleFeedback() {
        return mToReceiveFemaleFeedback;
    }

    public void setToReceiveFemaleFeedback(boolean mToReceiveFemaleFeedback) {
        this.mToReceiveFemaleFeedback = mToReceiveFemaleFeedback;
    }

    public Bitmap getImage() {
        return mImage;
    }

    public void setImage(Bitmap mImage) {
        this.mImage = mImage;
    }

    public String getSubmittedByUser() {
        return mSubmittedByUser;
    }

    public void setSubmittedByUser(String mSubmittedByUser) {
        this.mSubmittedByUser = mSubmittedByUser;
    }

    public String getGenderOfSubmitter() {
        return mGenderOfSubmitter;
    }

    public void setGenderOfSubmitter(String mGenderOfSubmitter) {
        this.mGenderOfSubmitter = mGenderOfSubmitter;
    }
}

