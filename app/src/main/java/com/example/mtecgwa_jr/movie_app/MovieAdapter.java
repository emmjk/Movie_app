package com.example.mtecgwa_jr.movie_app;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by mtecgwa-jr on 4/13/17.
 */

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.AdapterViewHolder> {


    private ArrayList<Movie> movieList = new ArrayList<Movie>();

    private Context context;


    private static final String COVER_BASE_URL = "http://image.tmdb.org/t/p/w154";

    public MovieAdapter(ArrayList<Movie> movieList , Context context )
    {
        this.movieList = movieList;
        this.context = context;

    }

    public class AdapterViewHolder extends RecyclerView.ViewHolder
    {
        ImageView cover_image;

        TextView movieName;

        CardView movieCard;


        public AdapterViewHolder(View view)
        {
            super(view);

            cover_image = (ImageView) view.findViewById(R.id.movie_cover);
            movieName = (TextView) view.findViewById(R.id.movie_name);
            movieCard = (CardView) view.findViewById(R.id.movie_card);
            context = view.getContext();
        }


    }

    @Override
    public AdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie, parent ,false );
        AdapterViewHolder viewHolder = new AdapterViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MovieAdapter.AdapterViewHolder holder, int position) {
        final Movie movie = movieList.get(position);

        holder.movieName.setText(movie.getMovieName());

        String className = this.getClass().getName();

        Picasso.with(context)
                .load(COVER_BASE_URL+movie.getCover_url())
                .placeholder(R.drawable.movie)
                .error(R.drawable.movie)
                .into(holder.cover_image);

        Log.v(className, "cover url is " + movie.getCover_url());

        holder.movieCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String movieId = String.valueOf(movie.getMovieId());

                Intent intent = new Intent(context , MovieActivity.class);
                intent.putExtra("movieId" , movieId);
                context.startActivity(intent);

            }
        });

    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }


}

