package android.ykjw.com.memowithnodejs;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.ykjw.com.memowithnodejs.domain.Data;
import android.ykjw.com.memowithnodejs.domain.Qna;

import com.google.gson.Gson;

import io.reactivex.Observable;

public class WriteActivity extends AppCompatActivity {

    EditText editText, editText2, editText3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        editText = (EditText)findViewById(R.id.editText);
        editText2 = (EditText)findViewById(R.id.editText2);
        editText3 = (EditText)findViewById(R.id.editText3);

        // 네트웍에 접속해서 데이터를 가져오는 RX 생성
        Observable<String> localObservable =
                Observable.create(emitter -> {
                    emitter.onNext(Remote.getUrl("naver.com"));
                });


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AsyncTask<String, Void, String> networkTask = new AsyncTask<String, Void, String>() {

                    @Override
                    protected String doInBackground(String... params) {

                        Qna qna = new Qna();
                        qna.setTitle(params[0]);
                        qna.setName(params[1]);
                        qna.setContent(params[2]);

                        Gson gson = new Gson();
                        String jsonString = gson.toJson(qna);

                        String result = Remote.postjson("http://192.168.0.253:8080/post",jsonString);

                        return result;
                    }

                    @Override
                    protected void onPostExecute(String result) {
                        super.onPostExecute(result);
                        Snackbar.make(view, result, Snackbar.LENGTH_LONG).show();
                    }
                };

                networkTask.execute(editText.getText().toString(),editText2.getText().toString(),editText3.getText().toString());
                Intent intent = new Intent(WriteActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

}
