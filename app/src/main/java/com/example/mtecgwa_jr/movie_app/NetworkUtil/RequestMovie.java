package com.example.mtecgwa_jr.movie_app.NetworkUtil;

import android.net.Uri;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

/**
 * Created by mtecgwa-jr on 4/14/17.
 */

public class RequestMovie {

    public static final String api_key = "/*   NOTE:*******    api_key goes here       */";
    public static final String baseQuery = "https://api.themoviedb.org/3/movie/";

    public  static URL buildUrl(String movieId)
    {
        String queryUri = baseQuery+movieId;

        Uri uri = Uri.parse(queryUri).buildUpon()
                .appendQueryParameter("api_key" , api_key).build();

        URL url = null;
        try
        {
            url = new URL(uri.toString());
        }
        catch (MalformedURLException e)
        {
            e.printStackTrace();
        }

        return url;
    }

    public static String getMovieJson(URL url) throws IOException
    {

        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        try{

            InputStream inputStream = connection.getInputStream();

            Scanner scanner = new Scanner(inputStream);

            scanner.useDelimiter("\\A");

            if(scanner.hasNext())
            {
                return scanner.next();
            }
            else
            {
                return null;
            }


        }
        finally {
            connection.disconnect();
        }

    }
}
