package com.example.younkyu.httpurlconnection;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    Button button;
    EditText editText;
    TextView textView, textView2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = (Button) findViewById(R.id.button);
        editText = (EditText) findViewById(R.id.editText);
        textView = (TextView) findViewById(R.id.textView);

        textView2 = (TextView) findViewById(R.id.textView2);
        button.setOnClickListener(this);


    }



    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button :
                String urlString = editText.getText().toString();
                getUrl(urlString);
                break;
        }
    }

    public void getUrl(String urlString) {

        if(!urlString.startsWith("http")) {
            urlString = "http://" + urlString;
        }

        new AsyncTask<String,Void,String>() {

            ProgressDialog dialog = new ProgressDialog(MainActivity.this);

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                dialog.setMessage("불러오는즁..");

                dialog.show();
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

                textView.setText(result);
                int a = result.toString().indexOf("<title>")+7;
                int b = result.toString().indexOf("</title>");
                String dap = result.toString().substring(a,b);
                textView2.setText(dap);
                dialog.dismiss();
            }
        }.execute(urlString);
    }


}
