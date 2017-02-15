package com.example.sayone.movieapp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by sayone on 2/2/17.
 */

public class ListViewAdapter extends BaseAdapter {

    // Declare Variables
    Context context;
    LayoutInflater inflater;
    ArrayList<HashMap<String, String>> data;
    ImageLoader imageLoader;
    HashMap<String, String> resultp = new HashMap<String, String>();

    public ListViewAdapter(Context context,
                           ArrayList<HashMap<String, String>> movieList) {
        this.context = context;
        data = movieList;
        imageLoader = new ImageLoader(context);
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    public View getView(final int position, View convertView, ViewGroup parent) {
        // Declare Variables
        TextView title;
        TextView overview;
        ImageView poster_path;

        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);


        View itemView = inflater.inflate(R.layout.list_item, parent, false);
        //ViewGroup header = (ViewGroup) inflater.inflate(R.layout.headers,movieList,false);
        //movieList.addHeaderView(header, null, false);
        // Get the position
        resultp = data.get(position);

        // Locate the TextViews in listview_item.xml
        title = (TextView) itemView.findViewById(R.id.title);
        overview = (TextView) itemView.findViewById(R.id.desc);
        // Locate the ImageView in listview_item.xml
        poster_path = (ImageView) itemView.findViewById(R.id.thumbnailImg);

        // Capture position and set results to the TextViews
        title.setText(resultp.get(MainActivity.TITLE));
        overview.setText(resultp.get(MainActivity.OVERVIEW));
        // Capture position and set results to the ImageView
        // Passes poster_path images URL into ImageLoader.class
        imageLoader.DisplayImage(resultp.get(MainActivity.POSTER_PATH), poster_path);
        // Capture ListView item click
        itemView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // Get the position
                resultp = data.get(position);
                Intent intent = new Intent(context, MovieDetailActivity.class);
                // Pass all data title
                intent.putExtra("title", resultp.get(MainActivity.TITLE));
                // Pass all data overview
                intent.putExtra("overview", resultp.get(MainActivity.OVERVIEW));
                // Pass all data poster_path
                intent.putExtra("poster_path", resultp.get(MainActivity.POSTER_PATH));
                // Start MovieDetailActivity Class
                context.startActivity(intent);

            }
        });
        return itemView;
    }
}
