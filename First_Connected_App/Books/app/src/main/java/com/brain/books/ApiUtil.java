package com.brain.books;

import android.net.Uri;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

public class ApiUtil {
    private ApiUtil(){

    }

    //constant variable for url to the API
    public static final String BASE_API_URL = "https://www.googleapis.com/books/v1/volumes";
    public static final String QUERY_PARAMETER_KEY = "q";
    public static final String KEY = "key";
    public static final String API_KEY = "AIzaSyARDxucdTP2EC-DifN3OJgCl5CA_5_VAus";

    //function that builds the completes URL
    public static URL buildURl(String title){

        URL url = null;
        Uri uri = Uri.parse(BASE_API_URL).buildUpon()
                .appendQueryParameter(QUERY_PARAMETER_KEY, title)
                .appendQueryParameter(KEY, API_KEY)
                .build();
        try {
            url = new URL(uri.toString());
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

    public static ArrayList<Book> getBooksFromJson(String json){
        final String ID = "id";
        final String TITLE = "title";
        final String SUBTITLE = "subtitle";
        final String AUTHORS = "authors";
        final String PUBLISHER = "publisher";
        final String PUBLISHED_DATE = "publishedDate";
        final String ITEMS = "items";
        final String DESCRIPTION = "description";

        final String VOLUMEINFO = "volumeInfo";

        ArrayList<Book> books = new ArrayList<Book>();
        try {
            JSONObject jsonBooks = new JSONObject(json);
            JSONArray arrayBooks = jsonBooks.getJSONArray(ITEMS);// array that contains all the books
            int numberOfBooks = arrayBooks.length(); //getting number of books

            for(int i = 0; i< numberOfBooks; i++){
                JSONObject bookJSON = arrayBooks.getJSONObject(i); // getting each element
                JSONObject volumeInfoJSON = bookJSON.getJSONObject(VOLUMEINFO);

                int authorNum = volumeInfoJSON.getJSONArray(AUTHORS).length();
                String[] authors = new String[authorNum]; //contains all the authors

                for(int j = 0; j < authorNum; j++){
                    authors[j] = volumeInfoJSON.getJSONArray(AUTHORS).get(j).toString();
                }

                Book book = new Book(
                        bookJSON.getString(ID),
                        volumeInfoJSON.getString(TITLE),
                        (volumeInfoJSON.isNull(SUBTITLE)?"":volumeInfoJSON.getString(SUBTITLE)),
                        authors,
                        volumeInfoJSON.getString(PUBLISHER),
                        volumeInfoJSON.getString(PUBLISHED_DATE),
                        volumeInfoJSON.getString(DESCRIPTION)
                );
                books.add(book);
            }
        }catch(JSONException e){
            e.printStackTrace();
        }
        return books;
    }
}
