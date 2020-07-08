package com.brain.books;

import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class ApiUtil {
    private ApiUtil(){

    }

    //constant variable for url to the API
    public static final String BASE_API_URL = "https://www.googleapis.com/books/v1/volumes";

    //function that builds the complete URL
    public static URL buildURl(String title){
        String fullURL = BASE_API_URL + "?=q" + title;
        URL url = null;
        try {
            url = new URL(fullURL);
        }catch(Exception e){
            e.printStackTrace();
        }
        return url;
    }

    //function that connects to the API
    public static String getJson(URL url) throws IOException{
        HttpURLConnection connection = (HttpURLConnection)url.openConnection();


        try {
            InputStream stream = connection.getInputStream();
            Scanner scanner = new Scanner(stream);
            scanner.useDelimiter("\\A"); //pattern and regular expressions : https://goo.gl/zMZcXt
            boolean hasData = scanner.hasNext();
            if(hasData){
                return  scanner.next();
            }else{
                return null;
            }
        }catch(Exception e){
            Log.d("Error", e.toString());
            return  null;
        }finally {
            connection.disconnect();
        }
    }
}
