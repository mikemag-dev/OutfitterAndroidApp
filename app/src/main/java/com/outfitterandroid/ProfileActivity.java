package com.outfitterandroid;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.parse.ParseUser;

import java.io.ByteArrayOutputStream;

public class ProfileActivity extends Activity {

    public static final String EXTRA_SUBMISSION_BUNDLE = "com.outfitterandroid.submission_photo";

    private static final String TAG = "ProfileActivity";
    private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 1;
    private static final int SUBMIT_PHOTO_ACTIVITY_REQUEST_CODE = 2;

    private ParseUser mCurrentUser;

    private Button mLogoutButton;
    private Button mDeleteUserButton;
    private Button mCapturePhotoButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        mLogoutButton = (Button) findViewById(R.id.profile_logout_button);
        mDeleteUserButton = (Button) findViewById(R.id.profile_delete_user_button);
        mCapturePhotoButton = (Button) findViewById(R.id.capture_photo_button);

        mLogoutButton.setOnClickListener(logoutUser());
        mDeleteUserButton.setOnClickListener(logoutAndDeleteUser());
        mCapturePhotoButton.setOnClickListener(launchCapturePhotoActivityForResult());
    }

    private View.OnClickListener launchCapturePhotoActivityForResult() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                //Performing this check is important because if you call startActivityForResult()
                // using an intent that no app can handle, your app will crash. So as long as the
                // result is not null, it's safe to use the intent.
                if(cameraIntent.resolveActivity(getPackageManager()) != null){
                    startActivityForResult(cameraIntent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
                }
            }
        };
    }

    private View.OnClickListener logoutUser() {
        return new View.OnClickListener() {
         @Override
         public void onClick(View v) {
             if(mCurrentUser != null){
                 ParseUser.logOut();
                 mCurrentUser = null;
                 Intent intent = new Intent(ProfileActivity.this, LoginDispatchActivity.class);
                 intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                 startActivity(intent);
                 finish();
             }
             else{
                 Log.d(TAG, "Showing logged in page, but no user logged in");
             }
         }
        };
    }

    private View.OnClickListener logoutAndDeleteUser() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mCurrentUser != null){
                    User.deleteUser(mCurrentUser);
                    ParseUser.logOut();
                    mCurrentUser = null;
                    Intent intent = new Intent(ProfileActivity.this, LoginDispatchActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    finish();
                }
                else{
                    Log.d(TAG, "Showing logged in page, but no user logged in");
                }
            }
        };
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(data == null){
            Log.d(TAG, "Returned to profile with no data");
        }
        switch (requestCode){
            //if successfully captured photo, launch submission activity with photo as an extra
            case CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE:
                if(resultCode == Activity.RESULT_OK){
                    Toast.makeText(this, "Image saved to:\n" +   data.getData(), Toast.LENGTH_LONG).show();
                    Bundle extras = data.getExtras();
//                    Bitmap photoBitmap = (Bitmap) extras.get("data");
//                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
//                    photoBitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
//                    byte[] photoByteArray = stream.toByteArray();
                    Intent submissionIntent = new Intent(ProfileActivity.this, SubmissionActivity.class);
                    submissionIntent.putExtra(ProfileActivity.EXTRA_SUBMISSION_BUNDLE, extras);
                    startActivityForResult(submissionIntent, SUBMIT_PHOTO_ACTIVITY_REQUEST_CODE);
                }
                break;

            //if successfully submitted submission, launch portfolio activity
            case SUBMIT_PHOTO_ACTIVITY_REQUEST_CODE:
                if(resultCode == Activity.RESULT_OK){
                    Toast.makeText(this, "File successfully submitted!", Toast.LENGTH_LONG).show();
                    //launch portfolio activity here
                }
                else{
                    Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    //Performing this check is important because if you call startActivityForResult()
                    // using an intent that no app can handle, your app will crash. So as long as the
                    // result is not null, it's safe to use the intent.
                    if(cameraIntent.resolveActivity(getPackageManager()) != null){
                        startActivityForResult(cameraIntent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
                    }
                }
                break;

        }

    }

    @Override
    protected void onStart() {
        super.onStart();

        mCurrentUser = ParseUser.getCurrentUser();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
