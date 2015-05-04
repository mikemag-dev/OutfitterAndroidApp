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


    //create the portfolio grid
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_portfolio);

        GridView gridview = (GridView) findViewById(R.id.portfolio_view);
        gridview.setAdapter(new GridAdapter(this,getImageFiles(50)));

    }



    /*****************
    Get images in main thread and use image url to load
     *****************/
    private ArrayList<SubmissionStats> getImageFiles(int max){
        final ArrayList<SubmissionStats> imageFiles = new ArrayList<>();

        ParseQuery<ParseObject> query = ParseQuery.getQuery("Submission");
        //do not do this query in background because it can take minutes to show up
        query.whereEqualTo("submittedByUser", ParseUser.getCurrentUser().getObjectId());

        query.orderByDescending("updatedAt");
        query.setLimit(max);
        try {
            Log.d(TAG, "count: " + Integer.toString(query.count()));
        }
        catch (ParseException pe){
            Log.d(TAG, "mulitple image query not working right");
        }
        //do the query in main thread instead of in background
        try {
           List<ParseObject> objects= query.find();
           for (ParseObject p: objects)
           {

               String submissionId = p.getObjectId();
               // get the image url instead of the image because its easier to load without caching
               String image_url = p.getParseFile("image").getUrl();
               int dislikes= p.getInt("numDislikes");
               int likes= p.getInt("numLikes");
               // collect the url and the likes and dislikes and make a Submission stats container for it
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
