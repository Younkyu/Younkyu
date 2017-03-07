package com.example.younkyu.remoteokhttp;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new AsyncTask<Void,Void,Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                try {
                    String dpa = getData("http://daum.net");
                    Log.i("",dpa);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }
        }.execute();

        new Thread() {
            @Override
            public void run() {
                try {
                    String dpa = getData("http://google.com");
                    Log.i("",dpa);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();

    }

    private String getData(String url) throws IOException {
        // 1. Okhttp인스턴스 생성성
        OkHttpClient client = new OkHttpClient();
        // 2. request 생성성
       Request request = new Request.Builder()
                .url(url)
                .build();
        // 3. client 인스턴스에 request를 담아 보낸다.
        Response response = client.newCall(request).execute();
        return response.body().string();
    }

}
