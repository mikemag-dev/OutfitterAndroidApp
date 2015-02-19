package com.outfitterandroid;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.parse.ParseUser;
import com.parse.ui.ParseLoginBuilder;
import com.parse.ui.ParseLoginDispatchActivity;


public class ProfileActivity extends Activity {

    private static final String TAG = "ProfileActivity";

    private ParseUser mCurrentUser;
    private Button mLogoutButton;
    private Button mDeleteUserButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        mLogoutButton = (Button) findViewById(R.id.profile_logout_button);
        mDeleteUserButton = (Button) findViewById(R.id.profile_delete_user_button);

        mLogoutButton.setOnClickListener(new View.OnClickListener() {
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
        });

        mDeleteUserButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mCurrentUser != null){
                    mCurrentUser.deleteInBackground();
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
        });
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
