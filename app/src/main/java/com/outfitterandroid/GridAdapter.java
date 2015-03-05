package com.outfitterandroid;

/**
 * Created by angal2 on 3/5/15.
 */
import android.content.Context;
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
    PortfolioActivity myActivity;
    ArrayList<String> urlList;
    private static LayoutInflater inflater=null;
    public GridAdapter(PortfolioActivity mainActivity,ArrayList<String> urls) {
        // TODO Auto-generated constructor stub
        context=mainActivity;
        this.urlList=urls;
        inflater = ( LayoutInflater )context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return urlList.size();
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

    public class Holder
    {
        TextView tv;
        ImageView img;
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View rowView;

        rowView = inflater.inflate(R.layout.grid_element,null );
        ImageView imageView=(ImageView) rowView.findViewById(R.id.imageView1);
        Picasso.with(context)
                .load(urlList.get(position))
                .into(imageView);
        rowView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "You Clicked "+position, Toast.LENGTH_LONG).show();
            }
        });

        return rowView;
    }

}
