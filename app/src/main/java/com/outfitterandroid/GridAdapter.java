package com.outfitterandroid;

/**
 * Created by angal2 on 3/5/15.
 * This class is the adapter for the Portfolio  page and controls what the user can see
 */
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.parse.ParseImageView;
import com.parse.ParseFile;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import java.util.ArrayList;
import com.squareup.picasso.*;
public class GridAdapter extends BaseAdapter{

    private static final String TAG = "GridAdapter";
    Context context;
    ArrayList<SubmissionStats> dataList;
    private static LayoutInflater inflater=null;
    public GridAdapter(PortfolioActivity mainActivity,ArrayList<SubmissionStats> data) {
        // TODO Auto-generated constructor stub
        context=mainActivity;
        this.dataList=data;
        inflater = ( LayoutInflater )context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return dataList.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View rowView;

        rowView = inflater.inflate(R.layout.grid_element,null );
        ImageView imageView=(ImageView) rowView.findViewById(R.id.imageView1);
        Log.d(TAG, dataList.get(position).getUrl());
        // load the image with picasso image loader, this bypasses local caching
        Picasso.with(context)
                .load(dataList.get(position).getUrl())
                .into(imageView);
        // this is the onClick listener for each of the elements for the grid
        rowView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ViewSubmissionActivity.class);
                String submissionId = dataList.get(position).getNumSubmissionId();
                intent.putExtra(ViewSubmissionActivity.EXTRA_SUBMISSION_ID, submissionId);
                context.startActivity(intent);
            }
        });

        return rowView;
    }

}
