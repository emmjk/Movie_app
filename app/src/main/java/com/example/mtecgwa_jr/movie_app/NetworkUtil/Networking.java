package com.example.mtecgwa_jr.movie_app.NetworkUtil;

import android.content.Context;
import android.net.Uri;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

/**
 * Created by mtecgwa-jr on 4/13/17.
 */

public class Networking {


    static final String API_KEY = "/*NOTE : ************* api_key goes here         *********  */";
    public static URL buildUrl(String queryUrl)
    {
        Uri uri = Uri.parse(queryUrl).buildUpon()
                .appendQueryParameter("api_key" , API_KEY).build();

        URL url = null;
        try {
            url = new URL(uri.toString());


        }
        catch(MalformedURLException e)
        {
            e.printStackTrace();
        }

        return url;
    }

    public static String getJson(URL url) throws IOException {
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        try {
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
        finally
        {
            connection.disconnect();
        }

    }

}
