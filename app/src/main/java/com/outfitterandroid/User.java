package com.outfitterandroid;

import android.graphics.Bitmap;
import android.util.Log;

import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by MaguireM on 3/2/15.
 */
public class User {
    private static final String TAG = "User";

    public static void submitVote(ParseUser user, ParseObject submission, boolean liked){
        ArrayList<String> userVotedOnIDList = (ArrayList) user.getList("votedOnIDList");
        userVotedOnIDList.add(submission.getObjectId());
        user.put("votedOnIDList", userVotedOnIDList);

        if (liked){
            submission.put("numLikes", ((int) submission.get("numLikes")) + 1);
        }
        else {
            submission.put("numDislikes", ((int) submission.get("numDislikes")) + 1);
        }

        try{
            submission.saveInBackground();
            user.save();
        }
        catch (ParseException e){

            Log.d(TAG, "vote did not submit");
        }
    }


    /**
     *
     * @param user user to be examined
     * @return whether or not the user has already submitted a priority submission today
     */
    public static boolean hasSubmittedPrioritySubmissionToday(ParseUser user){
        Date lastPrioritySubmissionDate = user.getDate("lastPrioritySubmission");
        Calendar c = new GregorianCalendar();
        c.set(GregorianCalendar.HOUR_OF_DAY, 0);
        c.set(GregorianCalendar.MINUTE, 0);
        c.set(GregorianCalendar.SECOND, 0);
        c.set(GregorianCalendar.MILLISECOND, 0);

        Log.d("in func", Boolean.toString(lastPrioritySubmissionDate == null ?  false : lastPrioritySubmissionDate.compareTo(c.getTime()) <= 0));

        if(lastPrioritySubmissionDate == null) return false;
        if(lastPrioritySubmissionDate.compareTo(c.getTime()) <= 0) return false;
        return true;
    }

    /**
     * submits the submission to the submission table in Parse. Also updates user's list
     * of submission object ids to include this submission
     * @param user user submitting the submission
     * @param mCurrentSubmission submission info to be prepared and submitted
     */
    public static void submitSubmission(ParseUser user, Submission mCurrentSubmission) {
        ParseObject submission = new ParseObject("Submission");
        submission.put("submittedByUser", mCurrentSubmission.getSubmittedByUser());
        submission.put("article", mCurrentSubmission.getArticle());
        submission.put("numLikes", mCurrentSubmission.getNumLikes());
        submission.put("numDislikes", mCurrentSubmission.getNumDislikes());
        submission.put("isPrioritySubmission", mCurrentSubmission.isPrioritySubmission());
        submission.put("toReceiveMaleFeedback", mCurrentSubmission.isToReceiveMaleFeedback());
        submission.put("toReceiveFemaleFeedback", mCurrentSubmission.isToReceiveFemaleFeedback());

        Bitmap bmp = mCurrentSubmission.getImage();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
        bmp.recycle();
        Log.d(TAG, Integer.toString(stream.size()));
        byte[] byteArray = stream.toByteArray();

        ParseFile image = new ParseFile("submissionImage.png", byteArray);
        image.saveInBackground();
        submission.put("image", image);
        try{
            submission.save();
            addToSubmissionsList(user, submission.getObjectId());
            if(mCurrentSubmission.isPrioritySubmission()){
                User.updateLastPrioritySubmission(user);
            }
        }
        catch (ParseException e){
            Log.d(TAG, "submit did not save");
        }
    }

    /**
     * deletes user account
     * @param user user to be deleted
     */
    public static void deleteUser(ParseUser user) {
        user.deleteInBackground();
    }

    /**
     * deletes all associated submissions by the user
     * @param user
     */
    public static void deleteAssociatedData (ParseUser user){
        //to be implemented
    }

    private static void updateLastPrioritySubmission(ParseUser user) {
        String currentUserObjectId = user.getObjectId();
        user.put("lastPrioritySubmission", new Date());
        user.saveInBackground();
    }

    private static void addToSubmissionsList(ParseUser user, String submissionObjectId){
        ArrayList<String> curSubmissionIDList = (ArrayList) user.getList("submissionIDList");
        if(curSubmissionIDList == null) curSubmissionIDList = new ArrayList<>();
        curSubmissionIDList.add(submissionObjectId);
        user.put("submissionIDList", Arrays.asList(curSubmissionIDList));
        user.saveInBackground();
    }
}
