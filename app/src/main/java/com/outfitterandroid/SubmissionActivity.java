package com.outfitterandroid;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.parse.ParseUser;

import java.util.Date;

/**
 * Created by MaguireM on 2/23/15.
 */
public class SubmissionActivity extends Activity {
    private static final String TAG = "SubmissionActivity";
    private static final String ALREADY_SUBMITTED_PRIORITY_SUBMISSION_TODAY = "Already submitted priority submission today!";

    //private String mCurrentPhotoPath;
    private Bitmap mPhotoBitmap;
    private Submission mCurrentSubmission;
    private ParseUser mCurrentUser;

    private ImageView mSubmissionImageView;
    private RadioGroup mArticleRadioGroup;
    private RadioGroup mAudienceRadioGroup;
    private CheckBox mIsPriorityCheckBox;
    private Button mSubmitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submission);

        mSubmissionImageView = (ImageView) findViewById(R.id.submission_image_view);
        mArticleRadioGroup = (RadioGroup) findViewById(R.id.article_selection_radio_group);
        mAudienceRadioGroup = (RadioGroup) findViewById(R.id.audience_selection_radio_group);
        mIsPriorityCheckBox = (CheckBox) findViewById(R.id.is_priority_submission_check_box);
        mSubmitButton = (Button) findViewById(R.id.submit_submission_button);

        mCurrentUser = ParseUser.getCurrentUser();

        //set image view and checkboxes
        Bitmap capturedImage = (Bitmap) getIntent()
                .getBundleExtra(ProfileActivity.EXTRA_SUBMISSION_BUNDLE)
                .get("data");
        mSubmissionImageView.setImageBitmap(capturedImage);
        mIsPriorityCheckBox.setChecked(!User.hasSubmittedPrioritySubmissionToday(mCurrentUser));

        //build submission
        mCurrentSubmission = new Submission();
        mCurrentSubmission.setSubmittedByUser(mCurrentUser.getObjectId());
        mCurrentSubmission.setIsPrioritySubmission(!User.hasSubmittedPrioritySubmissionToday(mCurrentUser));
        mCurrentSubmission.setImage(capturedImage);

        //set button listeners

        mArticleRadioGroup.setOnCheckedChangeListener(updateSubmissionArticle());
        mAudienceRadioGroup.setOnCheckedChangeListener(updateSubmissionAudience());
        mIsPriorityCheckBox.setOnCheckedChangeListener(updateSubmissionIsPrioritySubmission());
        mSubmitButton.setOnClickListener(submitSubmission());
    }

    private RadioGroup.OnCheckedChangeListener updateSubmissionArticle() {
        return new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                mCurrentSubmission.setArticle(group.indexOfChild(findViewById(checkedId)) - 1);
            }
        };
    }

    private View.OnClickListener submitSubmission() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User.submitSubmission(mCurrentUser, mCurrentSubmission);
                setResult(RESULT_OK);
                finish();
            }
        };
    }

    private CompoundButton.OnCheckedChangeListener updateSubmissionIsPrioritySubmission() {
        return new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Log.d("wtwtf", Boolean.toString(User.hasSubmittedPrioritySubmissionToday(mCurrentUser)));
                if(isChecked && User.hasSubmittedPrioritySubmissionToday(mCurrentUser)){
                    Toast.makeText(SubmissionActivity.this, ALREADY_SUBMITTED_PRIORITY_SUBMISSION_TODAY, Toast.LENGTH_SHORT).show();
                    buttonView.setChecked(false);
                }
                else
                {
                    mCurrentSubmission.setIsPrioritySubmission(isChecked);
                }
            }
        };
    }

    private RadioGroup.OnCheckedChangeListener updateSubmissionAudience() {
        return new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (group.indexOfChild(findViewById(checkedId))){
                    case 1:
                        mCurrentSubmission.setToReceiveMaleFeedback(true);
                        mCurrentSubmission.setToReceiveFemaleFeedback(true);
                        break;
                    case 2:
                        mCurrentSubmission.setToReceiveMaleFeedback(true);
                        mCurrentSubmission.setToReceiveFemaleFeedback(false);
                        break;
                    case 3:
                        mCurrentSubmission.setToReceiveMaleFeedback(false);
                        mCurrentSubmission.setToReceiveFemaleFeedback(true);
                        break;
                }
            }
        };
    }
}
