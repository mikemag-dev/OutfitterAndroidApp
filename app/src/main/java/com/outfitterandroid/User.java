package com.outfitterandroid;

import android.graphics.Bitmap;

import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.io.ByteArrayOutputStream;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by MaguireM on 3/2/15.
 */
public class User {

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

        if(null == lastPrioritySubmissionDate){
            return true;
        }
        else
        {
            if (lastPrioritySubmissionDate.compareTo(c.getTime()) < 0){
                return true;
            }
            else {
                return false;
            }
        }
    }

    public static void submitSubmission(ParseUser user, Submission mCurrentSubmission) {
        ParseObject submission = new ParseObject("Submission");
        submission.put("article", mCurrentSubmission.getArticle());
        submission.put("numLikes", mCurrentSubmission.getNumLikes());
        submission.put("numDislikes", mCurrentSubmission.getNumDislikes());
        submission.put("isPrioritySubmission", mCurrentSubmission.isPrioritySubmission());
        submission.put("toReceiveMaleFeedback", mCurrentSubmission.isToReceiveMaleFeedback());
        submission.put("toReceiveFemaleFeedback", mCurrentSubmission.isToReceiveFemaleFeedback());

        Bitmap bmp = mCurrentSubmission.getImage();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();

        ParseFile image = new ParseFile("submissionImage.png", byteArray);
        image.saveInBackground();
        submission.put("image", image);
        submission.saveInBackground();

        if(mCurrentSubmission.isPrioritySubmission()){
            User.updateLastPrioritySubmission(user);
        }
    }

    private static void updateLastPrioritySubmission(ParseUser user) {
        String currentUserObjectId = user.getObjectId();

        ParseQuery<ParseObject> query = ParseQuery.getQuery("User");


        query.getInBackground(currentUserObjectId, new GetCallback<ParseObject>() {
            @Override
            public void done(ParseObject parseObject, ParseException e) {
                parseObject.put("lastPrioritySubmission", new Date());
                parseObject.saveInBackground();
            }
        });
    }

    /**
     * removes user and all submissions by user
     * @param user user to be deleted
     */
    public static void delete(ParseUser user) {
        //to be implemented
        user.deleteInBackground();
    }
}
