package com.outfitterandroid;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.view.GestureDetectorCompat;
import android.view.View;
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
 * Created by MaguireM on 4/23/15.
 * See the sumbmisson details
 */
public class ViewSubmissionActivity extends Activity {
    public static final String EXTRA_SUBMISSION_ID = "com.android.submissionid";
    ImageView mSubmissionImageView;
    TextView mDisplayedSubmissionArticleTextView;
    TextView mDisplayedSubmissionGenderOfSubmitterTextView;
    Button mViewCommentsButton;


    ParseObject mCurrentSubmission;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_submission);

        mSubmissionImageView = (ImageView) findViewById(R.id.discovery_image_view);
        mDisplayedSubmissionArticleTextView = (TextView) findViewById(R.id.displayed_article_text_view);
        mDisplayedSubmissionGenderOfSubmitterTextView = (TextView) findViewById(R.id.displayed_submission_gender_of_submitter_text_view);
        mViewCommentsButton = (Button) findViewById(R.id.view_comments_button);

        //hoook up buttons
        mViewCommentsButton.setOnClickListener(viewComments());

        //populate UI elements
        try {
            String currentSubmissionId = getIntent().getStringExtra(EXTRA_SUBMISSION_ID);
            ParseQuery<ParseObject> query = ParseQuery.getQuery("Submission");
            query.get(currentSubmissionId);
            mCurrentSubmission = query.getFirst();
            String article = Submission.articleIdToString(mCurrentSubmission.getNumber("article").intValue());
            String genderOfSubmitter = mCurrentSubmission.getString("genderOfSubmitter");
            byte[] submissionByteArray = mCurrentSubmission.getParseFile("image").getData();
            Bitmap submissionImage = BitmapFactory.decodeByteArray(submissionByteArray, 0, submissionByteArray.length);
            mSubmissionImageView.setImageBitmap(submissionImage);
            mDisplayedSubmissionArticleTextView.setText("Article Displayed: " + article);
            mDisplayedSubmissionGenderOfSubmitterTextView.setText("Gender of Submitter: " + genderOfSubmitter);
        } catch (ParseException e) {
            Toast.makeText(this, "no more submissions to discover", Toast.LENGTH_SHORT).show();
            mCurrentSubmission = null;
            mSubmissionImageView.setImageBitmap(null);
            mDisplayedSubmissionArticleTextView.setText("Article Displayed: ");
            mDisplayedSubmissionGenderOfSubmitterTextView.setText("Gender of Submitter: ");
        }

    }

    private View.OnClickListener viewComments() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ViewSubmissionActivity.this, CommentsActivity.class);
                intent.putExtra(CommentsActivity.EXTRA_SUBMISSION_ID, mCurrentSubmission.getObjectId());
                startActivity(intent);
            }
        };
    }
}


