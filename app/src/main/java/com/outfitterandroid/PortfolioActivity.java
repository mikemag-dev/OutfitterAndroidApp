package com.outfitterandroid;

import android.app.Activity;
import android.os.Bundle;
import android.widget.GridView;


import com.parse.ParseUser;

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
    }
}
