package com.example.mtecgwa_jr.movie_app;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.mtecgwa_jr.movie_app.NetworkUtil.RequestMovie;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;

public class MovieActivity extends AppCompatActivity {

    private String movieId;
    private TextView mOverView , mTitle , mReleaseDate , mRatings;

    private ImageView mMovieCover;

    public String baseMovieCoverUrl = "https://image.tmdb.org/t/p/w500";

    private ProgressBar mProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);


        mOverView = (TextView) findViewById(R.id.overview);

        mMovieCover = (ImageView) findViewById(R.id.cover);

        mTitle = (TextView) findViewById(R.id.movie_title);

        mReleaseDate = (TextView) findViewById(R.id.release_date);

        mRatings = (TextView) findViewById(R.id.ratings);

        mProgress = (ProgressBar) findViewById(R.id.progress);


        Intent intent = getIntent();

        movieId = intent.getStringExtra("movieId");


        MovieTask movieAsync = new MovieTask();
        movieAsync.execute(movieId);




    }



    public void showProgress()
    {
        mProgress.setVisibility(View.VISIBLE);
    }
    public void hideProgress()
    {
        mProgress.setVisibility(View.INVISIBLE);
    }

    public class MovieTask extends AsyncTask<String , Void , String>
    {
        @Override
        protected void onPreExecute() {
            showProgress();
        }

        @Override
        protected String doInBackground(String... params) {

            String movieId = params[0];

            URL url = RequestMovie.buildUrl(movieId);

            String jsonResults = null;

            try{
                jsonResults = RequestMovie.getMovieJson(url);

            }
            catch(IOException e)
            {
                e.printStackTrace();
            }



            return jsonResults;
        }

        @Override
        protected void onPostExecute(String s)
        {
            hideProgress();

            String movieName = null;
            String overview = null;
            String imageFile = null;
            String releaseDate = null;
            double userRate = 0;
            try{
                JSONObject movie = new JSONObject(s);
                movieName = movie.getString("original_title");
                overview = movie.getString("overview");
                imageFile = movie.getString("poster_path");
                releaseDate = movie.getString("release_date");

                userRate = movie.getDouble("vote_average");

            }
            catch(JSONException e)
            {
                e.printStackTrace();
            }

            mTitle.setText(movieName);
            mReleaseDate.append(releaseDate);
            mOverView.setText(overview);
            mRatings.setText(userRate+"/10");
            String movieCoverFullUrl = baseMovieCoverUrl+imageFile;
            Picasso.with(MovieActivity.this)
                    .load(movieCoverFullUrl)
                    .placeholder(R.drawable.movie)
                    .into(mMovieCover);


        }
    }
}
