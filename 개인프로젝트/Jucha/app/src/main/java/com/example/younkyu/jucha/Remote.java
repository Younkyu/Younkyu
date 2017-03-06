package com.example.younkyu.jucha;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Younkyu on 2017-03-06.
 */

public class Remote {

    public void getData(final Callback obj) {

        String urlString = obj.getUrl();

        if(!urlString.startsWith("http")) {
            urlString = "http://" + urlString;
        }

        new AsyncTask<String,Void,String>() {


            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                obj.getProgress().setProgressStyle(ProgressDialog.STYLE_SPINNER);
                obj.getProgress().setMessage("불러오는 중...");
                obj.getProgress().show();
            }

            @Override
            protected String doInBackground(String... params) {
                String urlString = params[0];

                try {

                    // 1. string 을 url 객체로 변환
                    URL url = new URL(urlString);
                    // 2. url로 네트워크 연결시작
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    // 3. url 연결에 대한 옵션 설정
                    connection.setRequestMethod("GET"); // 가.GET : 데이터 요청시 사용하는 방식
                    // 나.POST : 데이터 입력시 사용하는 방식
                    // 다.PUT : 데이터 수정시
                    // 라.DELETE : 데이터 삭제시


                    // 4. 서버로부터 회신
                    int responseCode = connection.getResponseCode();
                    if(responseCode == HttpURLConnection.HTTP_OK) {

                        // 4. 서버연결로 부터 스트림을 얻고, 버퍼래퍼로 감싼다
                        BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                        // 4.1 qksqhransdmf ehfaustj qjvjdml epdlxjfmf dlfrdjdhsek.
                        StringBuilder result = new StringBuilder(); // 이걸로 스트링 연산 빠르게 처리
                        String lineOfData = "";
                        while ((lineOfData = br.readLine()) != null) {
                            result.append(lineOfData);

                        }
                        connection.disconnect();



                        return result.toString();

                    } else {
                        Log.e("httpconnection", "error code = "+responseCode);
                    }
                }catch(Exception e) {
                    e.printStackTrace();
                }

                return null;
            }

            @Override
            protected void onPostExecute(String result) {
                super.onPostExecute(result);

                // 결과값 출력
                Log.i("Remote", result);

                // remote 객체를 생성한 측의 callback 함수 호출
                obj.call(result);
            }
        }.execute(urlString);

    }

    public interface Callback {
        public void call(String jsonString);
        public Context getContext();
        public String getUrl();
        public ProgressDialog getProgress();
    }


}
