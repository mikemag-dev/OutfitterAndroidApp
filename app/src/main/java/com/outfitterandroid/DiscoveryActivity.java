package com.outfitterandroid;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.view.GestureDetectorCompat;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;

/**
 * Created by MaguireM on 3/16/15.
 * this class controls the Discovery page and collects the user feedback
 */
public class DiscoveryActivity extends Activity{

    GestureDetectorCompat mGestureDetector;

    ImageView mSubmissionImageView;
    TextView mDisplayedSubmissionArticleTextView;
    TextView mDisplayedSubmissionGenderOfSubmitterTextView;
    Button mViewCommentsButton;
    Button mLikeButton;
    Button mDislikeButton;
    Spinner mArticleSpinner;
    Spinner mGenderSubmittedBySpinner;

    ParseObject mCurrentSubmission;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discovery);

        mGestureDetector = new GestureDetectorCompat(this, new SwipeGestureListener());

        mSubmissionImageView = (ImageView) findViewById(R.id.discovery_image_view);
        mDisplayedSubmissionArticleTextView = (TextView) findViewById(R.id.displayed_article_text_view);
        mDisplayedSubmissionGenderOfSubmitterTextView = (TextView) findViewById(R.id.displayed_submission_gender_of_submitter_text_view);
        mViewCommentsButton = (Button) findViewById(R.id.view_comments_button);
        mLikeButton = (Button) findViewById(R.id.discovery_like_button);
        mDislikeButton = (Button) findViewById(R.id.discovery_dislike_button);
        mArticleSpinner = (Spinner) findViewById(R.id.article_spinner);
        mGenderSubmittedBySpinner = (Spinner) findViewById(R.id.gender_submitted_by_spinner);

        mViewCommentsButton.setOnClickListener(viewComments());
        mLikeButton.setOnClickListener(submitButtonVote());
        mDislikeButton.setOnClickListener(submitButtonVote());
        mSubmissionImageView.setOnTouchListener(submitSwipeVote());
        mArticleSpinner.setOnItemSelectedListener(refreshDiscoverySubmission());
        mGenderSubmittedBySpinner.setOnItemSelectedListener(refreshDiscoverySubmission());
    }

    private View.OnClickListener viewComments() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DiscoveryActivity.this, CommentsActivity.class);
                intent.putExtra(CommentsActivity.EXTRA_SUBMISSION_ID, mCurrentSubmission.getObjectId());
                startActivity(intent);
            }
        };
    }

    private AdapterView.OnItemSelectedListener refreshDiscoverySubmission() {
        return new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                populateWithSubmission();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        };
    }

    private View.OnTouchListener submitSwipeVote() {
        return new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                mGestureDetector.onTouchEvent(event);
                return false;
            }
        };
    }

    @Override
    protected void onStart() {
        super.onStart();

        populateWithSubmission();
    }

    private void populateWithSubmission() {
        ParseUser curUser = ParseUser.getCurrentUser();
        ArrayList<String> userVotedOnIDList = (ArrayList<String>) curUser.get("votedOnIDList");
        if (null == userVotedOnIDList){
            userVotedOnIDList = new ArrayList<>();
            curUser.put("votedOnIDList", userVotedOnIDList);
            curUser.saveInBackground();
        }
        int articleFilterSelectedIndex = mArticleSpinner.getSelectedItemPosition();
        int genderOfSubmitterSelectedIndex = mGenderSubmittedBySpinner.getSelectedItemPosition();

        //build query
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Submission");
        query.whereNotContainedIn("objectId", userVotedOnIDList);
        if (articleFilterSelectedIndex != 0){
            query.whereEqualTo("article", articleFilterSelectedIndex-1);
        }
        if (genderOfSubmitterSelectedIndex != 0){
            query.whereEqualTo("genderOfSubmitter", genderOfSubmitterSelectedIndex == 1 ? "male" : "female");
        }


        //populate UI elements
        try{
            mCurrentSubmission = query.getFirst();
            String article = Submission.articleIdToString(mCurrentSubmission.getNumber("article").intValue());
            String genderOfSubmitter = mCurrentSubmission.getString("genderOfSubmitter");
            byte[] submissionByteArray = mCurrentSubmission.getParseFile("image").getData();
            Bitmap submissionImage = BitmapFactory.decodeByteArray(submissionByteArray, 0, submissionByteArray.length);
            mSubmissionImageView.setImageBitmap(submissionImage);
            mDisplayedSubmissionArticleTextView.setText("Article Displayed: " + article);
            mDisplayedSubmissionGenderOfSubmitterTextView.setText("Gender of Submitter: " + genderOfSubmitter);
        }
        catch (ParseException e){
            Toast.makeText(this, "no more submissions to discover", Toast.LENGTH_SHORT).show();
            mCurrentSubmission = null;
            mSubmissionImageView.setImageBitmap(null);
            mDisplayedSubmissionArticleTextView.setText("Article Displayed: ");
            mDisplayedSubmissionGenderOfSubmitterTextView.setText("Gender of Submitter: ");
        }
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mGestureDetector.onTouchEvent(event);
        return super.onTouchEvent(event);
    }

    private View.OnClickListener submitButtonVote() {
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
