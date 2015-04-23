package com.outfitterandroid;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.widget.GridView;


import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Kris on 3/3/2015.
 */
public class PortfolioActivity extends Activity{

    private static final String TAG = "PortfolioActivity";


    //TODO
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_portfolio);

        GridView gridview = (GridView) findViewById(R.id.portfolio_view);
        gridview.setAdapter(new GridAdapter(this,getImageFiles(50)));
        //ArrayList<Bitmap> test = getThumbnails(5, new Date());
        //test = null;
    }


    /*****************
    PLEASE READ
            This function seems to successfully query and receive results, however
     the "thumbnails" temporary variable is not getting Bitmaps added to it.

     I did not find out about the Parse query adapter until it was too late but that
     seems to be the better approach.
    *****************/
    private ArrayList<Bitmap> getThumbnails(int max, Date takenBeforeDate){
        final ArrayList<Bitmap> thumbnails = new ArrayList<>();

        ParseQuery<ParseObject> query = ParseQuery.getQuery("Submission");
        //perhaps not get in background? maybe halt the UI thread before this?
        query.whereEqualTo("submittedByUser", ParseUser.getCurrentUser().getObjectId());
        query.whereLessThanOrEqualTo("updatedAt", takenBeforeDate);
        query.orderByDescending("updatedAt");
        query.setLimit(max);
        try {
            Log.d(TAG, "count: " + Integer.toString(query.count()));
        }
        catch (ParseException pe){
            Log.d(TAG, "mulitple image query not working right");
        }
        //do something with local datastore to make it faster
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> parseObjects, ParseException e) {
                if(e != null){
                    Log.d(TAG, "multiple image query not working right");
                }
                byte[] byteArr;
                Bitmap bmp;
                for (ParseObject p : parseObjects){
                    try{
                        byteArr = p.getParseFile("image").getData();
                        thumbnails.add(BitmapFactory.decodeByteArray(byteArr, 0, byteArr.length));
                    }
                    catch (ParseException pe){
                        Log.d(TAG, "single image query not working right");
                    }
                }
            }
        });
        return thumbnails;
    }
    /*****************
     PLEASE READ
     This function seems to successfully query and receive results, however
     the "thumbnails" temporary variable is not getting Bitmaps added to it.

     I did not find out about the Parse query adapter until it was too late but that
     seems to be the better approach.
     *****************/
    private ArrayList<SubmissionStats> getImageFiles(int max){
        final ArrayList<SubmissionStats> imageFiles = new ArrayList<>();

        ParseQuery<ParseObject> query = ParseQuery.getQuery("Submission");
        //perhaps not get in background? maybe halt the UI thread before this?
        query.whereEqualTo("submittedByUser", ParseUser.getCurrentUser().getObjectId());

        query.orderByDescending("updatedAt");
        query.setLimit(max);
        try {
            Log.d(TAG, "count: " + Integer.toString(query.count()));
        }
        catch (ParseException pe){
            Log.d(TAG, "mulitple image query not working right");
        }
        //do something with local datastore to make it faster
        try {
           List<ParseObject> objects= query.find();
           for (ParseObject p: objects)
           {
               String submissionId = p.getObjectId();
               String image_url = p.getParseFile("image").getUrl();
               int dislikes= p.getInt("numDislikes");
               int likes= p.getInt("numLikes");
               Log.d(TAG, image_url);
               SubmissionStats new_stat= new SubmissionStats(submissionId, image_url, likes+"", dislikes+"");
               imageFiles.add(new_stat);
           }
        }
        catch (ParseException e)
        {
            Log.d(TAG, "query cant run");
        }

        return imageFiles;
    }
}
