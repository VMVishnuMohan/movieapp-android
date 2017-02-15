package com.example.sayone.movieapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by sayone on 2/2/17.
 */
public class MovieDetailActivity extends AppCompatActivity {
    // Declare Variables
    String title;
    String overview;
    String poster_path;
    String position;
    ImageLoader imageLoader = new ImageLoader(this);
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Get the view from singleitemview.xml
        setContentView(R.layout.singleitemview);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //setSupportActionBar(toolbar);
        //Your toolbar is now an action bar and you can use it like you always do, for example:
        // getSupportActionBar().setDisplayHomeAsUpEnabled(true);*/
        Intent i = getIntent();
        // Get the result of rank
        title = i.getStringExtra("title");
        // Get the result of country
        overview = i.getStringExtra("overview");
        // Get the result of population
        // Get the result of flag
        poster_path = i.getStringExtra("poster_path");
        // Locate the TextViews in singleitemview.xml
        TextView txttitle = (TextView) findViewById(R.id.title);
        TextView txtoverview = (TextView) findViewById(R.id.desc);
        // Locate the ImageView in singleitemview.xml
        ImageView imgposter = (ImageView) findViewById(R.id.thumbnailImg);
        // Set results to the TextViews
        txttitle.setText(title);
        txtoverview.setText(overview);
        // Capture position and set results to the ImageView
        // Passes flag images URL into ImageLoader.class
        imageLoader.DisplayImage(poster_path, imgposter);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
            return super.onOptionsItemSelected(item);
        }

        return super.onOptionsItemSelected(item);
    }
}
