package com.example.sayone.movieapp;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    private static String url = "https://api.themoviedb.org/3/discover/movie?api_key=336fa0a66d0da2e03a9065be059ab769&language=en&sort_by=popularity.desc&include_adult=false&include_video=false&page=1/";
    private String TAG = MainActivity.class.getSimpleName();
    private ProgressDialog pDialog;
    private ListView listView;
    private ListViewAdapter adapter;
    static String TITLE = "title";
    static String OVERVIEW = "overview";
    static String POSTER_PATH = "poster_path";




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    ActivityCompat.requestPermissions(MainActivity.this,
                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                1);

        listView = (ListView) findViewById(R.id.list);
        ViewGroup headerView = (ViewGroup)getLayoutInflater().inflate(R.layout.headers, listView,false);
        // Add header view to the ListView
        listView.addHeaderView(headerView);
        if(BaseApp.movieList.isEmpty()){
            new GetMovies().execute();
        } else {
            adapter = new ListViewAdapter(MainActivity.this, BaseApp.movieList);
            listView.setAdapter(adapter);
        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
        }

        return super.onOptionsItemSelected(item);
    }



    private class GetMovies extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            pDialog = new ProgressDialog(MainActivity.this);
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();

        }

        @Override
        protected Void doInBackground(Void... arg0) {
            HttpHandler sh = new HttpHandler();

            // Making a request to url and getting response
            String jsonStr = sh.makeServiceCall(url);


            Log.e(TAG, "Response from url: " + jsonStr);


            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);

                    // Getting JSON Array node
                    JSONArray results = jsonObj.getJSONArray("results");

                    // looping through All Contacts
                    for (int i = 0; i < results.length(); i++) {
                        JSONObject c = results.getJSONObject(i);
                        String overview = c.getString("overview");
                        String title = c.getString("title");
                        String poster_path = "http://image.tmdb.org/t/p/w92" + c.getString("poster_path");
                        HashMap<String, String> movie = new HashMap<>();
                        // adding each child node to HashMap key => value
                        //  movie.put("id", id);
                        movie.put("title", title);
                        movie.put("overview", overview);
                        movie.put("poster_path", poster_path);
                        //    contact.put("mobile", mobile);

                        // adding contact to contact list
                        BaseApp.movieList.add(movie);
                    }
                } catch (final JSONException e) {
                    Log.e(TAG, "Json parsing error: " + e.getMessage());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),
                                    "Json parsing error: " + e.getMessage(),
                                    Toast.LENGTH_LONG)
                                    .show();
                        }
                    });

                }
            } else {
                Log.e(TAG, "Couldn't get json from server.");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),
                                "Couldn't get json from server. Check LogCat for possible errors!",
                                Toast.LENGTH_LONG)
                                .show();
                    }
                });

            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            listView = (ListView) findViewById(R.id.list);
            adapter = new ListViewAdapter(MainActivity.this, BaseApp.movieList);
            listView.setAdapter(adapter);
            if (pDialog.isShowing())
                pDialog.dismiss();
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {

                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                } else {
                    Toast.makeText(MainActivity.this, "Permission denied to read your External storage", Toast.LENGTH_SHORT).show();
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }
}
