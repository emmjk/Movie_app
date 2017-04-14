package com.example.mtecgwa_jr.movie_app;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.mtecgwa_jr.movie_app.NetworkUtil.Networking;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {





    private static final String QUERY_POPULAR_URL = "https://api.themoviedb.org/3/movie/popular";
    private static final String QUERY_TOP_RATED_URL = " https://api.themoviedb.org/3/movie/top_rated";

    private String queryTypeCheck = "popular";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MovieQueryTask movieAsync = new MovieQueryTask();
        movieAsync.execute(QUERY_POPULAR_URL);



    }



    public class MovieQueryTask extends AsyncTask<String , Movie , Void>
    {
        private RecyclerView recyclerView;
        private MovieAdapter movieAdapter;
        private ArrayList<Movie> movieList = new ArrayList<>();
        private ProgressBar progressBar;

        public void showProgressBar()
        {
            progressBar.setVisibility(View.VISIBLE);
        }
        public void hidePrigressBar()
        {
            progressBar.setVisibility(View.INVISIBLE);
        }


        @Override
        protected void onPreExecute() {

            recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
            movieAdapter = new MovieAdapter(movieList , MainActivity.this);
            recyclerView.setHasFixedSize(true);
            RecyclerView.LayoutManager layoutManager = new GridLayoutManager(MainActivity.this , 2);

            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setAdapter(movieAdapter);

            progressBar = (ProgressBar) findViewById(R.id.progress_bar);

            showProgressBar();


        }

        @Override
        protected Void doInBackground(String... params) {

            String queryUrl = params[0];
            URL url = Networking.buildUrl(queryUrl);
            String coverImage , movieName;
            String jsonResult = "";
            long movieId;
            try
            {
                jsonResult = Networking.getJson(url);
                JSONObject json = new JSONObject(jsonResult);
                JSONArray resultArray = json.getJSONArray("results");

                for(int i = 0 ; i< resultArray.length() ; i++)
                {
                    JSONObject movie = resultArray.getJSONObject(i);
                    coverImage = movie.getString("poster_path");
                    movieName = movie.getString("original_title");
                    movieId = movie.getLong("id");
                    Movie movieQueried = new Movie(coverImage , movieName , movieId);
                    publishProgress(movieQueried);
                }
            }
            catch(IOException e)
            {
                e.printStackTrace();
            }
            catch(JSONException e)
            {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onProgressUpdate(Movie... values) {
            movieList.add(values[0]);
            movieAdapter.notifyDataSetChanged();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            hidePrigressBar();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.overflow , menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int clickedMenu = item.getItemId();
        switch(clickedMenu)
        {
            case R.id.query_popular:
                if(!queryTypeCheck.equals("popular"))
                {
                    queryTypeCheck = "popular";
                    MovieQueryTask asyncTask = new MovieQueryTask();
                    asyncTask.execute(QUERY_POPULAR_URL);
                }
                break;
            case R.id.query_top_rated:
                if(!queryTypeCheck.equals("top_rated"))
                {
                    queryTypeCheck = "top_rated";
                    MovieQueryTask asyncTask = new MovieQueryTask();
                    asyncTask.execute(QUERY_TOP_RATED_URL);

                }
                break;
            default:

        }

        return super.onOptionsItemSelected(item);
    }

}
