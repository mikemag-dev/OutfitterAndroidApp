package com.outfitterandroid;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;

/**
 * Created by MaguireM on 2/23/15.
 */
public class SubmissionActivity extends Activity {
    private static final String TAG = "SubmissionActivity";

    private String mCurrentPhotoPath;
    private Bitmap mPhotoBitmap;

    private ImageView mSubmissionPhoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submission);

        mSubmissionPhoto = (ImageView) findViewById(R.id.submission_photo_view);

        Bitmap photoBitmap = (Bitmap) getIntent()
                .getBundleExtra(ProfileActivity.EXTRA_SUBMISSION_BUNDLE)
                .get("data");
        mSubmissionPhoto.setImageBitmap(photoBitmap);
//        ByteArrayOutputStream stream = new ByteArrayOutputStream();
//        photoBitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
//        byte[] photoByteArray = stream.toByteArray();
    }
}
