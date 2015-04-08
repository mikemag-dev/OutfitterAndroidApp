package com.outfitterandroid;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v4.view.MotionEventCompat;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;

/**
 * Created by MaguireM on 3/16/15.
 */
public class DiscoveryActivity extends Activity{


    GestureDetectorCompat mGestureDetector;

    ImageView mSubmissionImageView;
    Button mLikeButton;
    Button mDislikeButton;

    ParseObject mCurrentSubmission;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discovery);

        mGestureDetector = new GestureDetectorCompat(this, new SwipeGestureListener());

        mSubmissionImageView = (ImageView) findViewById(R.id.discovery_image_view);
        mLikeButton = (Button) findViewById(R.id.discovery_like_button);
        mDislikeButton = (Button) findViewById(R.id.discovery_dislike_button);

        mLikeButton.setOnClickListener(submitVote());
        mDislikeButton.setOnClickListener(submitVote());
        mSubmissionImageView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                mGestureDetector.onTouchEvent(event);
                return false;
            }
        });
    }
    @Override
    protected void onStart() {
        super.onStart();

        populateWithSubmission();
    }

    private void populateWithSubmission() {
        ParseUser curUser = ParseUser.getCurrentUser();
        ArrayList<String> userVotedOnIDList = (ArrayList<String>) curUser.get("votedOnIDList");
        if (null == userVotedOnIDList) userVotedOnIDList = new ArrayList<>();
        curUser.put("votedOnIDList", userVotedOnIDList);
        curUser.saveInBackground();

        //build query
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Submission");
        query.whereNotContainedIn("objectId", userVotedOnIDList);

        //populate UI elements
        try{
            mCurrentSubmission = query.getFirst();
            byte[] submissionByteArray = mCurrentSubmission.getParseFile("image").getData();
            Bitmap submissionImage = BitmapFactory.decodeByteArray(submissionByteArray, 0, submissionByteArray.length);
            mSubmissionImageView.setImageBitmap(submissionImage);
        }
        catch (ParseException e){
            Toast.makeText(this, "no more submissions to discover", Toast.LENGTH_SHORT).show();
            mCurrentSubmission = null;
            mSubmissionImageView.setImageBitmap(null);
        }
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mGestureDetector.onTouchEvent(event);
        return super.onTouchEvent(event);
    }

    private View.OnClickListener submitVote() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(null == mCurrentSubmission){
                    Toast.makeText(DiscoveryActivity.this, "no more submissions to discover", Toast.LENGTH_SHORT).show();
                }
                else{
                    boolean liked;
                    if(v.getId() == R.id.discovery_like_button){
                        liked = true;
                    }
                    else{
                        liked = false;
                    }
                    User.submitVote(ParseUser.getCurrentUser(), mCurrentSubmission, liked);
                    populateWithSubmission();
                }

            }
        };
    }
    class SwipeGestureListener extends GestureDetector.SimpleOnGestureListener{
        private static final String TAG = "Gestures";


        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            boolean liked;
            if(velocityX > 0){
                liked = true;
                User.submitVote(ParseUser.getCurrentUser(), mCurrentSubmission, liked);
                populateWithSubmission();
                Log.d(TAG, "right swipe detected, submitted like vote");
                return true;
            }
            else if (velocityX < 0){
                liked = false;
                User.submitVote(ParseUser.getCurrentUser(), mCurrentSubmission, liked);
                populateWithSubmission();
                Log.d(TAG, "left swipe detected, submitted dislike vote");
                return true;
            }
            else{
                return super.onFling(e1, e2, velocityX, velocityY);
            }
        }
    }

}
