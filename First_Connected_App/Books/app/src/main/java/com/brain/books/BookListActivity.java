package com.brain.books;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.io.IOException;
import java.net.URL;

public class BookListActivity extends AppCompatActivity {
    private ProgressBar mLoadingProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_list);

        mLoadingProgress = (ProgressBar)findViewById(R.id.pb_Loading);

        try {
            URL bookUrl = ApiUtil.buildURl("cooking"); //getting url with title 'cooking'
            //String jsonResult = ApiUtil.getJson(bookUrl);//getting the response in json format
            new BooksQueryTask().execute(bookUrl);
        }catch(Exception e){
            Log.d("Error ", e.getMessage());
        }
    }

    public class BooksQueryTask extends AsyncTask<URL, Void, String>{
        @Override
        protected String doInBackground(URL... urls) {
            URL searchURL = urls[0];
            String result = null;
            try {
                result = ApiUtil.getJson(searchURL);
            }catch(IOException e){
                Log.e("Error ", e.getMessage());
            }
            return  result;
        }

        @Override
        protected void onPostExecute(String result) {

            TextView tvResult = (TextView)findViewById(R.id.tvResponse);
            TextView tvError = (TextView)findViewById(R.id.tv_error);

            if(result == null){
                tvResult.setVisibility(View.INVISIBLE);
                tvError.setVisibility(View.VISIBLE);
            }else{
                tvResult.setVisibility(View.VISIBLE);
                tvError.setVisibility(View.INVISIBLE);
            }
            mLoadingProgress.setVisibility(View.INVISIBLE );
            tvResult.setText(result);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mLoadingProgress.setVisibility(View.VISIBLE);

        }
    }
}