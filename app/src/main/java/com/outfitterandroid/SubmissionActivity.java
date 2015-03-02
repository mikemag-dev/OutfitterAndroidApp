package com.outfitterandroid;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RadioGroup;

import java.io.ByteArrayOutputStream;

/**
 * Created by MaguireM on 2/23/15.
 */
public class SubmissionActivity extends Activity {
    private static final String TAG = "SubmissionActivity";

    private String mCurrentPhotoPath;
    private Bitmap mPhotoBitmap;

    private ImageView mSubmissionPhoto;
    private RadioGroup mArticleRadioGroup;
    private RadioGroup mAudienceRadioGroup;
    private CheckBox mIsPriorityCheckBox;
    private Button mSubmitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submission);

        mSubmissionPhoto = (ImageView) findViewById(R.id.submission_photo_view);
        mArticleRadioGroup = (RadioGroup) findViewById(R.id.article_selection_radio_group);
        mAudienceRadioGroup = (RadioGroup) findViewById(R.id.audience_selection_radio_group);
        mIsPriorityCheckBox = (CheckBox) findViewById(R.id.is_priority_submission_check_box);
        mSubmitButton = (Button) findViewById(R.id.submit_submission_button);

        Bitmap photoBitmap = (Bitmap) getIntent()
                .getBundleExtra(ProfileActivity.EXTRA_SUBMISSION_BUNDLE)
                .get("data");
        mSubmissionPhoto.setImageBitmap(photoBitmap);
//        ByteArrayOutputStream stream = new ByteArrayOutputStream();
//        photoBitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
//        byte[] photoByteArray = stream.toByteArray();
    }
}
