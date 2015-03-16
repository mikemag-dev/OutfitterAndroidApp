package com.outfitterandroid;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
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

    ImageView mSubmissionImageView;
    Button mLikeButton;
    Button mDislikeButton;

    ParseObject mCurrentSubmission;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discovery);

        mSubmissionImageView = (ImageView) findViewById(R.id.discovery_image_view);
        mLikeButton = (Button) findViewById(R.id.discovery_like_button);
        mDislikeButton = (Button) findViewById(R.id.discovery_dislike_button);

        mLikeButton.setOnClickListener(submitVote());
        mDislikeButton.setOnClickListener(submitVote());
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
}
